/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

/**
 * Created by Neo on 2016/7/21.
 */

/* 使用 sessionStorage 存储操作数据*/
var wsCache = new WebStorageCache({storage: 'sessionStorage'});
/* 判断是支持本地存储，如果不能无法完成后续操作*/
if (!wsCache.isSupported()) {
    layer.alert('您的浏览器无法支持页面功能，推荐使用360极速浏览器，再页面进行操作。');
}

/**
 * GlobalID 全局参数，用于存储当前操作组件的唯一 ID
 * identity 全局参数，用于存储当前操作控件的唯一标示
 */
var GlobalID, identity;

/**
 *  初始化 wsCache.get(id) 若为 null 组件初始化状态，该组件的 properties 为对应控件的默认 properties
 *  若不为 null 组件再次操作，返回该组件的 properties，可为空
 * @param id 传入GlobalID;
 * @returns 返回对应properties
 */
function widgetProperties(id) {
    var ele = $('#' + id);
    var identity = ele.attr('data-widgetidentity');
    var dataCache = wsCache.get(id);
    if (identity) {
        if (dataCache) {
            return dataCache.properties;
        } else {
            return wsCache.get(identity).properties;
        }
    }
}

/**
 * 组件数据控制逻辑
 * getEditAreaElement: 获取当前组件对应编辑器的编辑区域 jQuery 对象
 * getIdentity: 获取当前组件对应的控件 Identity 标示
 * createStore: 初始化GlobalID
 * setStroe: 设置操作组件的 properties 本地数据存储
 * getGlobalFunc: 获取GlobalID
 * initFunc: 动态调用组件初始化方法
 * saveFunc：动态调用组件保存方法
 */
var widgetHandle = {
    getEditAreaElement: function (dataId) {
        var $DOM = '';
        $('.common-conf').each(function () {
            var oId = $(this).attr('data-id');
            if (editFunc.contrastString(oId, dataId)) {
                $DOM = $(this).children().eq(1);
            }
        });
        return $DOM;
    },
    getIdentity: function (globaId, callback) {
        identity = $('#' + globaId).attr('data-widgetidentity');
        callback && callback(identity);
    },
    createStore: function (globaId) {
        var data = widgetProperties(globaId);
        if (wsCache.get(globaId) == null) widgetHandle.setStroe(globaId, data);
    },
    openEditor: function (ele) {
        var globaId = $(ele).siblings('.view').children().attr('id');
        GlobalID = globaId;
        updataWidgetEditor(globaId, widgetProperties(globaId));
    },
    setStroe: function (id, data) {
        if (data) {
            wsCache.set(id, {'properties': data});
        } else {
            wsCache.set(id, {'properties': {}});
        }
    },
    saveFunc: function (id) {
        var $DOM = widgetHandle.getEditAreaElement(identity);
        CMSWidgets.saveComponent(id, {
            onSuccess: function (ps) {
                if (ps !== null) {
                    updataCompoentPreview(id, ps);
                }
                CMSWidgets.closeEditor(GlobalID, identity, $DOM);
                $DOM.children('.borderBoxs').remove();
                editFunc.closeFunc();
            },
            onFailed: function (msg) {
                layer.msg(msg)
            }
        }, $DOM);
    },
    closeSetting: function () {
        var $DOM = widgetHandle.getEditAreaElement(identity);
        CMSWidgets.closeEditor(GlobalID, identity, $DOM);
        $DOM.children('.borderBoxs').remove();
        editFunc.closeFunc();
    }
};

/**
 * 更新控件编辑器
 */
function updataWidgetEditor(globalID, properties) {
    var ele = $('#' + globalID);
    var widgetId = ele.attr('data-widgetidentity');
    var DATA = {
        "widgetIdentity": widgetId,
        "properties": properties,
        "pageId": pageId
    };
    Util.ajaxHtml(
        widgetEditor,
        JSON.stringify(DATA),
        function (html) {
            if (html) {
                var container = editFunc.findCurrentEdit(globalID).children().eq(1);
                container.append(html);
                widgetHandle.getIdentity(globalID, function (identity) {
                    var $DOM = widgetHandle.getEditAreaElement(identity);
                    dynamicLoading.js(wsCache.get(identity).script);
                    if (CMSWidgets) CMSWidgets.openEditor(globalID, identity, $DOM);
                });
                cmdColorPicker();
            }
        }
    );
}

