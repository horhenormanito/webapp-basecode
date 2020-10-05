
package jp.co.acms.jpost.base.common.utils.error;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.acms.jpost.base.constant.WebConst;
import lombok.val;

@Controller
@RequestMapping("/error") // エラーページへのマッピング
public class HtmlErrorPageController implements ErrorController {

  /**
   * エラーページのパスを返す。
   *
   * @return エラーページのパス
   */
  @Override
  public String getErrorPath() {
    return WebConst.ERROR_URL;
  }

  /**
   * HTML レスポンス用の ModelAndView オブジェクトを返す。
   *
   * @param req リクエスト情報
   * @param mav レスポンス情報
   * @return HTML レスポンス用の ModelAndView オブジェクト
   */
  @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView myErrorHtml(HttpServletRequest req, ModelAndView mav) {

//    // エラー情報を取得
//    Map<String, Object> attr = getErrorAttributes(req);

    // HTTP ステータスを決める
    HttpStatus status = getHttpStatus(req);

    // HTTP ステータスをセットする
    mav.setStatus(status);

    // ビュー名を指定する
    // Thymeleaf テンプレートの場合は src/main/resources/templates/error.html
    mav.setViewName(WebConst.ERROR_VIEW);

    // 出力したい情報をセットする
    mav.addObject("status", status.value());
//    mav.addObject("error", attr.get("error"));
//    mav.addObject("trace", attr.get("trace"));

    // https://web.havincoffee.com/design/2015/06/1506171.html
    val messages = new ArrayList<String>();
    mav.addObject("messages", messages);

    switch (status) {

    case BAD_REQUEST:
      messages.add("不正な要求です。");
      messages.add("以下のブラウザ操作は使用しないでください。");
      messages.add("・URL(アドレスバー)の直接操作");
      messages.add("・ページの再読み込み、最新の情報に更新");
      messages.add("・前に戻る、次に進む");
      break;

    case FORBIDDEN:
      messages.add("アクセスできません。");
      messages.add("指定されたページへのアクセス権限がありません。");
      break;

    case NOT_FOUND:
      messages.add("ページが見つかりません。");
      messages.add("移動もしくは削除されたか、URLの入力間違いの可能性があります。");
      break;

    case INTERNAL_SERVER_ERROR:
      messages.add("サーバーエラーが発生しました。");
      messages.add("サーバーの問題でお探しのページを表示できません。");
      messages.add("再度時間をおいてアクセスしてください。");
      break;

    default:
      break;
    }

    return mav;
  }

  /**
   * エラー情報を抽出する。
   *
   * @param req リクエスト情報
   * @return エラー情報
   */
//  private static Map<String, Object> getErrorAttributes(HttpServletRequest req) {
//    // DefaultErrorAttributes クラスで詳細なエラー情報を取得する
//    ServletWebRequest swr = new ServletWebRequest(req);
//    DefaultErrorAttributes dea = new DefaultErrorAttributes(true);
//    return dea.getErrorAttributes(swr, true);
//  }

  /**
   * レスポンス用の HTTP ステータスを決める。
   *
   * @param req リクエスト情報
   * @return レスポンス用 HTTP ステータス
   */
  private static HttpStatus getHttpStatus(HttpServletRequest req) {

    // HTTP ステータスを決める
    Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    // 500
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    if (statusCode != null) {
      switch (statusCode.toString()) {
      case "400":
        status = HttpStatus.BAD_REQUEST;
        break;
      case "403":
        status = HttpStatus.FORBIDDEN;
        break;
      case "404":
        status = HttpStatus.NOT_FOUND;
        break;
      }
    }

    return status;
  }

}
