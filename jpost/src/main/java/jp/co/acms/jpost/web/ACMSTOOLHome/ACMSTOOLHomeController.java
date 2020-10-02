/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : Controllerクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOLHome;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.acms.jpost.base.domain.controller.ACMSTOOLBaseController;
import lombok.val;

@Controller
public class ACMSTOOLHomeController extends ACMSTOOLBaseController<ACMSTOOLHomeForm> {

  /** プロパティクラス */
  @Autowired
  private ACMSTOOLHomeProperties prop;

  /** フォームヴァリデーションクラス */
  @Autowired
  private ACMSTOOLHomeFormValidator validator;

  @Override
  protected void initBinder(WebDataBinder binder) {

    // binderにフォームヴァリデーション追加
    binder.addValidators(validator);

  }

  @Override
  protected ACMSTOOLHomeForm setUpForm() {

    // 新規フォーム作成
    val form = new ACMSTOOLHomeForm();
    form.setFormId(prop.getFormId());
    form.setFormName(prop.getFormName());
    return form;

  }

  @Override
  public String init(ACMSTOOLHomeForm form, Model model, RedirectAttributes redirects) {

    // viewに返す
    return super.home(form, model);

  }

  @Override
  public String returned(ACMSTOOLHomeForm form, Model model, RedirectAttributes redirects) {

    // viewに返す
    return view(model, redirects);

  }

  @Override
  public String getBreadcrumbName(ACMSTOOLHomeForm form) {
    return prop.getValue("ACMSTOOLHome.breadcrumb.name");
  }

}
