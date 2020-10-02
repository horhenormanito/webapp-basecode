/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : ホーム画面
　*
 * 注意事項              : プロパティクラス
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

package jp.co.acms.jpost.web.ACMSTOOLHome;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import jp.co.acms.jpost.base.domain.properties.BaseProperties;
import lombok.Getter;

//@formatter:off
@PropertySource(value = "classpath:properties/ACMSTOOLHome/ACMSTOOLHome.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
//@formatter:on
@Component
@Getter
public class ACMSTOOLHomeProperties extends BaseProperties {

  // --------------------------------------------------------------------------------
  // 共通項目
  // --------------------------------------------------------------------------------
  @Value("${ACMSTOOLHome.formId}")
  private String formId = "ACMSTOOLHome";

  @Value("${ACMSTOOLHome.formName}")
  private String formName = "ACMSTOOLHome";

  // --------------------------------------------------------------------------------
  // PG独自定義
  // --------------------------------------------------------------------------------

  // --------------------------------------------------------------------------------
  // 画面項目定義
  // --------------------------------------------------------------------------------

}
