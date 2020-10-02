package jp.co.acms.jpost.base;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jp.co.acms.jpost.base.common.utils.date.LocalDateConverter;
import jp.co.acms.jpost.base.common.utils.date.LocalDateTimeConverter;
import jp.co.acms.jpost.base.constant.WebConst;
import jp.co.acms.jpost.base.domain.validator.DefaultLocalValidatorFactory;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@Slf4j
public abstract class BaseWebConfig implements WebMvcConfigurer {

  @Bean
  public ClassLoaderTemplateResolver templateResolver() {

    val templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setCacheable(false);
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setCharacterEncoding("UTF-8");

    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    // templateEngine.setTemplateEngineMessageSource(messageSource());
    return templateEngine;
  }

  @Bean
  public ViewResolver viewResolver() {
    val viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");
    return viewResolver;
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

  // ====================================================================================================
  // Validation
  // ====================================================================================================
  /**
   * beanValidator.
   *
   * @param messageSource MessageSource
   * @return LocalValidatorFactoryBean
   */
  @Bean
  public LocalValidatorFactoryBean beanValidator(MessageSource messageSource) {
    val bean = new DefaultLocalValidatorFactory();
    bean.setValidationMessageSource(messageSource);
    return bean;
  }

  // ====================================================================================================
  // Converter
  // ====================================================================================================
  @Override
  public void addFormatters(FormatterRegistry registry) {
    // LocalDate
    registry.addConverter(new LocalDateConverter(WebConst.LOCALDATE_FORMAT));
    // LocalDateTime
    registry.addConverter(new LocalDateTimeConverter(WebConst.LOCALDATETIME_FORMAT));
  }

}
