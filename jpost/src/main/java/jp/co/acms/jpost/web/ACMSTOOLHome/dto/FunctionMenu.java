/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : FunctionMenuのdto
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOLHome.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FunctionMenu {

  /** メニューID */
  private String menuId;

  /** メニュー名 */
  private String menuName;

}
