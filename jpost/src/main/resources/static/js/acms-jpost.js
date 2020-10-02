/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : メインJSモジュール
 *
 * 注意事項              : メインJavascriptファイル
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

var ACMS;
var formJs = {
    'vars' : {},
    'ready' : {},
    'bindActionHandler' : {},
    'utils' : {},
    'actions' : {},
    // home page js bind,utils property
    'homeReady' : {},
    'homeBindActionHandler' : {},
    'homeUtils' : {},
    'homeActions' : {}
};
Object.seal(formJs);

const FORM = {
   FUNCTION_MENU: 0,
   BREADCRUMB: 1,
   MAIN_CONTENT: 2
};
Object.freeze(FORM);


$(function() {
  'use strict';

  // landing/home page form ready function
  formJs.homeReady = function () {

  };

  // landing/home page form's bind action handler
  formJs.homeBindActionHandler = function () {
      $('[data-action-id]').each(function () {
          const $e = $(this);
          const actionId = $e.data('actionId');
          const handler = formJs.homeActions[actionId];
          if (!handler) {
              return true;
          }
          // --------------------------------------------------
          // bind handler.
          // --------------------------------------------------
          switch (actionId) {
          // Function menu
          case 'functionMenuLink':
          // breadcrumbs
          case 'breadcrumbLink':
              $e.on('click', handler);
              break;
          }
      });
  };

  // landing/home page form action properties
  formJs.homeActions = {
      // [actions] your action(event) functions.
      // 権限機能メニューボタン押下
      'functionMenuLink' : function (event) {
          // (1) 最大起動画面数のチェックを行う。
          // (2) 業務画面に遷移する。本画面をサイドバーよりモーダル起動した場合は新規ウィンドウで各業務画面を開く。

          // 起動する画面ID
          const nextFormId = event.target.dataset.nextFormId;

          // 業務画面に遷移する。
          window.name = nextFormId;
          $('#nextFormId').val(nextFormId);

          var data = {};

          if (event) {
              // --------------------------------------------------
              // event canceled
              // --------------------------------------------------
              event.preventDefault();
              event.stopImmediatePropagation();
              // --------------------------------------------------
              // actionInfo
              // --------------------------------------------------
              // Action情報を設定
              ACMS.utils.setActionInfo(data, event);
          }

          const form = document.forms[FORM.FUNCTION_MENU];
          Object.keys(data).forEach(function (key) {
              const value = data[key];
              if (value) {
                  const hidden = document.createElement('input');
                  hidden.type = 'hidden';
                  hidden.name = key;
                  hidden.value = value;
                  form.appendChild(hidden)
              }
          });

          // --------------------------------------------------
          // submit
          // --------------------------------------------------
          form.action += ('?' + data['action.actionId']);
          form.submit();
      },
   // breadcrumb linkの押下
      'breadcrumbLink' : function (event) {
          // 起動する画面ID
          const breadcrumbNextFormId = event.target.dataset.breadcrumbNextFormId;

          // 業務画面に遷移する。
          window.name = breadcrumbNextFormId;
          $('#breadcrumbNextFormId').val(breadcrumbNextFormId);

          var data = {};

          if (event) {
              // --------------------------------------------------
              // event canceled
              // --------------------------------------------------
              event.preventDefault();
              event.stopImmediatePropagation();
              // --------------------------------------------------
              // actionInfo
              // --------------------------------------------------
              // Action情報を設定
              ACMS.utils.setActionInfo(data, event);
          }

          const form = document.forms[FORM.BREADCRUMB];
          Object.keys(data).forEach(function (key) {
              const value = data[key];
              if (value) {
                  const hidden = document.createElement('input');
                  hidden.type = 'hidden';
                  hidden.name = key;
                  hidden.value = value;
                  form.appendChild(hidden)
              }
          });

          // --------------------------------------------------
          // submit
          // --------------------------------------------------
          form.action += ('?' + data['action.actionId']);
          form.submit();
      }

  };

  // landing/home page's utilities
  formJs.homeUtils = {
  };


  // ACMS common js function utilities
  ACMS = function () {
    var init, events, utils, notification;

      function toTarget(fieldName) {
          return "#" + utils.selectorEscape(fieldName) + ", " + "[name='"
                  + fieldName + "']";
      }

  // ■■■[events]
      // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
      events = {

      /** load */
        load : function () {

            // Error item control
            // const detailedErrorsMap = new Map();
            $.each(ACMS.vars.detailedErrors, function (i, e) {
                const field = e.fieldName;
                const message = e.message;
                utils.setFieldError(field, message);
            });
            // $('[data-toggle="tooltip"]').tooltip();

            // ResultMessage display
            ACMS.notification.show();

        },
          /** submit */
          submit : function (event) {

              var data = {};

              if (event) {
                // --------------------------------------------------
                  // event canceled
                  // --------------------------------------------------
                  event.preventDefault();
                  event.stopImmediatePropagation();
                  // --------------------------------------------------
                  // actionInfo
                  // --------------------------------------------------
                  // Action情報を設定
                  ACMS.utils.setActionInfo(data, event);
              }

              const form = document.forms[FORM.MAIN_CONTENT];
              Object.keys(data).forEach(function (key) {
                  const value = data[key];
                  if (value) {
                      const hidden = document.createElement('input');
                      hidden.type = 'hidden';
                      hidden.name = key;
                      hidden.value = value;
                      form.appendChild(hidden)
                  }
              });

              // --------------------------------------------------
              // submit
              // --------------------------------------------------
              form.action += ('?' + data['action.actionId']);
              form.submit();
          },

      };
      // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
      // ■■■[events]

      utils = {
        // [actions] your action(event) functions.
          setActionInfo : function (data, e) {
            if (!e) {
                  e = event;
              }
              if (e) {
                  const currentTarget = e.currentTarget;
                  if (currentTarget) {
                      // currentTarget属性
                      data['action.srcElementName'] = currentTarget.name;
                      data['action.srcElementId'] = currentTarget.id;
                      // data属性
                      if (currentTarget.dataset) {
                          const dataset = currentTarget.dataset;
                          data['action.actionId'] = dataset['actionId'];
                          // data['action.meisaiId'] = dataset['meisaiId'];
                          data['action.rowNumber'] = dataset['rowNumber']
                          if (dataset['fieldId']) {
                              data['action.srcElementId'] = dataset['fieldId'];
                          }
                      }
                  }
              }
          },

          /**
           * return $('form').attr('action').
           */
          getFormAction : function () {
              return $('form').attr('action');
          },

          selectorEscape : function (val) {
              return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g,
                      '\\$&');
          },

          clearFieldError : function (field) {
              $(field).removeClass("is-invalid");
              $(field).tooltip('dispose');
          },

          setFieldError : function (field, message) {
              $("form").find(toTarget(field)).each(function (i, e) {
                  utils.setError(e, message);
              });
          },

          setError : function (elem, message) {

              var $elem;
              if (elem instanceof jQuery) {
                  $elem = elem;
              } else {
                  $elem = $(elem);
              }
              var $e = $elem;

              switch ($elem.attr("type")) {
              case "checkbox":
              case "radio":
                  // チェックボックスまたはラジオボタンの場合はラベルにエラークラスを追加する
                  $elem.addClass("is-invalid");
                  $e = $elem.closest("label");
                  // ラベルが無い場合(チェックボックス(単一))
                  if (!$e.get(0)) {
                      $elem.after('<label>');
                      $e = $elem.parent();
                  }
                  break;
              }

              // エラークラス設定
              if (!$e.hasClass("is-invalid")) {
                  $e.addClass("is-invalid");
              }

              // ==============================
              // https://cccabinet.jpn.org/bootstrap4/components/tooltips
              // ==============================
              $e.tooltip({
                  title : message,
                  container : 'body',
                  placement : 'auto',
                  trigger : 'focus',
              }).on(
                      'shown.bs.tooltip',
                      function () {
                          // --------------------------------------------------
                          // tooltip表示後のマウスホイール含む縦横スクロール時に非表示にする
                          // --------------------------------------------------
                          // 画面全体の縦横スクロール
                          $(document).on('scroll wheel', function () {
                              $e.tooltip('hide');
                          });
                          // 明細内(div.dataTables_scrollBody)の縦横スクロール
                          $("div.dataTables_scrollBody").on('scroll wheel',
                                  function () {
                                      $e.tooltip('hide');
                                  });
                      });

              const card = $e.closest('.card');
              if (card && card.hasClass('collapsed-card')) {
                  card.CardWidget('expand');
              }
          },
      };

      // ■■■[init]
      // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
      init = function () {

          // href="#"の画面遷移を無効化
          $('a[href="#"]').attr("href", "javascript:void(0)");

          // --------------------------------------------------
          // [form]属性
          // --------------------------------------------------
          $('form')
          // HTML5のバリデーション機能(入力値チェック)を無効化
          .attr('novalidate', 'novalidate')
          // autocompleteを無効化
          .attr('autocomplete', 'off');

          // always display sidebar every init
          $('[data-widget="pushmenu"]').PushMenu("expand");

          return this;
      };
      // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
      // ■■■[init]

      // ■■■[notification]
      // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
      notification = {
      show : function () {

              const resultMessages = ACMS.vars.resultMessages;

              const errors = resultMessages.filter(function(m) {
                  if (m.id) {
                      return m.id.endsWith("E") || m.id.endsWith("Z");
                  }
                  return false;
              });
              const warnings = resultMessages.filter(function(m) {
                  if (m.id) {
                      return m.id.endsWith("W") || m.id.endsWith("Q");
                  }
                  return false;
              });
              const infos = resultMessages.filter(function(m) {
                  if (m.id) {
                      return m.id.endsWith("I");
                  }
                  return false;
              });

              // ==============================
              // https://adminlte.io/docs/3.0/javascript/toasts.html
              // ==============================
              var messages;
              var bg;
              var pos="notif-center";
              var title;
              if (errors.length) {
                  messages = errors;
                  bg = 'bg-danger';
                  title = 'エラー';
              } else if (warnings.length) {
                  messages = warnings;
                  bg = 'bg-warning';
                  title = '注意報';
              } else if (infos.length) {
                  messages = infos;
                  bg = 'bg-info';
                  title = '情報';
              } else if (resultMessages.length) {
                  messages = [resultMessages[0]];
                  bg = 'bg-danger';
                  title = 'エラー';
              } else {
                  messages = [];
                  bg = null;
                  title = 'メッセージ';
              }

              var message = "";
              $.each(messages, function() {
                  // message += (this.id + ":" + this.text) +'<br>';
                  message += (this.text) +'<br>';
              });

              if (message) {
                  const notifications = function(e) {
                      // 削除
                      $('.toast').remove();
                      // 作成
                      $(document).Toasts('create', {
                          icon: 'far fa-comments fa-lg',
                          title: title,
                          body: message,
                          class: bg + ' ' + pos,
                          autohide: true,
                          delay: 2000,
                      });
                  };
                  // 表示
                  notifications();
                  // click時イベント
                  $('.main-header-messages').click(notifications);
              }
          },
      }
      // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
      // ■■■[notification]


      // メンバ公開
      return {
        'init' : init,
          'events' : events,
          'utils' : utils,
          'notification' : notification
      };
  };

  // href="#"の画面遷移を無効化
  $('a[href="#"]').attr("href", "javascript:void(0)");

  // ==============================
  // slideToTop (↑icon)
  // ==============================
  const slideToTop = $("<div id='slideToTop'/>");
  slideToTop.html('<i class="fa fa-arrow-up"></i>');
  slideToTop.css({
    position: 'fixed',
    bottom: '10px',
    right: '10px',
    width: '30px',
    height: '30px',
    color: '#eee',
    'font-size': '',
    'line-height': '30px',
    'text-align': 'center',
    'background-color': '#222d32',
    cursor: 'pointer',
    'border-radius': '3px',
    'z-index': '99999',
    opacity: '.7',
    'display': 'none'
  });
  $('body').append(slideToTop);
  $(window).off('scroll.slideToTop').on('scroll.slideToTop', function() {
    if ($(window).scrollTop() >= 150) {
      if (!$(slideToTop).is(':visible')) {
        $(slideToTop).fadeIn('fast');
      }
    } else {
      $(slideToTop).fadeOut('fast');
    }
  });
  $(slideToTop).click(function() {
    $('body,html').animate({
      scrollTop: 0
    }, 'fast');
  });

  // TODO toasts
  // ==============================
  // https://adminlte.io/docs/3.0/javascript/toasts.html
  // ==============================
  /*$('.toasts').click(function() {
    $(document).Toasts('create', {
      icon: 'far fa-comments fa-lg',
      title: 'Message',
      body: 'メッセージサンプル(bg-danger)',
      class: 'bg-danger',

      // autohide
// autohide: true,
      delay: 2000,
    });
    return false;
  });*/

  // TODO エラー項目のサンプル
  // ==============================
  // https://cccabinet.jpn.org/bootstrap4/components/tooltips
  // ==============================
  /*$('.is-invalid').each(function(index, element) {
    const $e = $(element);
    $e.attr('data-toggle', 'tooltip').tooltip({
      title: '入力項目エラーの例',
      container: 'body',
    });
  })
  $('[data-toggle="tooltip"]').tooltip();*/

  // ==============================
  // http://www.detelu.com/fademover/
  // ==============================
  $('.content-wrapper').fadeMover({});

  // ==============================
  // https://tempusdominus.github.io/bootstrap-4/
  // ==============================
  $.fn.datetimepicker.Constructor.Default =
      $.extend({}, $.fn.datetimepicker.Constructor.Default, {
          dayViewHeaderFormat : 'YYYY年 MMMM',
          locale : moment.locale('ja', {
              week : {
                  dow : 1
              }
          }),
          format : 'L',
    });

  // Bootstrap 4でDatetimePickerを使いたい
  // https:// qiita.com/yaju/items/2cbe5e5914c5be08820a
  $('.date').datetimepicker({});

  // ==============================
  // https://datatables.net/
  // ==============================
  $.extend($.fn.dataTable.defaults, {

// dom: "<'row'<'col-sm-4'l><'col-sm-4 text-center'B><'col-sm-4 text-right'f>>"
// + "<'row'<'col-sm-5'i><'col-sm-7'p>><'row'<'col-sm-12'tr>>",
    dom: "<'row'<'col-sm-1'f>>"
        + "<'row'<'col-sm-5'i><'col-sm-7'p>>"
        + "<'row'<'col-sm-12'tr>>",

    language: {
      "sProcessing": "処理中...",
      "sLengthMenu": "_MENU_ 件表示",
      "sZeroRecords": "データはありません。",
      "sInfo": " _TOTAL_ 件中 _START_ から _END_ まで表示",
      "sInfoEmpty": " 0 件中 0 から 0 まで表示",
      "sInfoFiltered": "（全 _MAX_ 件より抽出）",
      "sInfoPostFix": "",
      "sSearch": "抽出:",
      "searchPlaceholder": "抽出したい値を入力",
      "sUrl": "",
      "sEmptyTable" : "データはありません。",
      "oPaginate": {
        "sFirst": "先頭",
        "sPrevious": '<i class="fas fa-angle-left"></i>',
        "sNext": '<i class="fas fa-angle-right"></i>',
        "sLast": "最終"
      }
    },

    // https://datatables.net/reference/option/
    "paging": true,
    "pageLength": 10,
    "lengthChange": false,
    "searching": false,
    "ordering": false,
    order : [],
    "info": true,
    // "autoWidth": false,
    "retrieve": true,

  // https://datatables.net/reference/option/scrollY
  // scrollY : '50vh',
  scrollCollapse : true,
  scrollX : true,
  });

});


