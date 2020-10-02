
package jp.co.acms.jpost.base.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

  /** Beanマッピング(ModelMapper)です。 */
  @Autowired
  protected ModelMapper modelMapper;

}
