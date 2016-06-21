/**
 * Created by chendeyu on 2015/12/29.
 */
define(function (require, exports, module) {
    $("#addColumnForm").validate({
        rules: {
            categoryName:{
                required: true
            },
            route:{
                remote: {
                    url: "/manage/route/isExistsRouteBySiteAndRule",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        siteId:$("#siteId").val(),
                        rule: function () {
                            return $("#route").val();
                        }
                    }
                },
                route:true
            },
            template:{
                route:true
            }
        },
        messages: {
            route:{
                route:"请使用非中文字符，且长度为1至50个字符",
                remote: "该栏目路由已经存在,或者内置关键字"
            },
            template:{
                route:"请使用非中文字符，且长度为1至50个字符",
            }
        },
        submitHandler: function (form, ev) {
            var commonUtil = require("common");
            commonUtil.setDisabled("jq-cms-Save");
            $.ajax({
                url: "/manage/category/saveCategory",
                data: {
                    siteId:$("#siteId").val(),
                    parentId: $("#parentId").val(),
                    name: $("#categoryName").val(),
                    model: $("#modelType").val(),
                    orderWeight: $("#orderWeight").val(),
                    rule:$("#route").val(),
                    template:$("#template").val(),
                    parentPath:$("#parentPath").val(),
                    routeType:$("#routeType").val()
                },
                type: "POST",
                dataType: 'json',
                success: function (data) {
                    var layer=require("layer");
                    if(data!=null)
                    {
                        var index=parseInt(data.code);
                        switch (index){
                            case 200:
                                $("#categoryName").val("")
                                $("#route").val("");
                                $("#template").val("")
                                layer.msg("操作成功",{time: 2000});
                                break;
                            case 204:
                                layer.msg("路由规则已经存在",{time: 2000});
                                break;
                            case 500:
                                layer.msg("操作失败",{time: 2000})
                                break;
                            case 502:
                                layer.msg("服务器错误",{time: 2000})
                                break;
                        }
                    }
                    commonUtil.cancelDisabled("jq-cms-Save");
                },
                error: function () {
                    commonUtil.cancelDisabled("jq-cms-Save");
                }
            });
            return false;
        },
        invalidHandler: function () {
            return true;
        }
    });

    $("#route").on("blur",function(){
        var value=$("#route").val();
        if(value!=''){
            $("#jq-cms-routeType").show();
        }else{
            $("#jq-cms-routeType").hide();
        }
    })
});