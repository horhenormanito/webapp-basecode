/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy Screen only
　*
 * 注意事項              : フォームクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOL99;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import jp.co.acms.jpost.base.domain.form.BaseForm;
import jp.co.acms.jpost.web.ACMSTOOL00.dto.ApexCommTask;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ACMSTOOL99Form extends BaseForm {

  private String taskId;

  private String workingGroup;

}