/**
 * 更新预览视图
 */
function updataCompoentPreview(globalID, properties) {
    var ele = $('#' + globalID);
    var widgetId = ele.attr('data-widgetidentity');
    var styleId = ele.attr('data-styleid');
    var DATA = {
        "widgetIdentity": widgetId,
        "styleId": styleId,
        "properties": properties,
        "pageId": pageId,
        "componentId": globalID
    };
    var loading = layer.load(2);
    $.ajax({
        type: 'POST',
        url: component,
        contentType: "application/json; charset=utf-8",
        dataType: 'html',
        data: JSON.stringify(DATA),
        success: function (html, textStatus, jqXHR) {
            if (html) {
                // 操作成功后才更新数据
                widgetHandle.setStroe(globalID, properties);

                var updateHtml = $(html);
                updateHtml.attr({
                    'id': globalID,
                    'data-widgetidentity': widgetId,
                    'data-styleid': styleId,
                    'widgetidentity': widgetId
                });
                ele.parent('.view').html(updateHtml);
                editFunc.closeFunc();
                var path = jqXHR.getResponseHeader('cssLocation');
                if (path) dynamicLoading.css(path);
            }
            layer.close(loading);
        },
        error: function (response, textStatus, errorThrown) {
            if (response.status === 400) {
                layer.msg('参数错误或者不足，该控件将无法保存。', {time: 2000});
            }
            try {
                layer.close(loading);
                var data = $.parseJSON(response.responseText);
                layer.msg(data.msg || '服务器错误，请稍后操作。', {time: 2000});
            } catch (e) {
                console.error(e);
                layer.msg('服务器错误，请稍后操作。', {time: 2000});
            }
        }
    });
}

/**
 *
 * @param type 表示查询的数据类别 比如 findGalleryItem
 * @param parameter 查询参数 可选
 *
 * @param onSuccess 成功回调 参考 http://api.jquery.com/jquery.ajax/#success
 * @param onError 错误回调 参考 http://api.jquery.com/jquery.ajax/#error
 */
function getDataSource(type, parameter, onSuccess, onError) {
    if (_CMS_DataSource_URI == null) {
        console.log('invoke getDataSource in po..');
        // TODO 根据不同的type给予不同的mock数据
        onSuccess({});
        return;
    }
    var url = _CMS_DataSource_URI + type;
    if (parameter != null) {
        url = url + "/" + parameter;
    }
    console.error("url:" + url);
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        async: !testMode,
        success: onSuccess,
        error: onError
    });
}

/**
 * 动态加载组件的 JS文件
 * @type {{css: dynamicLoading.css, js: dynamicLoading.js}}
 */
var dynamicLoading = {
    init: function (type, path) {
        if (!path || path.length === 0) {
            console.error('参数 "path" 是必需的！');
        }
        var exist = false;
        var handler = (type === 'css');
        var ele = handler ? $('link') : $('script');
        $.each(ele, function (i, v) {
            var attr = handler ? 'href' : 'src';
            var argv = $(v).attr(attr);
            if (argv && argv.indexOf(path) != -1) {
                exist = true;
            }
        });
        if (!exist) {
            var lastElement = ele.last();
            var addEle = handler ? $('<link>') : $('<script></script>');
            if (handler) {
                addEle.attr({'rel': 'stylesheet', 'href': path});
            } else {
                addEle.attr('src', path);
            }
            lastElement.after(addEle);
        }
    },
    css: function (path) {
        dynamicLoading.init('css', path);
    },
    js: function (path) {
        dynamicLoading.init('js', path);
    }
};

/**
 * 所有参数都有初始化默认
 * @param obj [] 参数对象
 * @param obj.ui [String] 绑定元素的 class 或者 id 如：#test
 * @param obj.inputName [String] imput[type=file] name值，传给接口的参数名 默认 file
 * @param obj.maxWidth [Number] 图片宽度规格 默认 1920
 * @param obj.maxHeight [Number] 图片高度 默认 1080
 * @param obj.maxFileCount [Number] 限制上传的图片数量，-1 不限制参数为 默认 -1
 * @param obj.uploadUrl [String] 上传图片接口地址 默认 /manage/cms/resourceUpload
 * @param obj.successCallback [Function] 上传成功后回调函数 默认为空
 * @param obj.deleteUrl [String] 删除图片接口地址. 默认 /manage/cms/deleteResource
 * @param obj.deleteCallback [Function] 删除成功后回调函数 默认为空
 * @param obj.isCongruent [Boolean] 是否启用完全相等，false 不启用，默认 false
 */
