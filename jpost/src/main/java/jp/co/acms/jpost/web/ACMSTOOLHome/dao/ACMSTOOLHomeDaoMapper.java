/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : DAOマッパーインターフェース
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOLHome.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.acms.jpost.web.ACMSTOOLHome.dto.ApexCommTask;
import jp.co.acms.jpost.web.ACMSTOOLHome.dto.FunctionMenu;

@Mapper
public interface ACMSTOOLHomeDaoMapper {

  List<ApexCommTask> findAllApexCommTask();

  /**
   * 機能メニューリスト取得(TODO)
   *
   * @return List<FunctionMenu>
   */
  default List<FunctionMenu> getFunctionMenuList() {
    List<FunctionMenu> menuList = new ArrayList<FunctionMenu>(
        Arrays.asList(FunctionMenu.builder().menuId("ACMSTOOL00").menuName("ACMSTOOL00").build(),
            FunctionMenu.builder().menuId("ACMSTOOL01").menuName("回線状況一覧").build(),
            FunctionMenu.builder().menuId("ACMSTOOL02").menuName("未使用通信定義抽出").build()));

    return menuList;
  }
}
