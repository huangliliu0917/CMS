/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

jQuery(window).load(function () {

    // Page Preloader
    jQuery('#status').fadeOut();
    jQuery('#preloader').delay(350).fadeOut(function () {
        jQuery('body').delay(350).css({'overflow': 'visible'});
    });
});

jQuery(document).ready(function () {

    // 调整下在iframe环境中 mainpanel的位置问题
    $('.mainpanel').css('margin-left', '0px');

    function adjustmainpanelheight() {
        // Adjust mainpanel height
        var docHeight = jQuery(document).height();
        if (docHeight > jQuery('.mainpanel').height())
            jQuery('.mainpanel').height(docHeight);
    }

    adjustmainpanelheight();


    // Tooltip
    jQuery('.tooltips').tooltip({container: 'body'});

    // Popover
    jQuery('.popovers').popover();

    // Close Button in Panels
    jQuery('.panel .panel-close').click(function () {
        jQuery(this).closest('.panel').fadeOut(200);
        return false;
    });

    // Form Toggles
    jQuery('.toggle').toggles({on: true});

    jQuery('.toggle-chat1').toggles({on: false});

    // Minimize Button in Panels
    jQuery('.minimize').click(function () {
        var t = jQuery(this);
        var p = t.closest('.panel');
        var h;
        if (!jQuery(this).hasClass('maximize')) {
            p.find('.panel-body').slideUp(0);
            p.find('.panel-footer').slideUp(0, function () {
                h = document.body.clientHeight;
                resizeParentIframe(h);
            });
            t.addClass('maximize');
            t.html('&plus;');
        } else {
            p.find('.panel-body, .panel-footer').slideDown(0);
            p.find('.panel-footer').slideDown(0, function () {
                h = document.body.clientHeight;
                resizeParentIframe(h);
            });
            t.removeClass('maximize');
            t.html('&minus;');
        }
        return false;
    });

    // 动态改变父级 iframe 高度，在 元素 .minimize 点击事件里触发
    function resizeParentIframe( height ) {
        $(parent.document).find('iframe').height(height);
    }

    // Add class everytime a mouse pointer hover over it
    jQuery('.nav-bracket > li').hover(function () {
        jQuery(this).addClass('nav-hover');
    }, function () {
        jQuery(this).removeClass('nav-hover');
    });

    // reposition_topnav();
    reposition_searchform();

    jQuery(window).resize(function () {

        if (jQuery('body').css('position') == 'relative') {

            jQuery('body').removeClass('leftpanel-collapsed chat-view');

        } else {

            jQuery('body').removeClass('chat-relative-view');
            jQuery('body').css({left: '', marginRight: ''});
        }

        reposition_searchform();
        // reposition_topnav();

    });


    /* This function will reposition search form to the left panel when viewed
     * in screens smaller than 767px and will return to top when viewed higher
     * than 767px
     */
    function reposition_searchform() {
        if (jQuery('.searchform').css('position') == 'relative') {
            jQuery('.searchform').insertBefore('.leftpanelinner .userlogged');
        } else {
            jQuery('.searchform').insertBefore('.header-right');
        }
    }

    // Sticky Header
    if (jQuery.cookie('sticky-header'))
        jQuery('body').addClass('stickyheader');

    // Sticky Left Panel
    if (jQuery.cookie('sticky-leftpanel')) {
        jQuery('body').addClass('stickyheader');
        jQuery('.leftpanel').addClass('sticky-leftpanel');
    }

    // Left Panel Collapsed
    if (jQuery.cookie('leftpanel-collapsed')) {
        jQuery('body').addClass('leftpanel-collapsed');
        jQuery('.menutoggle').addClass('menu-collapsed');
    }

    // Changing Skin
    var c = jQuery.cookie('change-skin');
    if (c) {
        jQuery('head').append('<link id="skinswitch" rel="stylesheet" href="css/style.' + c + '.css" />');
    }

    // Changing Font
    var fnt = jQuery.cookie('change-font');
    if (fnt) {
        jQuery('head').append('<link id="fontswitch" rel="stylesheet" href="css/font.' + fnt + '.css" />');
    }

    // Check if leftpanel is collapsed
    if (jQuery('body').hasClass('leftpanel-collapsed'))
        jQuery('.nav-bracket .children').css({display: ''});


    // Handles form inside of dropdown 
    jQuery('.dropdown-menu').find('form').click(function (e) {
        e.stopPropagation();
    });


});