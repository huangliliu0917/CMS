/**
 * Created by chendeyu on 2016/1/11.
 */
define(function (require, exports, module) {
    window.onload=function(){
        //var modelId= $("#categoryId").find("option:selected").attr("data-modelType");
        //var commonUtil = require("common");
        //commonUtil.setDisabled("jq-cms-Save");
        //var customerId =commonUtil.getQuery("customerId");
        //var widgetUrl="";
        //if(modelId==0) {
        //    widgetUrl = "/article/addArticle?customerId=" + customerId;
        //}
        //if (modelId == 1) {
        //    widgetUrl = "/notice/addNotice?customerId=" + customerId;
        //}
        //if (modelId == 2) {
        //    widgetUrl = "/video/addVideo?customerId=" + customerId;
        //}
        //if (modelId == 3) {
        //    widgetUrl = "/gallery/addGallery?customerId=" + customerId;
        //}
        //if (modelId == 4) {
        //    widgetUrl = "/download/addDownload?customerId=" + customerId;
        //}
        //if (modelId == 5) {
        //    widgetUrl = "/link/addLink?customerId=" + customerId;
        //}
        //$.get(widgetUrl+"&_="+Math.random(), function (html) {
        //    $("#widget").html(html);
        //});
        initViewModule.initView();
    }

    $("#categoryId").on("change",function(){
        initViewModule.initView();
    })

    var initViewModule={
        initView:function(){
            $(".jq-jupload-box").remove();
            var modelId= $("#categoryId").find("option:selected").attr("data-modelType");
            var commonUtil = require("common");
            commonUtil.setDisabled("jq-cms-Save");
            var customerId =commonUtil.getQuery("customerId");
            var widgetUrl="";
            if(modelId==0) {
                widgetUrl = "/article/addArticle?customerId=" + customerId;
            }
            if (modelId == 1) {
                widgetUrl = "/notice/addNotice?customerId=" + customerId;
            }
            if (modelId == 2) {
                widgetUrl = "/video/addVideo?customerId=" + customerId;
            }
            if (modelId == 3) {
                widgetUrl = "/gallery/addGallery?customerId=" + customerId;
            }
            if (modelId == 4) {
                widgetUrl = "/download/addDownload?customerId=" + customerId;
            }
            if (modelId == 5) {
                widgetUrl = "/link/addLink?customerId=" + customerId;
            }
            if(modelId!=-2) {
                $.get(widgetUrl + "&_=" + Math.random(), function (html) {
                    $("#widget").html(html);
                });
            }
        }
    }

    initViewModule.initView();

    $("#jq-cms-return").click(function(){
        var layer=require("layer");
        var layerIndex = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(layerIndex);
    })


});
