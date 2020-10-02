/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : ApexCommTaskのdto
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL00.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ApexCommTask {

  /** タスクID */
  String taskId;

  /** workingグループ */
  String workingGroup;

}
