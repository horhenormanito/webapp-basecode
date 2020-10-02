package jp.co.acms.jpost.base.constant;

/**
 * 定数定義
 */
public interface WebConst {

  String UNKNOWN = "unknown";
  /** forward:/ */
  String FORWARD = "forward:/";
  /** redirect:/ */
  String REDIRECT = "redirect:/";

  // ---- クエリ文字列（URLパラメーター） ----
  /** クエリ文字列：アクションID */
  String QUERY_STRING_ACTION_ID = "action.actionId";
  /** クエリ文字列：action.actionId=init */
  String QUERY_STRING_INIT = "action.actionId=init";
  /** クエリ文字列：action.actionId=returned */
  String QUERY_STRING_RETURNED = "action.actionId=returned";

  /** ---- View ---- **/
  String ERROR_VIEW = "error";

  String FORBIDDEN_VIEW = "forbidden";

  String LOGIN_TIMEOUT_VIEW = "loginTimeout";

  /** ---- DateFormat ---- **/
  String LOCALDATE_FORMAT = "yyyy/MM/dd";

  String LOCALDATE_FORMAT_YYYYMM = "yyyy/MM";

  String LOCALDATETIME_FORMAT = "yyyy/[]M/[]d []H:[]m:[]s";

  String MAV_ERRORS = "errors";
  //
  /** ---- URLs ---- **/
  String ERROR_URL = "/error";

  String FORBIDDEN_URL = "/forbidden";

  String WEBJARS_URL = "/webjars/**";

  String STATIC_RESOURCES_URL = "/static/**";

  String API_BASE_URL = "/api/**";

  String APP_BASE_HOME_URL = "/home";

  String APP_BASE_FUNCTION_MENU_URL = "/functionMenuLink";

  String APP_BASE_BREADCRUMB_URL = "/breadcrumbLink";

  String APP_BASE_PATH = "ACMSTOOL/";

  String APP_BASE_HOME_PATH = "ACMSTOOLHome";

}
