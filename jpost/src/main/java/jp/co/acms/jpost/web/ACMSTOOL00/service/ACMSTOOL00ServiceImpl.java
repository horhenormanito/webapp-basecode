/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : サービス実施クラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL00.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.acms.jpost.base.domain.service.BaseTransactionalService;
import jp.co.acms.jpost.web.ACMSTOOL00.ACMSTOOL00Form;
import jp.co.acms.jpost.web.ACMSTOOL00.dao.ACMSTOOL00DaoMapper;
import jp.co.acms.jpost.web.ACMSTOOL00.dto.ApexCommTask;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ACMSTOOL00ServiceImpl extends BaseTransactionalService implements ACMSTOOL00Service {

  /** DAOマッパー */
  @Autowired
  private ACMSTOOL00DaoMapper daoMapper;

  @Override
  public void init(ACMSTOOL00Form form) {

    // For testing only (DB mapping)
    List<ApexCommTask> apexCommTask = daoMapper.findAllApexCommTask();
    apexCommTask.stream().forEach(data -> {
      log.info(">> " + data.getTaskId() + " == " + data.getWorkingGroup());
    });
  }

  @Override
  public void search(ACMSTOOL00Form form) {
    // For testing only (DB mapping)
    List<ApexCommTask> apexCommTaskList = daoMapper.findAllApexCommTask();

    // Check apexCommTaskList is not empty
    if (CollectionUtils.isNotEmpty(apexCommTaskList)) {
      form.setApexCommTaskList(apexCommTaskList);
    }
  }

}
