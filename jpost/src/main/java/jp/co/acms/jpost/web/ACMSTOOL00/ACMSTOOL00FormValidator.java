/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : フォームヴァリデーションクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOL00;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jp.co.acms.jpost.base.domain.message.ResultMessage;
import jp.co.acms.jpost.base.domain.validator.BaseValidator;

@Component
//@Slf4j
public class ACMSTOOL00FormValidator extends BaseValidator<ACMSTOOL00Form> {

  @Autowired
  private ACMSTOOL00Properties prop;

  @Override
  protected void doValidate(ACMSTOOL00Form form, Errors errors) {
    switch (form.getAction().getActionId()) {
    // 検索ボタン押下
    case "searchBtn":
      searchBtn(form, errors);
      break;
    default:
      // nothing to do.
      break;
    }
  }

  /**
   * 検索処理ののvalidationチェックを行う
   *
   * @param form   ACMSTOOL00Form
   * @param errors Errors
   */
  private void searchBtn(ACMSTOOL00Form form, Errors errors) {

    // if no error, perform other validation(correlation check)
    if (!errors.hasFieldErrors("dateFrom") && !errors.hasFieldErrors("dateTo")) {
      // Correlation check between dateFrom and dateTo
      if (Objects.nonNull(form.getDateFrom()) && Objects.nonNull(form.getDateTo())) {
        if (form.getDateFrom().isAfter(form.getDateTo())) {
          // error setting
          super.rejectValue(errors, "dateFrom", ResultMessage.fromId("ACMSTOOL00E",
              prop.getValue("ACMSTOOL00.dateFrom"), prop.getValue("ACMSTOOL00.dateTo")));
          super.rejectValue(errors, "dateTo", ResultMessage.fromId("ACMSTOOL00E", prop.getValue("ACMSTOOL00.dateFrom"),
              prop.getValue("ACMSTOOL00.dateTo")));
        }
      }
    }

  }

}
