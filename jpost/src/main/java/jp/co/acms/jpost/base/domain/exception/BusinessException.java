
package jp.co.acms.jpost.base.domain.exception;

import java.util.List;

import jp.co.acms.jpost.base.domain.message.ResultMessage;

public class BusinessException extends ResultMessagesNotificationException {

  public BusinessException(Throwable cause) {
    super(ResultMessage.fromText(cause.getMessage()));
  }

  public BusinessException(String message) {
    super(ResultMessage.fromText(message));
  }

  public BusinessException(ResultMessage message) {
    super(message);
  }

  public BusinessException(ResultMessage message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(List<ResultMessage> messages) {
    super(messages);
  }

  public BusinessException(List<ResultMessage> messages, Throwable cause) {
    super(messages, cause);
  }

}
