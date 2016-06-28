/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

define(function(require, exports, module) {
    require('../../css/slick/slick.css');
    require('../../css/slick/slick-theme.css');
    require('../../libs/slick.min.js');

    var Config = {
        init: function (url) {
            $.getJSON(url, function(result){
                var parent = $('#configuration').find('.conf-body');
                $.each(result, function (i, v) {
                    var child = $('<div class="common-conf"></div>');
                    child.attr('id', v['identity']);
                    child.append(v['editorHTML']);
                    parent.append(child);
                });
            });
        }
    };
    exports.init = function () {
        $(".common-conf .styles").slick({
            infinite: false,
            speed: 300,
            slidesToShow: 4,
            slidesToScroll: 4
        });

        //编辑器视图初始化
        Config.init('data/widget.json');//url
    };
});