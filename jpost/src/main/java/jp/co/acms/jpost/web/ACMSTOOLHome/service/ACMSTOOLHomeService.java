/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
 *
 * 注意事項              : サービスインターフェース
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOLHome.service;

import jp.co.acms.jpost.web.ACMSTOOLHome.ACMSTOOLHomeForm;

public interface ACMSTOOLHomeService {

  /**
   * 初期表示
   *
   * @param form ACMSTOOLHomeForm
   */
  void init(ACMSTOOLHomeForm form);

}
