
package jp.co.acms.jpost.base.common.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 *
 */
public final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

  private final DateTimeFormatter formatter;

  public LocalDateTimeConverter(String dateFormat) {
    this.formatter = DateTimeFormatter.ofPattern(dateFormat);
  }

  @Override
  public LocalDateTime convert(String source) {

    if (StringUtils.isEmpty(source)) {
      return null;
    }

    return LocalDateTime.parse(source, formatter);
  }
}
