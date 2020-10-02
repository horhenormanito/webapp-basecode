
package jp.co.acms.jpost.base.domain.exception;

import java.util.List;

import jp.co.acms.jpost.base.domain.message.ResultMessage;

public class SystemException extends ResultMessagesNotificationException {

  public SystemException(Throwable cause) {
    super(ResultMessage.fromText(cause.getMessage()));
  }

  public SystemException(String message) {
    super(ResultMessage.fromText(message));
  }

  public SystemException(ResultMessage message) {
    super(message);
  }

  public SystemException(ResultMessage message, Throwable cause) {
    super(message, cause);
  }

  public SystemException(List<ResultMessage> messages) {
    super(messages);
  }

  public SystemException(List<ResultMessage> messages, Throwable cause) {
    super(messages, cause);
  }

}
