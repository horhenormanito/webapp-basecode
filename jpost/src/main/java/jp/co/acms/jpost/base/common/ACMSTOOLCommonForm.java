package jp.co.acms.jpost.base.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.acms.jpost.web.ACMSTOOLHome.dto.Breadcrumb;
import jp.co.acms.jpost.web.ACMSTOOLHome.dto.FunctionMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class ACMSTOOLCommonForm {

  /** 次フォームID */
  private String nextFormId;

  /** 機能メニューリスト */
  public List<FunctionMenu> functionMenuList = new ArrayList<>();

  /** breadcrumbs */
  public List<Breadcrumb> breadcrumbs = new ArrayList<>();

}
