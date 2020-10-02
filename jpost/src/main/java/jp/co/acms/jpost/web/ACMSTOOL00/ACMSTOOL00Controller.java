/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : Controllerクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.acms.jpost.base.domain.controller.ACMSTOOLBaseController;
import jp.co.acms.jpost.web.ACMSTOOL00.service.ACMSTOOL00Service;
import lombok.val;

@RequestMapping("ACMSTOOL00")
@Controller
public class ACMSTOOL00Controller extends ACMSTOOLBaseController<ACMSTOOL00Form> {

  /** プロパティクラス */
  @Autowired
  private ACMSTOOL00Properties prop;

  /** フォームヴァリデーションクラス */
  @Autowired
  private ACMSTOOL00FormValidator validator;

  /** サービスクラス */
  @Autowired
  private ACMSTOOL00Service service;

  @Override
  protected ACMSTOOL00Form setUpForm() {

    // 新規フォーム作成
    val form = new ACMSTOOL00Form();
    form.setFormId(prop.getFormId());
    form.setFormName(prop.getFormName());
    return form;

  }

  @Override
  protected void initBinder(WebDataBinder binder) {

    // binderにフォームヴァリデーション追加
    binder.addValidators(validator);

  }

  @Override
  public String init(ACMSTOOL00Form form, Model model, RedirectAttributes redirects) {

    // サービス呼び出す
    service.init(form);

    // viewに返す
    return view(model, redirects);

  }

  @Override
  public String returned(ACMSTOOL00Form form, Model model, RedirectAttributes redirects) {

    // viewに返す
    return view(model, redirects);

  }

  @Override
  public String getBreadcrumbName(ACMSTOOL00Form form) {
    return prop.getValue("ACMSTOOL00.breadcrumb.name");
  }

  @PostMapping(params = "searchBtn")
  public String searchBtn(@Validated ACMSTOOL00Form form, BindingResult br, Model model,
      RedirectAttributes redirects) {

    // ビーンヴァリデーションとフォームヴァリデーションのエラーがある場合、自画面に遷移し、エラーを表示する。
    if (br.hasErrors()) {
      return view(model, redirects, br);
    }

    // サービス「search」関数を呼び出す。
    service.search(form);

    // エラーがない場合、正常処理を行う。
    return view(model, redirects);
  }

  @PostMapping(params = "detailLink")
  public String detailLink(ACMSTOOL00Form form, BindingResult br, Model model,
      RedirectAttributes redirects) {

    // エラーがない場合、正常処理を行う。
    return view(model, redirects, "ACMSTOOL99");
  }

}
