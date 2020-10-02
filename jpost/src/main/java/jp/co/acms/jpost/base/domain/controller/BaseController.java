
package jp.co.acms.jpost.base.domain.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * BaseController.
 *
 * @author GDC
 */
public class BaseController {

  /** Beanマッピング(ModelMapper). */
  @Autowired
  protected ModelMapper modelMapper;

  /** ApplicationContext(DIコンテナ). */
  @Autowired
  protected ApplicationContext applicationContext;

  /**
   * ApplicaiotnContext(DIコンテナ)を返します。
   *
   * @return
   */
  protected ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