function uploadForm(obj) {
    var ui = obj.ui,
        method = obj.method || 'POST',
        inputName = obj.inputName || 'file',
        maxWidth = obj.maxWidth || 9999,
        maxHeight = obj.maxHeight || 9999,
        maxFileCount = obj.maxFileCount || -1,
        uploadUrl = obj.uploadUrl || '/manage/cms/resourceUpload',
        successCallback = obj.successCallback || function () {
        },
        deleteUrl = obj.deleteUrl || '/manage/cms/deleteResource',
        deleteCallback = obj.deleteCallback || function () {
        },
        sign = obj.isCongruent || false;
    var uploadFile = $(ui).uploadFile({
        url: uploadUrl,
        method: method,
        showFileCounter: false,
        returnType: "json",
        fileName: inputName,
        multiple: true,
        maxFileCount: maxFileCount,
        dragDropStr: "<span>拖拽至此</span>",
        abortStr: "中止",
        cancelStr: "取消",
        deletelStr: "删除",
        uploadStr: "上传图片",
        maxFileCountErrorStr: " 不可以上传. 最大数量: ",
        showPreview: true,
        statusBarWidth: 360,
        previewHeight: "60px",
        previewWidth: "60px",
        showDelete: true,
        autoSubmit: false,
        onSuccess: successCallback,
        onSelect: function (files) {

            var file = files[0];
            var reader = new FileReader();
            reader.onload = function (e) {
                var data = e.target.result;
                var image = new Image();
                image.src = data;
                image.onload = function () {
                    var width = image.width;
                    var height = image.height;
                    var vWidth = sign ? width == maxWidth : width >= maxWidth;
                    var vHeight = sign ? height == maxHeight : height >= maxHeight;
                    verifySize(sign, vWidth, vHeight, function () {
                        uploadFile.startUpload()
                    });
                };

            };
            reader.readAsDataURL(file);

            return true;
        },
        onError: function (files, status, message, pd) {
            pd.statusbar.hide();
            layer.msg('上传失败，请稍后再说', {time: 2000});
        },
        deleteCallback: function (data, pd) {
            $.ajax({
                type: 'DELETE',
                url: deleteUrl + "?path=" + data.path,
                error: function (jqXHR, textStatus, errorThrown) {
                    //等待处理
                    layer.msg('服务器错误，请稍后操作。', {time: 2000});
                }
            });
            pd.statusbar.hide();
        }
    });
}


/**
 *
 * @param congruent [Boolean] 是否启用完全相等
 * @param vWidth [Boolean]  宽度验证返回值
 * @param vHeight [Boolean] 高度验证返回值
 * @param callback [Function] 验证成功后的回调函数
 */
function verifySize(congruent, vWidth, vHeight, callback) {
    var widthText = congruent ? '图片宽度不符合限制' : '图片宽度超出限制';
    var heightText = congruent ? '图片高度不符合限制' : '图片高度超出限制';

    if (congruent) {
        if (!vWidth) layer.msg(widthText);
        if (!vHeight) layer.msg(heightText);
        if (vWidth === true && vHeight === true) callback();
    } else {
        if (vWidth) layer.msg(widthText);
        if (vHeight) layer.msg(heightText);
        if (!vWidth === true && !vHeight === true) callback();
    }
}

