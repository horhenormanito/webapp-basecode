
package jp.co.acms.jpost.base.domain.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public abstract class BaseTransactionalService extends BaseService {

}
