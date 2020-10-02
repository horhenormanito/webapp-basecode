/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : サービス実施クラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL99.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.acms.jpost.base.domain.service.BaseTransactionalService;
import jp.co.acms.jpost.web.ACMSTOOL99.ACMSTOOL99Form;
import jp.co.acms.jpost.web.ACMSTOOL99.dao.ACMSTOOL99DaoMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ACMSTOOL99ServiceImpl extends BaseTransactionalService implements ACMSTOOL99Service {

  /** DAOマッパー */
  @Autowired
  private ACMSTOOL99DaoMapper daoMapper;

  @Override
  public void init(ACMSTOOL99Form form) {

  }

}
