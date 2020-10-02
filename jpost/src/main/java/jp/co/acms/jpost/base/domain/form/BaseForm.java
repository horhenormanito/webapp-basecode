
package jp.co.acms.jpost.base.domain.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.co.acms.jpost.base.domain.message.ResultMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseForm implements Serializable {

  /** 画面ID */
  private String formId = "";
  /** 画面名 */
  private String formName = "";

  /** 処理結果メッセージ */
  private List<ResultMessage> resultMessages = new ArrayList<ResultMessage>();

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // Action
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  /** 画面アクション情報 */
  private final Action action = new Action();

  @Data
  @EqualsAndHashCode(callSuper = false)
  public class Action implements Serializable {

    /** アクションを特定するフォーム・アクションID(HTMLの'data-action-id'の値) */
    private String actionId = "";
//    /** アクションが発生した明細ID(HTMLの'data-meisai-id'の値) */
//    private String meisaiId = "";
    /** アクションが発生した明細行番号(HTMLの'data-row-number'の値) */
    private Integer rowNumber;

    /** アクションが発生した要素のname属性(JavaScriptイベントオブジェクトのevent.currentTarget.name) */
    private String srcElementName = "";
    /** アクションが発生した要素のid属性(JavaScriptイベントオブジェクトのevent.currentTarget.id) */
    private String srcElementId = "";
  }

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // 画面項目制御
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  /** フォーカス設定項目 */
  private String focusField = "";
  /** 画面項目制御：disable項目Set */
  private final Set<String> disabledFields = new HashSet<>();
  /** 画面項目制御：readonly項目Set */
  private final Set<String> readonlyFields = new HashSet<>();
  /** 画面項目制御：hide項目Set(display:none) */
  private final Set<String> hideFields = new HashSet<>();

  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  // ホーム画面のプロパティ
  // ////////////////////////////////////////////////////////////////////////////////////////////////////
  private String nextFormId;

  private String breadcrumbNextFormId;
}
