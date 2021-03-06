/**
 * Created by Administrator on 2015/12/23.
 */
define(function (require, exports, module) {
    var commonUtil = require("common");
    var ownerId =commonUtil.getQuery("ownerId");
    var layer=require("layer");
    exports.fromValidata=function(){
        $("#addArticleForm").validate({
            rules: {
                title:{
                    required: true,
                    maxlength:200
                },
                content:{
                    required: true,
                },
                articleSource: {
                    selrequired: "-1"
                },
                isSystem: {
                    selrequired: "-1"
                },
                OrderWeight:{
                    digits:true,
                }
            },
            messages: {
                title:{
                    required:"请输入文件名称",
                    maxlength:"文章标题不许超过200个字符"
                },
                content:{
                    required:"请输入内容"
                },
                isSystem: {
                    selrequired: "请选择文章类型"
                },
                articleSource: {
                    selrequired: "请选择文章来源"
                },
                OrderWeight:{
                    digits:"请输入数字",
                }
            },
            submitHandler: function (form, ev) {
                commonUtil.setDisabled("jq-cms-Save");
                editor.sync();
                $.ajax({
                    url: "/manage/article/saveArticle",
                    data: {
                        id:$("#hidArticleID").val(),
                        title:$("#title").val(),
                        ownerId:ownerId,
                        content: $("#content").val(),
                        isSystem: $("#isSystem").val(),
                        thumbUri: $("#thumbUri").val(),
                        description: $("#description").val(),
                        author: $("#author").val(),
                        articleSourceId: $("#articleSource").val(),
                        categoryId: $("#categoryId").val(),
                        orderWeight: $("#orderWeight").val()
                    },
                    type: "POST",
                    dataType: 'json',
                    success: function (data) {
                        if(data!=null)
                        {
                            var index=parseInt(data.code);
                            if(index==200)
                            {
                                layer.msg("保存成功！", {time: 2000})
                                $("#title").val("");
                                $("#linkUrl").val("");
                                editor.html("");
                                $("#author").val("");
                                $("#isSystem").val("-1");
                                $("#thumbUri").val("");
                                $("#description").val("");
                                $("#articleSource").val("-1");
                                $("#uploadThumbUri").attr("src","");
                                $("#orderWeight").val("50")
                                //layer.msg("操作成功,2秒后将自动返回列表页面",{time: 2000})
                                //setTimeout(function(){
                                //        window.location.href="http://"+window.location.host+"/"+"contents/contentsList?&ownerId="+ownerId;
                                //    }
                                //    ,1000);
                            }
                            if(index==500)
                                layer.msg("操作失败",{time: 2000})
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
    }
    exports.uploadImg=function(){
        uploadModule.uploadImg();
    }


    //上传图片模块
    var uploadModule={
        uploadImg:function(){
            $("#btnFile").jacksonUpload({
                url: "/manage/cms/imgUpLoad",
                name: "btnFile",
                enctype: "multipart/form-data",
                submit: true,
                method: "post",
                data:{
                    ownerId: ownerId
                },
                callback: function (json) {
                    if(json!=null)
                    {
                        var code=parseInt(json.code);
                        switch (code){
                            case 200:
                                $("#uploadThumbUri").attr("src", json.data.fileUrl);
                                $("#thumbUri").val(json.data.fileUri);
                                commonUtil.cancelDisabled("jq-cms-Save");
                                layer.msg("操作成功", {time: 2000});
                                break;
                            case 403:
                                layer.msg("文件格式错误,请上传jpg, jpeg,png,gif,bmp格式的图片", {time: 2000});
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

    //uploadModule.uploadImg();


    //var ue = UE.getEditor('content',{
    //    //默认的编辑区域宽度
    //    initialFrameWidth:1200,
    //    //默认的编辑区域高度
    //    initialFrameHeight:300,
    //
    //});
});