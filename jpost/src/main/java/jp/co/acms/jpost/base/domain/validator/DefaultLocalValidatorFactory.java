
package jp.co.acms.jpost.base.domain.validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultLocalValidatorFactory extends LocalValidatorFactoryBean {

  /** ValidationMessages.properties */
  private static final String VALIDATION_MESSAGES = "ValidationMessages.properties";

  @Override
  protected void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {

    val errorCodeMap = makeErrorCodeMap();

    // errorsに格納されているエラー情報をerrorCodeMapの順にソート
    this.sortErrors(violations, errors, errorCodeMap);
  }

  /**
   * <pre>
   * 引数で取得したerrorCodeMapに定義されているエラーの順番で引数のerrors(BindingResult)の情報をソートします。
   * </pre>
   *
   * @param violations   Set of ConstraintViolation
   * @param errors       Errors
   * @param errorCodeMap エラーコード優先順位Map
   */
  private void sortErrors(Set<ConstraintViolation<Object>> violations, Errors errors,
      Map<String, String> errorCodeMap) {

    val addErrorMap = new TreeMap<String, ObjectError>();
    val bindingResult = (BindingResult) errors;

    for (ConstraintViolation<Object> violation : violations) {

      val field = determineField(violation);
      val fieldError = errors.getFieldError(field);

      if (fieldError == null || !fieldError.isBindingFailure()) {

        try {

          val cd = violation.getConstraintDescriptor();
          val errorCode = determineErrorCode(cd);
          val errorArgs = getArgumentsForConstraint(errors.getObjectName(), removeArrayString(field), cd);

          if (errors instanceof BindingResult) {
            // Can do custom FieldError registration with invalid value from
            // ConstraintViolation,
            // as necessary for Hibernate Validator compatibility (non-indexed set path in
            // field)
            // BindingResult bindingResult = (BindingResult) errors;
            val nestedField = bindingResult.getNestedPath() + field;

            if ("".equals(nestedField)) {
              val errorCodes = bindingResult.resolveMessageCodes(errorCode);
              val treeMapKey = errorCodeMap.get(errorCode) + "_" + nestedField;
              addErrorMap.put(treeMapKey,
                  new ObjectError(errors.getObjectName(), errorCodes, errorArgs, violation.getMessage()));
            } else {
              val rejectedValue = getRejectedValue(field, violation, bindingResult);
              val errorCodes = bindingResult.resolveMessageCodes(errorCode, field);
              val treeMapKey = errorCodeMap.get(errorCode) + "_" + nestedField;
              addErrorMap.put(treeMapKey, new FieldError(errors.getObjectName(), nestedField, rejectedValue, false,
                  errorCodes, errorArgs, violation.getMessage()));
            }
          } else {
            // got no BindingResult - can only do standard rejectValue call with automatic
            // extraction of the current field value
            errors.rejectValue(field, errorCode, errorArgs, violation.getMessage());
          }

        } catch (NotReadablePropertyException ex) {
          throw new IllegalStateException("JSR-303 validated property '" + field
              + "' does not have a corresponding accessor for Spring data binding - "
              + "check your DataBinder's configuration (bean property versus direct field access)", ex);
        }
      }
    }

    // 入力チェックの優先順でbindingResultにエラー情報を追加する。
    val it = addErrorMap.entrySet().iterator();
    while (it.hasNext()) {
      val entry = it.next();
      bindingResult.addError(entry.getValue());
      it.remove();
    }
  }

  /**
   * <pre>
   * 引数で指定されたプロパティを読み込み、定義されているエラーコードの順にerrorCodeMapに
   * key:エラーコード、value:優先順(5桁0埋め0スタート)を格納した情報を返却します。
   * </pre>
   *
   * @return エラーコード優先順位Map
   */
  private Map<String, String> makeErrorCodeMap() {

    // メッセージプロパティの情報を取得し、優先順をMapに格納
    val errorCodeMap = new LinkedHashMap<String, String>();
    int priorityNo = 0;

    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(VALIDATION_MESSAGES)))) {

      String line;
      while ((line = br.readLine()) != null) {
        line = line.replaceAll(" ", "");
        if (line.indexOf("=") != -1) {
          if (line.charAt(0) != '#') {
            errorCodeMap.put(line.split("=")[0], StringUtils.leftPad(String.valueOf(priorityNo), 5, '0'));
            priorityNo++;
          }
        }
      }

    } catch (IOException e) {
      log.error("makeErrorCodeMap", e);
    }

    return errorCodeMap;
  }

  /**
   * <pre>
   * 引数の文字列から"["を探し、存在した場合は"["から"]"を削除します。
   * 例：m1List[0].cusName
   *        ↓
   *     m1List.cusName
   * </pre>
   *
   * @param field the Spring-reported field (for use with {@link Errors})
   * @return 引数の文字列から"["を探し、存在した場合は"["から"]"を削除した文字列(例：m1List[0].cusName →
   *         m1List.cusName)
   */
  public static String removeArrayString(String field) {
    while (field.indexOf("[") != -1) {
      field = field.substring(0, field.indexOf("[")) + field.substring(field.indexOf("]") + 1, field.length());
    }
    return field;
  }

}
