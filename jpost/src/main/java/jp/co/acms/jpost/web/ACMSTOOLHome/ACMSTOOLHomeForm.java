/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : フォームクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOLHome;

import java.util.List;

import jp.co.acms.jpost.base.domain.form.BaseForm;
import jp.co.acms.jpost.web.ACMSTOOLHome.dto.FunctionMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ACMSTOOLHomeForm extends BaseForm {

  /** 機能メニューリスト */
  public List<FunctionMenu> functionMenuList;

}