var Util = {
    /**
     *
     * @param url 接口地址
     * @param option 传递参数 JSON格式
     * @param callback 成功回调
     */
    ajaxHtml: function (url, option, callback) {
        var loading = layer.load(2);
        $.ajax({
            type: 'POST',
            url: url,
            contentType: "application/json; charset=utf-8",
            dataType: 'html',
            data: option,
            success: function (data, textStatus, jqXHR) {
                if ($.isFunction(callback)) {
                    callback(data, textStatus, jqXHR);
                    layer.close(loading);
                }
            },
            statusCode: {
                400: function () {
                    layer.msg('参数错误或者不足，该控件将无法保存。', {time: 2000});
                }
            },
            error:function(response, textStatus, errorThrown){
                try{
                    layer.close(loading);
                    var data = $.parseJSON(response.responseText);
                    layer.msg(data.msg || '服务器错误，请稍后操作。', {time: 2000});
                } catch (e) {
                    console.error(e);
                    layer.msg('服务器错误，请稍后操作。', {time: 2000});
                }
            }
        });
    }
};
/**
 * jquery.datatable.js 简单封装
 * @type {{createTable: TableData.createTable, selectSingleRow: TableData.selectSingleRow}}
 */
var TableData = {
    /**
     * 创建一个Table
     * @param element 插件绑定的元素 jquery对象
     * @param ajaxData ajax的一些配置项，Object类型
     * @param flag 启用单选或者多选 Boolean类型
     * @param config 插件的配置项 Object类型
     * @param callback 选取数据后执行的回调函数， Function类型
     */
    createTable: function (element, ajaxData, flag, config, callback) {
        var table = element.DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": ajaxData,
            "ordering": false,

            "columns": config.columns,
            "columnDefs": config.columnDefs || [],
            "lengthMenu": config.lengthMenu || [],
            "displayLength": config.displayLength || '',

            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }

        });

        if (flag !== '') {
            element.find('tbody').off('click', 'tr');
            if (flag) {
                element.find('tbody').on('click', 'tr', function () {
                    $(this).toggleClass('selected');
                });
                this.selectRowData(table, callback);
            } else {
                element.find('tbody').on('click', 'tr', function () {
                    if ($(this).hasClass('selected')) {
                        $(this).removeClass('selected');
                    }
                    else {
                        table.$('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                });
                this.selectRowData(table, callback);
            }
        }
    },
    /**
     * 获取图片数据函数
     * @param member 绑定table的对象
     * @param callback callback 选取数据后执行的回调函数， Function类型
     */
    selectRowData: function (member, callback) {
        var $container = $('#selectDataTable');
        var $ele = $container.find('.js-select-btn');
        $ele.off('click');
        $ele.on('click', function () {
            var ARRAY = [];
            var arr = member.rows('.selected').data();
            var len = arr.length;
            for (var i = 0; i < len; i++) {
                ARRAY.push(arr[i]);
            }
            if ($.isFunction(callback)) {
                callback(ARRAY);
            }
        });
    }
};

/*临时拾色器公共方法*/
function cmdColorPicker() {
    $('.color-picker').each(function () {
        var color = $(this).val() || 'transparent';
        $(this).spectrum({
            color: color,
            allowEmpty: true,
            chooseText: "确定",
            cancelText: "取消",
            preferredFormat: "#",
            showButtons: false,
            showAlpha: true,
            showInitial: true,
            showInput: true,
            move: function (color) {
                if (color) $(this).val(color.toRgbString());
            },
            hide: function (color) {
                if (color) $(this).val(color.toRgbString());
            },
            change: function (color) {
                if (color) $(this).val(color.toRgbString());
            }
        });
    });
}

// 解决两个Low插件耦合度太高问题
var CommonPlugin = {
    //TODO
    uploadCallBackData: [],
    popover: function () {

        $('body').append($('#templateHtml').html());
        var $container = $('#selectDataTable');

        $container.modal();

        $container.on('hide.bs.modal', function () {
            $(this).off('hide.bs.modal');
            $(this).remove();
        });

    }
}
// 编辑器交互
$('.conf-body').on('change', 'input[name=layout]', function () {
    var ele = $('.js-topnavbar-width');
    var input = ele.find('input');
    if ($(this).val() == 'topnavbar-full') {
        ele.removeClass('show').addClass('hide');
    }
    if ($(this).val() == 'topnavbar-center') {
        ele.removeClass('hide').addClass('show');
    }
});

// 左侧动态高度
var draggableGroup = $('#container').find('.sidebar > .draggable-group');
var draggableGroupHeight = draggableGroup.height();
var firstOne = draggableGroup.children('dl').eq(0).height();
console.log(draggableGroupHeight)
draggableGroup.find('.boxes').css({
    height: (draggableGroupHeight - firstOne - 96) * 0.85
});