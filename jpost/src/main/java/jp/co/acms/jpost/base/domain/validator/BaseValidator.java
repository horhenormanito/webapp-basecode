
package jp.co.acms.jpost.base.domain.validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.acms.jpost.base.domain.message.ResultMessage;
import lombok.extern.slf4j.Slf4j;

/**
 */
@Slf4j
public abstract class BaseValidator<T> implements Validator {

  /** オブジェクトマッピング(ModelMapper) */
  @Autowired
  protected ModelMapper modelMapper;

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void validate(final Object target, final Errors errors) {
    try {
      // boolean hasErrors = errors.hasErrors();
      //
      // if (!hasErrors || passThruBeanValidation(hasErrors)) {
      // 各機能で実装しているバリデーションを実行する
      doValidate((T) target, errors);
      log.debug("errors=" + errors);
      // }
    } catch (RuntimeException e) {
      log.error("validate error", e);
      throw e;
    }
  }

  /**
   * 入力チェックを実施します。
   *
   * @param form   Form
   * @param errors Errors
   */
  protected abstract void doValidate(final T form, final Errors errors);

  /**
   * 相関チェックバリデーションを実施するかどうかを示す値を返します。<br />
   * デフォルトは、JSR-303バリデーションでエラーがあった場合は相関チェックを実施しません。
   *
   * @return
   */
  protected boolean passThruBeanValidation(boolean hasErrors) {
    return false;
  }

  protected static void rejectValue(Errors errors, String field, ResultMessage message) {
    errors.rejectValue(field, message.getId(), message.getArgs(), message.getText());
  }
}
