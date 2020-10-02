
package jp.co.acms.jpost.base.domain.modelmapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.PropertyInfo;

import lombok.val;

/**
 * ModelMapper
 */
public class DefaultModelMapperFactory {

  /**
   * ModelMapperを返します。
   *
   * @return
   */
  public static ModelMapper create() {

    // ObjectMappingのためのマッパー
    val modelMapper = new ModelMapper();
    val configuration = modelMapper.getConfiguration();

    configuration.setPropertyCondition(
        // IDフィールド以外をマッピングする
        context -> {
          PropertyInfo propertyInfo = context.getMapping().getLastDestinationProperty();
          return !propertyInfo.getName().equals("id");
        });

    // 厳格にマッピングする
    configuration.setMatchingStrategy(MatchingStrategies.STRICT);

    return modelMapper;
  }
}
