
var editFunc = {
    handleSaveLayout: function () {
        var e = $(".pageHTML").html();
        if (e != window.demoHtml) {
            saveLayout();
            window.demoHtml = e;
        };
    },
    // 需要一些改变，待考虑
    // handleAccordionIds: function() {
    //     var e = $(".pageHTML #myAccordion");
    //     var t = editFunc.randomNumber();
    //     var n = "panel-" + t;
    //     var r;
    //     e.attr("id", n);
    //     e.find(".panel").each(function (e, t) {
    //         r = "panel-element-" + editFunc.randomNumber();
    //         $(t).find(".panel-title").each(function (e, t) {
    //             $(t).attr("data-parent", "#" + n);
    //             $(t).attr("href", "#" + r)
    //         });
    //         $(t).find(".panel-collapse").each(function (e, t) {
    //             $(t).attr("id", r)
    //         })
    //     })
    // },
    // handleModalIds: function() {
    //     var e = $(".pageHTML #myModalLink");
    //     var t = editFunc.randomNumber();
    //     var n = "modal-container-" + t;
    //     var r = "modal-" + t;
    //     e.attr("id", r);
    //     e.attr("href", "#" + n);
    //     e.next().attr("id", n)
    // },
    // handleTabsIds: function() {
    //     var e = $(".pageHTML #myTabs");
    //     var t = editFunc.randomNumber();
    //     var n = "tabs-" + t;
    //     e.attr("id", n);
    //     e.find(".tab-pane").each(function (e, t) {
    //         var n = $(t).attr("id");
    //         var r = "panel-" + editFunc.randomNumber();
    //         $(t).attr("id", r);
    //         $(t).parent().parent().find("a[href=#" + n + "]").attr("href", "#" + r)
    //     })
    // },
    handleWidgetIds: function(id) {
        var data = editFunc.getWidgetId(id);
        data.e.attr("id", data.n);
        if ( id == 'carousel') {
            data.e.find(".carousel-indicators li").each(function (e, t) {
                $(t).attr("data-target", "#" + data.n)
            });
            data.e.find(".left").attr("href", "#" + data.n);
            data.e.find(".right").attr("href", "#" + data.n)
        }
    },
    getWidgetId: function(id) {
        var data = {};
        var t = editFunc.randomNumber();
        data.e = $('.pageHTML').find('#'+id);
        data.n = id + "-" + t;
        return data;
    },
    randomNumber: function() {
        return +new Date();
    },
    gridSystemGenerator: function() {
        $(".ncrow .preview input").bind("keyup", function () {
            var e = 0;
            var t = "";
            var n = false;
            var r = $(this).val().split(" ", 12);
            $.each(r, function (r, i) {
                if (!n) {
                    if (parseInt(i) <= 0) n = true;
                    e = e + parseInt(i);
                    t += '<div class="col-md-' + i + ' column"></div>'
                }
            });
            if (e == 12 && !n) {
                $(this).parent().next().children().attr('data-layout', r.join(','));
                $(this).parent().next().children().html(t);
                $(this).parent().prev().show()
            } else {
                $(this).parent().prev().hide()
            }
        });
    },
    removeElement: function() {
        $(".pageHTML").on("click", ".remove", function (e) {
            e.preventDefault();
            $(this).parent().remove();
            if (!$(".pageHTML .ncrow").length > 0) {
                editFunc.clearDemo();
            }
        });
    },
    settingElement: function () {
        $('.pageHTML').on("click", ".setting", function () {
            $('.modal-backdrop').fadeIn();
            var ele = $('#configuration');
            ele.show();
            var id = $(this).data('target');
            $('#' + id).show();
            ele.stop().animate({
                right: 0
            },500);
            // 创建当前操作组件的数据
            widgetHandle.createStore($(this));
            var styleid = $('#'+GlobalID).data('styleid');
            //Todo
        });
    },
    saveConfig: function () {
        $('#confBtn').click(function () {
            widgetHandle.saveFunc(GlobalID);
        });
    },
    closeConfig: function () {
        $('#cancelBtn').click(function () {
            widgetHandle.closeSetting();
        });
    },
    closeFunc: function () {
        var ele = $('#configuration');
        var w = ele.width();
        ele.stop().animate({
            right: -w
        },500, function () {
            $('.common-conf').hide();
            $('#configuration').hide();
            $('.modal-backdrop').fadeOut();
        });
    },
    clearDemo: function() {
        $(".pageHTML").empty();
    },
    removeMenuClasses: function() {
        $(".operate-buttons li").removeClass("active")
    },
    changeImgStyleActive: function (ele) {
        $('img.changeStyle').removeClass('active');
        ele.addClass('active');
    },
    handleJsIds: function (id) {
        // editFunc.handleModalIds();
        // editFunc.handleAccordionIds();
        // editFunc.handleTabsIds();
        editFunc.handleWidgetIds(id);
    },
    init: function () {
        editFunc.removeElement();
        editFunc.settingElement();
        editFunc.saveConfig();
        editFunc.closeConfig();
        editFunc.gridSystemGenerator();
    }
};
var Page = {
    widgetHTML: [
        '<li>',
        '<div class="box box-element ui-draggable">',
        '<span class="setting label label-primary">',
        '<i class="icon-cog"></i> 设置',
        '</span>',
        '<span class="drag label label-default">',
        '<i class="icon-move"></i> 拖动',
        '</span>',
        '<span class="remove label label-danger">',
        '<i class="icon-cancel"></i> 删除',
        '</span>',
        '<div class="preview">',
        '<p></p>',
        '</div>',
        '<div class="view"></div>',
        '</div>',
        '</li>'
    ],
    styleList: [
	    '<div>',
	    '<h3><i class="icon-puzzle"></i><b></b>选择组件样式</h3>',
	    '<div class="swiper-container styles">',
	    '<div class="swiper-wrapper">',
	    '</div>',
	    '<div class="swiper-button-next"></div>',
	    '<div class="swiper-button-prev"></div>',
	    '</div>',
	    '</div>'
	],
    init: function (url) {
        $.getJSON(url, function(result){
            var parent = $('#configuration').find('.conf-body');
            $.each(result, function (i, v) {
                var initData = {};
                initData.script = v.scriptHref;
                initData.properties = v.defaultProperties;
                wsCache.set(v.identity, initData);
                // 组件列表渲染
                var element = $('#widgetLists');
                element.append(Page.widgetHTML.join('\n'));
                element.find('.setting').eq(i).attr('data-target', v.identity);
                element.find('.preview p').eq(i).html(v.locallyName);
                element.find('.view').eq(i).append(v.styles[0].previewHTML);
                element.find('.view').eq(i).children().eq(0).attr('data-widgetidentity', v.identity);
                element.find('.view').eq(i).children().eq(0).attr('data-styleid', 0);
                //编辑器视图渲染
                var child = $('<div class="common-conf"></div>');
                var container = $('<div></div>');
                child.attr('id', v['identity']);
                child.append(Page.styleList.join('\n'));
                $.each(v.styles, function (key, val) {
                    var div = $('<div class="swiper-slide"></div>')
                    var img = $('<img class="center-block changeStyle">');
                    var p = $('<p></p>');
                    img.attr('src',val.thumbnail);
                    img.attr('data-styleid',val.id);
                    p.text(val.locallyName);
                    div.append(img);
                    div.append(p);
                    child.find('.swiper-wrapper').append(div);
                });
                child.append(v['editorHTML']);
                parent.append(child);

                $('.swiper-container.styles').swiper({
                    nextButton: '.swiper-button-next',
                    prevButton: '.swiper-button-prev',
                    slidesPerView: 4,
                    paginationClickable: true,
                    observer: true,
                    observeParents: true,
                    updateOnImagesReady : true
                });

            });
            Page.draggable();
        });
    },
    draggable: function () {
        $(".draggable-group .box").draggable({
            connectToSortable: ".column",
            helper: "clone",
            handle: ".drag",
            drag: function (e, t) {
                t.helper.width(400)
            },
            stop: function (e, t) {
                var oId = t.helper.find('.view').children().eq(0).attr('id');
                editFunc.handleJsIds(oId);
            }
        });
    }
};

