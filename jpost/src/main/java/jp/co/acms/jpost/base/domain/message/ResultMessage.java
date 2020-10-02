package jp.co.acms.jpost.base.domain.message;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import jp.co.acms.jpost.base.common.utils.message.MessageUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMessage implements Serializable {

  /**
   * message id
   */
  private final String id;

  /**
   * message arguments
   */
  private final Object[] args;

  /**
   * message text
   */
  private final String text;

  public ResultMessage(String id, Object[] args, String text) {
    this.id = id;
    this.args = args == null ? new Object[0] : args;
    if (StringUtils.isNotEmpty(id)) {
      this.text = MessageUtils.getMessage(id, args);
    } else {
      this.text = text;
    }
  }

  public static ResultMessage fromId(ResultMessageId id, Object... args) {
    return new ResultMessage(id.getId(), args, null);
  }

  public static ResultMessage fromId(String id, Object... args) {
    return new ResultMessage(id, args, null);
  }

  public static ResultMessage fromText(String text) {
    return new ResultMessage(null, new Object[0], text);
  }

  public static boolean hasErrors(List<ResultMessage> resultMessages) {
    return resultMessages.stream().filter(m -> StringUtils.strip(m.getId()).endsWith("E")).findFirst().isPresent();
  }

}
