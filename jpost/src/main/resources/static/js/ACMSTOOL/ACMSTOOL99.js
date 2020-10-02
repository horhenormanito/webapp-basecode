/*
 * システム名             : ACMS Apex可視化ツール
 * サブシステム名       : Dummy screen only
 *
 * 注意事項              : Javascriptファイル
 *
 * 更新履歴
 * 2020/10/01    GDC)George.Carlon    新規作成
 */

$(function () {
    'use strict';

    const NAMESPACE = '.ACMSTOOL99';
    const CHANGE = 'change' + NAMESPACE;
    const BLUR = 'blur' + NAMESPACE;
    const CLICK = 'click' + NAMESPACE;

    // onload, screen form ready function
    formJs.ready = function () {
    };

    // bind action handler properties
    formJs.bindActionHandler = function () {
        $('[data-action-id]').each(function () {
            const $e = $(this);
            const actionId = $e.data('actionId');
            const handler = formJs.actions[actionId];
            if (!handler) {
                return true;
            }
            // --------------------------------------------------
            // bind handler.
            // --------------------------------------------------
            switch (actionId) {
              // 検索ボタン押下
              case 'searchBtn':
                $e.off(CLICK).on(CLICK, handler);
                break;
              case 'detailLink':
                  $e.off(CLICK).on(CLICK, handler);
                  break;
            }
        });
    };

    // screen's action properties
    formJs.actions = {
        // [actions] your action(event) functions.

        // 検索ボタン押下
        'searchBtn' : function (e) {
          ACMS.events.submit(e);
        },
        // 詳細リンク押下
        'detailLink' : function (e) {
          ACMS.events.submit(e);
        },
    };


    // utilities
    formJs.utils = {
        // [utils] screen's local utility functions.
    };

});
