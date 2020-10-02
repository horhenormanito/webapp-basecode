
package jp.co.acms.jpost.base.domain.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.acms.jpost.base.domain.message.ResultMessage;
import lombok.Getter;

public abstract class ResultMessagesNotificationException extends RuntimeException {

  @Getter
  private final List<ResultMessage> resultMessages = new ArrayList<ResultMessage>();

  public ResultMessagesNotificationException(ResultMessage message, Throwable cause) {
    this(Arrays.asList(message), cause);
  }

  /**
   * コンストラクタ
   *
   * @param messages ResultMessage
   * @param cause    Throwable
   */
  public ResultMessagesNotificationException(List<ResultMessage> messages, Throwable cause) {
    super(cause);
    if (messages == null) {
      throw new IllegalArgumentException("messages must not be null");
    }
    this.resultMessages.addAll(messages);
  }

  protected ResultMessagesNotificationException(ResultMessage message) {
    this(Arrays.asList(message), null);
  }

  protected ResultMessagesNotificationException(List<ResultMessage> messages) {
    this(messages, null);
  }

}
