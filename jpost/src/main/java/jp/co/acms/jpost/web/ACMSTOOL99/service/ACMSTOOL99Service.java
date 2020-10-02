/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : サービスインターフェース
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */
package jp.co.acms.jpost.web.ACMSTOOL99.service;

import jp.co.acms.jpost.web.ACMSTOOL99.ACMSTOOL99Form;

public interface ACMSTOOL99Service {

  /**
   * 初期表示
   *
   * @param form ACMSTOOL00Form
   */
  void init(ACMSTOOL99Form form);
}
