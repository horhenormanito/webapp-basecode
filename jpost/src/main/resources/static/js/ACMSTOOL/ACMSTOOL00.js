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

    const NAMESPACE = '.ACMSTOOL00';
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
            }
        });

        // 詳細リンク押下
        // bind all link 詳細 using .on event
        $("#search-result-list-tbl").on('click',
            '.detail-link',function(e) {
          ACMS.events.submit(e);
         });
    };

    // screen's action properties
    formJs.actions = {
        // [actions] your action(event) functions.

        // 検索ボタン押下
        'searchBtn' : function (e) {
          ACMS.events.submit(e);
        },
    };


    // utilities
    formJs.utils = {
        // [utils] screen's local utility functions.
    };

});
