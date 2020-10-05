package jp.co.acms.jpost.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import jp.co.acms.jpost.base.common.ACMSTOOLCommonForm;
import jp.co.acms.jpost.base.domain.modelmapper.DefaultModelMapperFactory;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BaseConfig {

  // ====================================================================================================
  // ACMSTOOL Common Form
  // ====================================================================================================
  @Bean(name = "commonForm")
  public ACMSTOOLCommonForm commonForm() {
    // ObjectMappingのためのマッパー
    return new ACMSTOOLCommonForm();
  }

  // ====================================================================================================
  // ModelMapper
  // ====================================================================================================
  @Bean
  public ModelMapper modelMapper() {
    // ObjectMappingのためのマッパー
    return DefaultModelMapperFactory.create();
  }

  // ====================================================================================================
  // mybatis config
  // ====================================================================================================
//  @Bean
//  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//    sessionFactory.setDataSource(dataSource);
//    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//        .getResources("classpath:mybatis-mapper/**/*.xml"));
//
//    return sessionFactory.getObject();
//  }

//  @Autowired
//  private DataSource dataSource;
//
//  public DataSource getDataSource() {
//    return new TransactionAwareDataSourceProxy(dataSource);
//  }
//
//  @Bean
//  @Primary
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DataSource primaryDataSource(DataSourceProperties properties) {
//    return properties.initializeDataSourceBuilder().build();
//  }

  // ====================================================================================================
  // MessageSource
  // ====================================================================================================
  /**
   * 全ての[画面ID].propertiesをMessageSourceに統合し、BeanValidationによるForm単項目エラー時の変数名と論理名を関連付けます。
   *
   * @param resourcePatternResolver ResourcePatternResolver
   * @return MessageSource
   */
  @Bean
  public MessageSource messageSource(ResourcePatternResolver resourcePatternResolver) {

    val messageSource = new ResourceBundleMessageSource();
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    messageSource.addBasenames(
        // messages.properties
        "messages",
        // messages-fw.properties
        "messages-fw",
        // ValidationMessages.properties
        "ValidationMessages");

    // ------------------------------------------------------------------------------------------
    // [画面ID].properties
    // ------------------------------------------------------------------------------------------
    // 動的メッセージの取得
    // パターン指定でプロパティファイルの一覧を取得
    Resource[] rs;
    try {
      rs = resourcePatternResolver.getResources(
          ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/**/ACMSTOOL*/ACMSTOOL*.properties");
    } catch (IOException e) {
      e.printStackTrace();
      return messageSource;
    }

    for (val r : rs) {
      try {
        val uri = r.getURI().toString();
        if (uri.indexOf("ACMSTOOL") > 0) {
          val baseNames = uri.substring(uri.indexOf("properties")).replace(".properties", "");
          log.info("properties : " + baseNames);
          messageSource.addBasenames();
        }
      } catch (IOException e) {
        e.printStackTrace();
        continue;
      }
    }

    return messageSource;
  }

}