var editPage = {};



editPage.init = function () {
    $(document.body).css("min-height", $(window).height() - 90);
    $(".pageHTML").css("min-height", $(window).height() - 160);
    $(".pageHTML, .pageHTML .column").sortable({
        connectWith: ".column",
        opacity: .35,
        handle: ".drag"
    });
    $(".draggable-group .ncrow").draggable({
        connectToSortable: ".pageHTML",
        helper: "clone",
        handle: ".drag",
        drag: function (e, t) {
            t.helper.width(400);
        },
        stop: function () {
            $(".pageHTML .column").sortable({
                opacity: .35,
                connectWith: ".column"
            });
        }
    });

    $("#editBtn").click(function () {
        $(document.body).removeClass("sourcepreview");
        $(document.body).addClass("edit");
        editFunc.removeMenuClasses();
        $(this).addClass("active");
        $('.edit').find('.sidebar').show();
        return false;
    });
    $("#previewBtn").click(function () {
        if ( $('#pageHTML').html() === '' ) return false;
        $(document.body).removeClass("edit");
        $(document.body).addClass("sourcepreview");
        editFunc.removeMenuClasses();
        $(this).addClass("active");
        $('.sourcepreview').find('.sidebar').hide();
        return false;
    });
    $("#clearBtn").click(function (e) {
        if ( $('#pageHTML').html() === '' ) return false;
        e.preventDefault();
        layer.confirm('页面会被清空？', {
            title: '警告',
            btn: ['重做','取消']
        }, function(index){
            editFunc.clearDemo();
            layer.close(index);
        });
    });

    $('.conf-body').on('click','img.changeStyle', function () {
        editFunc.changeImgStyleActive($(this));
    });
    editFunc.init();
    Page.init(initPath);
};

editPage.init();



