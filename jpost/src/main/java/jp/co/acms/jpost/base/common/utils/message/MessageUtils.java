package jp.co.acms.jpost.base.common.utils.message;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import jp.co.acms.jpost.base.domain.message.ResultMessageId;

/**
 * MessageUtils.
 */
@Component
public class MessageUtils {

  private static MessageSource messageSource;

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    MessageUtils.messageSource = messageSource;
  }

  public static String getMessage(String key, Object... args) {
    return MessageUtils.messageSource.getMessage(key, args, Locale.JAPANESE);
  }

  public static String getMessage(ResultMessageId id, Object... args) {
    return MessageUtils.messageSource.getMessage(id.getId(), args, Locale.JAPANESE);
  }

}
