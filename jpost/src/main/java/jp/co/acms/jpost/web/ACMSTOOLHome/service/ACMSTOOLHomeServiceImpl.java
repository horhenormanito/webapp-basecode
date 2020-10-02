/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
 *
 * 注意事項              : サービス実施クラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOLHome.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.acms.jpost.base.domain.service.BaseTransactionalService;
import jp.co.acms.jpost.web.ACMSTOOLHome.ACMSTOOLHomeForm;
import jp.co.acms.jpost.web.ACMSTOOLHome.dao.ACMSTOOLHomeDaoMapper;
import jp.co.acms.jpost.web.ACMSTOOLHome.dto.ApexCommTask;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ACMSTOOLHomeServiceImpl extends BaseTransactionalService implements ACMSTOOLHomeService {

  /** DAOマッパー */
  @Autowired
  private ACMSTOOLHomeDaoMapper daoMapper;

  @Override
  public void init(ACMSTOOLHomeForm form) {

    // For testing only (DB mapping)
    List<ApexCommTask> apexCommTask = daoMapper.findAllApexCommTask();
    apexCommTask.stream().forEach(data -> {
      log.info(">> " + data.getTaskId() + " == " + data.getWorkingGroup());
    });

    // 機能メニューリスト取得
    val functionMenuList = daoMapper.getFunctionMenuList();
    form.setFunctionMenuList(functionMenuList);
  }

}
