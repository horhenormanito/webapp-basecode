/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : フォームヴァリデーションクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOLHome;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jp.co.acms.jpost.base.domain.validator.BaseValidator;

@Component
//@Slf4j
public class ACMSTOOLHomeFormValidator extends BaseValidator<ACMSTOOLHomeForm> {

  @Override
  protected void doValidate(ACMSTOOLHomeForm form, Errors errors) {

  }

}
