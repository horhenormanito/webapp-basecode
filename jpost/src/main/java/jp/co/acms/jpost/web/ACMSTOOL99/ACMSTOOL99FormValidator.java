/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : フォームヴァリデーションクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOL99;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import jp.co.acms.jpost.base.domain.validator.BaseValidator;

@Component
//@Slf4j
public class ACMSTOOL99FormValidator extends BaseValidator<ACMSTOOL99Form> {

  @Override
  protected void doValidate(ACMSTOOL99Form form, Errors errors) {
    // 処理なし
  }

}
