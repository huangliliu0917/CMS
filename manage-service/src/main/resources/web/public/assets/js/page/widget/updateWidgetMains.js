/**
 * Created by chendeyu on 2016/3/17.
 */
define(function (require, exports, module) {
    var commonUtil = require("common");
    var ownerId = commonUtil.getQuery("ownerId");
    var layer = require("layer");
    exports.fromValidata = function () {
        $("#updateWidgetMainsForm").validate({
            rules: {
                name: {
                    required: true,
                },
                description: {
                    required: true,
                },
                thumbUri: {
                    required: true,
                },
                widgetTypeId: {
                    selrequired: "-1",
                },
                txtOrderWeight: {
                    required: true,
                    digits: true,
                }
            },
            messages: {
                widgetTypeId: {
                    selrequired: "请选择类型",
                }
            },
            submitHandler: function (form, ev) {
                var commonUtil = require("common");
                commonUtil.setDisabled("jq-cms-Save");
                var thumbUri = $("#thumbUri").val();
                if (thumbUri == null || thumbUri == "") {
                    layer.msg("请先上传图片", {
                        icon: 2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        commonUtil.cancelDisabled("jq-cms-Save");
                    });
                }
                else {
                    $.ajax({
                        url: "/manage/widget/saveWidgetMains",
                        data: {

                            name: $("#name").val(),
                            id: $("#hidWidgetTypeID").val(),
                            widgetTypeId: $("#widgetTypeId").val(),
                            description: $("#description").val(),
                            imageUri: $("#thumbUri").val(),
                            resourceUri: $("#resourceUri").val(),
                            resourceEditUri: $("#resourceEditUri").val(),
                            orderWeight: $("#txtOrderWeight").val(),
                            template: $("#uploadWidgetUri").val(),
                            editTemplate: $("#uploadWidgetEditUri").val(),
                            defaultsProperty: $("#widgetDefaultProperty").val()
                        },
                        type: "POST",
                        dataType: 'json',
                        success: function (data) {
                            var layer = require("layer");
                            if (data != null) {
                                var index = parseInt(data.code);
                                if (index == 200) {
                                    var layer = require("layer");
                                    layer.msg("操作成功,即将返回列表", {
                                        icon: 1,
                                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                    }, function () {
                                        window.location.href = "http://" + window.location.host + "/" + "widget/widgetMainsList";
                                    });
                                }
                                if (index == 500){
                                    layer.msg("操作失败,请联系管理员", {
                                        icon: 2,
                                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                    }, function () {
                                        commonUtil.cancelDisabled("jq-cms-Save");
                                    });
                                }
                                if(index==501){
                                    layer.msg("默认属性格式有误,请检查格式", {
                                        icon: 2,
                                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                    }, function () {
                                        commonUtil.cancelDisabled("jq-cms-Save");
                                    });
                                }
                            }
                        },
                        error: function () {
                            commonUtil.cancelDisabled("jq-cms-Save");
                        }
                    });
                }
                return false;
            },
            invalidHandler: function () {
                return true;
            }
        });
    }

    exports.uploadImg = function () {
        uploadModule.uploadImg();
        //uploadModule.uploadWidget();
    }

    //上传图片模块
    var uploadModule = {
        uploadImg: function () {
            $("#btnFile").jacksonUpload({
                url: "/manage/cms/imgUpLoad",
                name: "btnFile",
                enctype: "multipart/form-data",
                submit: true,
                method: "post",
                data: {
                    ownerId: ownerId
                },
                callback: function (json) {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                $("#uploadThumbUri").attr("src", json.data.fileUrl);
                                $("#thumbUri").val(json.data.fileUri);
                                layer.msg("操作成功", {
                                    icon: 1,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    commonUtil.cancelDisabled("jq-cms-Save");
                                });
                                break;
                            case 403:
                                layer.msg("文件格式错误", {
                                    icon: 2,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                })
                                break;
                            case 502:
                                layer.msg("服务器错误,请稍后再试", {time: 2000});
                                break;
                        }
                    }
                },
                timeout: 30000,
                timeout_callback: function () {
                    layer.msg("图片上传操作", {time: 2000});
                }
            });
        },

        uploadWidget: function () {
            $("#btnFile1").jacksonUpload({
                url: "/manage/cms/widgetUpLoad",
                name: "btnFile1",
                enctype: "multipart/form-data",
                submit: true,
                method: "post",
                data: {
                    ownerId: ownerId
                },
                callback: function (json) {
                    if (json != null) {
                        var code = parseInt(json.code);
                        switch (code) {
                            case 200:
                                $("#uploadWidgetUri").val(json.data.fileContent);
                                $("#resourceUri").val(json.data.fileUri);
                                layer.msg("操作成功", {
                                    icon: 1,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                }, function () {
                                    commonUtil.cancelDisabled("jq-cms-Save");
                                });
                                break;
                            case 403:
                                layer.msg("文件格式错误,请上传.html格式的文件", {
                                    icon: 2,
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                })
                                break;
                            case 502:
                                layer.msg("服务器错误,请稍后再试", {time: 2000});
                                break;
                        }
                    }
                },
                timeout: 30000,
                timeout_callback: function () {
                    layer.msg("图片上传操作", {time: 2000});
                }
            });
        }

    }

});

