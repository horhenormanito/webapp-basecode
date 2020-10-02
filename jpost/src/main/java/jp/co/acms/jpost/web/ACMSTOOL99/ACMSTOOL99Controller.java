/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : Controllerクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL99;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.acms.jpost.base.domain.controller.ACMSTOOLBaseController;
import jp.co.acms.jpost.web.ACMSTOOL00.ACMSTOOL00Form;
import jp.co.acms.jpost.web.ACMSTOOL99.service.ACMSTOOL99Service;
import lombok.val;

@RequestMapping("ACMSTOOL99")
@Controller
public class ACMSTOOL99Controller extends ACMSTOOLBaseController<ACMSTOOL99Form> {

  /** プロパティクラス */
  @Autowired
  private ACMSTOOL99Properties prop;

  /** フォームヴァリデーションクラス */
  @Autowired
  private ACMSTOOL99FormValidator validator;

  /** サービスクラス */
  @Autowired
  private ACMSTOOL99Service service;

  @Override
  protected ACMSTOOL99Form setUpForm() {

    // 新規フォーム作成
    val form = new ACMSTOOL99Form();
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
  public String init(ACMSTOOL99Form form, Model model, RedirectAttributes redirects) {

    // サービス呼び出す
    service.init(form);

    val acmsTool00Form = (ACMSTOOL00Form) super.getForm(ACMSTOOL00Form.class);

    int rowNumber = acmsTool00Form.getAction().getRowNumber();

    val apexCommTask = acmsTool00Form.getApexCommTaskList().get(rowNumber);

    modelMapper.map(apexCommTask, form);

    // viewに返す
    return view(model, redirects);

  }

  @Override
  public String returned(ACMSTOOL99Form form, Model model, RedirectAttributes redirects) {

    // viewに返す
    return view(model, redirects);

  }

  @Override
  public String getBreadcrumbName(ACMSTOOL99Form form) {
    return prop.getValue("ACMSTOOL99.breadcrumb.name");
  }

}
