/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : DAOマッパーインターフェース
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOL00.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.acms.jpost.web.ACMSTOOL00.dto.ApexCommTask;

@Mapper
public interface ACMSTOOL00DaoMapper {

  /** ApexCommTaskのテーブルにすべてデータを取得 */
  List<ApexCommTask> findAllApexCommTask();

}
