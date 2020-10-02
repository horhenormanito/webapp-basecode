/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ベースコード：Controller共通
　*
 * 注意事項              : Controller共通クラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.base.domain.controller;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.acms.jpost.base.common.ACMSTOOLCommonForm;
import jp.co.acms.jpost.base.constant.WebConst;
import jp.co.acms.jpost.base.domain.exception.BusinessException;
import jp.co.acms.jpost.base.domain.exception.SystemException;
import jp.co.acms.jpost.base.domain.form.BaseForm;
import jp.co.acms.jpost.base.domain.message.ResultMessage;
import jp.co.acms.jpost.base.utils.RequestUtils;
import jp.co.acms.jpost.web.ACMSTOOLHome.ACMSTOOLHomeForm;
import jp.co.acms.jpost.web.ACMSTOOLHome.dto.Breadcrumb;
import jp.co.acms.jpost.web.ACMSTOOLHome.service.ACMSTOOLHomeService;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public abstract class ACMSTOOLBaseController<F extends BaseForm> extends BaseController {

  @Autowired
  private ACMSTOOLCommonForm commonForm;

  /** PRG(Post-Redirect-Get) */
  private static final String PRG = "PRG";
  /** Action ID initial load/display */
  private static final String INIT = "init";

  /** 遷移元：画面ID(initメソッドでのみModelから取得可能) */
  protected static final String TRANSITION_SOURCE_FORM_ID = "TRANSITION_SOURCE_FORM_ID";

  /** HttpSession */
  @Autowired
  private HttpSession session;

  /**
   * ModelAttribute:Bind request parameter in 【Screen ID】Form
   *
   * @param request HttpServletRequest
   * @param model Model
   * @return [【Screen ID]Form
   */
  @ModelAttribute
  public F modelAttribute(HttpServletRequest request, Model model) {

    F form = getForm();

    // ======================================================
    // PRG (Post-Redirect-Get)
    // ======================================================
    if (request.getParameterMap().containsKey(PRG)) {
      if (log.isDebugEnabled()) {
        log.debug("form=" + form);
      }
      return form;
    }

    val actionId = StringUtils.stripToEmpty(request.getParameter(WebConst.QUERY_STRING_ACTION_ID));
    val isAjax = RequestUtils.isAjaxRequest(request);
    val isInit = !isAjax && (StringUtils.isEmpty(actionId) || INIT.equals(actionId));
    log.debug("{} {} actionId={} ajax={}", request.getMethod(), request.getRequestURL(), actionId,
        isAjax);

    // ======================================================
    // init
    // ======================================================
    if (isInit) {
      // setUpForm
      form = setUpForm();
      form.getAction().setActionId(INIT);
      setForm(form, model);

      // Re-setting of function menu list
      if (!(form instanceof ACMSTOOLHomeForm)) {
        val homeForm = (ACMSTOOLHomeForm) this.getForm(ACMSTOOLHomeForm.class);
        modelMapper.map(homeForm, commonForm);
      }
    }

    // Clear for every request
    if (!Objects.isNull(form)) {
      form.setFocusField(StringUtils.EMPTY);
      form.getResultMessages().clear();
    }

    // return [Screen ID]Form
    return form;
  }

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // abstract methods
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  protected abstract F setUpForm();

  @InitBinder
  protected abstract void initBinder(WebDataBinder binder);

  @GetMapping(params = WebConst.QUERY_STRING_INIT)
  @PostMapping(params = WebConst.QUERY_STRING_INIT)
  public abstract String init(F form, Model model, RedirectAttributes redirects);

  @GetMapping(params = WebConst.QUERY_STRING_RETURNED)
  @PostMapping(params = WebConst.QUERY_STRING_RETURNED)
  public abstract String returned(F form, Model model, RedirectAttributes redirects);

  public abstract String getBreadcrumbName(F form);

  // protected abstract void setHomeFunctionMenu(HttpSession session);

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // Screen Transition
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Own screen transition
   *
   * @param model Model
   * @param redirects RedirectAttributes
   * @return 【Screen ID】
   */
  protected String view(Model model, RedirectAttributes redirects) {
    return redirect(model, redirects, null, null);
  }

  /**
   * Own screen transition with BindingResult
   *
   * @param model Model
   * @param redirects RedirectAttributes
   * @param br BindingResult
   * @return 【Screen ID】
   */
  protected String view(Model model, RedirectAttributes redirects, BindingResult br) {
    return redirect(model, redirects, br, null);
  }

  /**
   * Transition to other screen
   *
   * @param model Model
   * @param redirects RedirectAttributes
   * @param nextFormId Next screen ID
   * @return 【Screen ID】
   */
  protected String view(Model model, RedirectAttributes redirects, String nextFormId) {
    return redirect(model, redirects, null, nextFormId);
  }

  /**
   * Transition back to other screen
   *
   * @param model Model
   * @param redirects RedirectAttributes
   * @param nextFormId Next screen ID
   * @return 【Screen ID】
   */
  protected String viewReturn(Model model, RedirectAttributes redirects, String nextFormId) {

    if (redirects != null) {
      // 転記 (model -> redirects.addFlashAttribute)
      modelToFlashAttribute(model, redirects);
    }

    // ---------------
    // 遷移元情報設定
    // ---------------
    setTransitionSource(redirects);

    // ---------------
    // セッションから自画面Formを削除する
    // ---------------
    session.removeAttribute(getSessionFormKey());

    return WebConst.REDIRECT.concat(nextFormId).concat("?").concat(WebConst.QUERY_STRING_RETURNED);
  }

  private String redirect(Model model, RedirectAttributes redirects, BindingResult br,
      String nextFormId) {

    if (redirects != null) {

      // 転記 (model -> redirects.addFlashAttribute)
      modelToFlashAttribute(model, redirects);

      // リダイレクト先に入力エラーを渡します。
      if (br != null) {

        // リダイレクトするとBindingResultのエラーがスコープアウトでクリアされるため、html表示.
        redirects.addFlashAttribute(WebConst.MAV_ERRORS, br);

        // br.hasResult.message = 入力内容に誤りがあります。<br>選択/入力内容を確認してください。
        val resultMessage = getForm().getResultMessages();
        resultMessage.clear();
        resultMessage.add(ResultMessage.fromId("br.hasResult.message"));

        return postRedirectGet(model, redirects);
      }
    }

    // ---------------
    // 遷移元情報設定
    // ---------------
    setTransitionSource(redirects);

    // 自画面ID
    val myFormId = getClass().getSimpleName().replaceAll("Controller", "");

    if (StringUtils.isNotBlank(nextFormId) && !StringUtils.equals(nextFormId, myFormId)) {
      // 他画面遷移
      return WebConst.REDIRECT.concat(nextFormId).concat("?").concat(WebConst.QUERY_STRING_INIT);
    } else {
      // 自画面遷移
      return WebConst.REDIRECT.concat(myFormId).concat("?").concat(PRG);
    }
  }

  @GetMapping(params = PRG)
  private String postRedirectGet(Model model, RedirectAttributes redirects) {

    // 転記 (model -> redirects.addFlashAttribute)
    modelToFlashAttribute(model, redirects);

    // set breadcrumb
    setBreadcrumb();

    // return HTML.
    // e.g.) ACMSTOOL/ACMSTOOL00
    val controllerName = this.getClass().getSimpleName();
    return String.join("/",
        // ACMSTOOL
        controllerName.substring(0, 8),
        // ACMSTOOL00
        controllerName.replaceAll("Controller", ""));
  }

  /**
   * Breadcrumbの設定
   */
  private void setBreadcrumb() {

    // Check if breadcrumb exist in the list
    commonForm.getBreadcrumbs().stream()
        .filter(bc -> StringUtils.equals(bc.getFormId(), this.getForm().getFormId())).findAny()
        .ifPresentOrElse(bc -> {
          // 存在する場合、breadcrumbリストの更新
          updateBreadcrumbs(this.getForm());
        }, () -> {
          // 存在しない場合、breadcrumbリストに新breadcrumbを追加
          val breadcrumbName = getBreadcrumbName(this.getForm());
          if (Objects.nonNull(breadcrumbName)) {
            val bcBuilder = Breadcrumb.builder().formId(this.getForm().getFormId())
                .breadcrumbName(breadcrumbName).build();
            commonForm.getBreadcrumbs().add(bcBuilder);
          }
        });
  }

  /**
   * breadcrumbsの更新
   *
   * @param <U> 対象フォーム
   * @param form <any form that extends BaseForm>
   */
  private <U extends BaseForm> void updateBreadcrumbs(U form) {

    // Get form ID
    val formId = new AtomicReference<String>(form.getFormId());
    // If form is an instance of ACMSTOOLHomeForm, set formId with
    // breadcrumbNextFormId
    if (form instanceof ACMSTOOLHomeForm) {
      formId.set(form.getBreadcrumbNextFormId());
    }

    val isFound = new AtomicBoolean();
    val updatedBreadcrumbList = commonForm.getBreadcrumbs().stream().filter(bc -> {
      // Reduce list of breadcrumbs up to the current target formId property
      if (!isFound.get()) {
        if (StringUtils.equals(bc.getFormId(), formId.get())) {
          isFound.set(Boolean.TRUE);
        }
        return Boolean.TRUE;
      }
      // skip breadcrumbs
      return Boolean.FALSE;
    }).collect(Collectors.toList());

    // update breadcrumb list
    commonForm.setBreadcrumbs(updatedBreadcrumbList);

  }

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // 例外ハンドリング
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  @ExceptionHandler({ Throwable.class })
  private String handleException(Throwable e, Model model) throws Exception {

    val form = getForm();
    model.asMap().put(form.getClass().getSimpleName(), form);

    val msgs = form.getResultMessages();

    if (e instanceof BusinessException) {
      // 業務例外
      msgs.addAll(((BusinessException) e).getResultMessages());
//		} else if (e instanceof PessimisticLockingFailureException) {
//			// 悲観ロックエラー
//			msgs.add(ResultMessage.fromId(MessageFwId.PessimisticLockingFailureException));
//		} else if (e instanceof OptimisticLockingFailureException) {
//			// 楽観ロックエラー
//			msgs.add(ResultMessage.fromId(MessageFwId.OptimisticLockingFailureException));
    } else {
      // システム例外
      throw new SystemException(e);
    }

    return view(model, null);
  }

  @SuppressWarnings("unchecked")
  protected <T extends Serializable> T getForm(Class<? extends BaseForm> cls) {
    return (T) session.getAttribute(cls.getSimpleName());
  }

  @SuppressWarnings("unchecked")
  private F getForm() {
    return (F) session.getAttribute(getSessionFormKey());
  }

  protected void setForm(F form, Model model) {
    val sessionFormKey = getSessionFormKey();
    session.setAttribute(sessionFormKey, form);
    model.asMap().put(sessionFormKey, form);
  }

  protected F clearForm(Model model) {

    val form = setUpForm();
    val sessionFormKey = getSessionFormKey();
    session.setAttribute(sessionFormKey, form);
    model.asMap().put(sessionFormKey, form);

    return form;
  }

  private void setTransitionSource(RedirectAttributes redirects) {

    if (redirects == null) {
      return;
    }

    val form = getForm();

    // 画面ID
    String formId = "";
    if (form != null) {
      formId = form.getFormId();
    }

    // 遷移元：画面ID
    redirects.addFlashAttribute(TRANSITION_SOURCE_FORM_ID, formId);
  }

  private String getSessionFormKey() {
    return setUpForm().getClass().getSimpleName();
  }

  private static void modelToFlashAttribute(Model model, RedirectAttributes redirects) {
    // 転記 (model -> redirects.addFlashAttribute)
    model.asMap().entrySet().forEach(e -> {
      redirects.addFlashAttribute(e.getKey(), model.getAttribute(e.getKey()));
    });
  }

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // ホームController
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  @Autowired
  private ACMSTOOLHomeService service;

  /**
   * ホームページ遷移
   *
   * @param form ACMSTOOLHomeForm
   * @param model Model
   * @return
   */
  @GetMapping(WebConst.APP_BASE_HOME_URL)
  public String home(ACMSTOOLHomeForm form, Model model) {

    service.init(form);

    modelMapper.map(form, commonForm);

    setBreadcrumb();

    return WebConst.APP_BASE_PATH + WebConst.APP_BASE_HOME_PATH;
  }

  /**
   * メニュー機能のエベント
   *
   * @param form ACMSTOOLHomeForm
   * @param model Model
   * @param redirects RedirectAttributes
   * @return
   */
  @PostMapping(value = WebConst.APP_BASE_FUNCTION_MENU_URL)
  public String functionMenuLink(ACMSTOOLHomeForm form, Model model, RedirectAttributes redirects) {
    return WebConst.REDIRECT + form.getNextFormId() + "?" + WebConst.QUERY_STRING_INIT;

  }

  /**
   * breadcrumbのエベント
   *
   * @param form ACMSTOOLHomeForm
   * @param model Model
   * @param redirects RedirectAttributes
   * @return
   */
  @PostMapping(value = WebConst.APP_BASE_BREADCRUMB_URL)
  public String breadcrumbLink(ACMSTOOLHomeForm form, Model model, RedirectAttributes redirects) {

    updateBreadcrumbs(form);

    // ホームの時、初期表示とする。
    if (StringUtils.equals(form.getFormId(), form.getBreadcrumbNextFormId())) {
      return WebConst.REDIRECT + "?" + WebConst.QUERY_STRING_INIT;
    }
    // 上記以外の場合、返すとする。
    return WebConst.REDIRECT + form.getBreadcrumbNextFormId() + "?"
        + WebConst.QUERY_STRING_RETURNED;

  }

}
