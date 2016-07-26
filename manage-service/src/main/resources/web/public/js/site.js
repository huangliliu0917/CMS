/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

/**
 * 关于站点的脚本
 * Created by CJ on 6/27/16.
 */
$(function () {

    var useTemplateModal = $('#useTemplateModal');

    var addSiteForm = $('#addSiteForm');

    addSiteForm.validate({
        rules: {
            name: {
                required: true
            },
            title: {
                required: true
            },
            description: {
                maxlength: 200
            },
            keywords: {
                maxlength: 200
            },
            domains: {
                required: true
                //remote: {
                //    url: "/site/isExistsDomain",     //后台处理程序
                //    type: "post",               //数据发送方式
                //    dataType: "json",           //接受数据格式
                //    data: {                     //要传递的数据
                //        domains: function () {
                //            return $("#domains").val();
                //        }
                //    }
                //},
            },
            homeDomain: {
                required: true
            },
            copyright: {
                required: true
            }
            // ,
            // regionId: {
            //     selrequired: "-1"
            // }
        },
        messages: {
            name: {
                required: "名称为必输项"
            },
            copyright: {
                required: "版权信息为必输项"
            },
            title: {
                required: "标题为必输项"
            },
            domains: {
                required: "请输入域名"
                //remote:"存在相同的域名"
            },
            homeDomain: {
                required: "必须存在一个主推域名"
            },
            description: {
                maxlength: "站点描述不能超过200个字符"
            },
            keywords: {
                maxlength: "站点关键字不能超过200个字符"
            }
            // ,
            // regionId: {
            //     selrequired: "请选择地区"
            // }
        }
    });

    $.cmsUploader($('#logo-uploader'), function (path) {
        $('input[name=tmpLogoPath]', addSiteForm).val(path);
    }, {
        allowedExtensions: ['jpeg', 'jpg', 'png', 'bmp'],
        itemLimit: 1,
        sizeLimit: 3 * 1024 * 1024
    });

    // 点赞的时候
    $('.template-lauds').click(function () {
        //  fa-thumbs-o-up fa-thumbs-up   o是没有
        var behavior;//用户行为，点赞或者取消
        var _this = $(this);
        if ($(this).find(".fa-thumbs-up").length == 0)
            behavior = 1;
        else
            behavior = 0;


        if(currentOwnerId==-1)//超级管理员，不参与点赞
            return;
            //alert("admin");

        if(laudUrl.indexOf("index")==-1){//如果不是静态界面测试 查看laudUrl的值
            $.ajax({
                url: laudUrl + $(this).find(".laudSpan").text(),
                type: 'post',
                dataType: 'json',
                data: {
                    ownerId: currentOwnerId,
                    behavior: behavior
                },
                success: function (data) {
                    var span = $('span', _this);
                    var newVal;
                    if(data){
                        if(behavior==1){
                            newVal = parseInt(span.text()) +1;
                           _this.find("i").attr("class","fa fa-thumbs-up");
                        }else{
                            newVal = parseInt(span.text()) -1;
                            _this.find("i").attr("class","fa fa-thumbs-o-up");
                        }
                        span.text(newVal);
                    }else{
                        layer.alert("服务器异常...");
                    }
                }
            })
        }else{
            var i = $('i', this);
            var padding;
            if (i.hasClass('fa-thumbs-o-up')) {
                padding = 1;
            } else {
                padding = -1;
            }

            i.toggleClass('fa-thumbs-o-up');
            i.toggleClass('fa-thumbs-up');
            var span = $('span', this);
            var newVal = parseInt(span.text()) + padding;
            span.text(newVal);
        }
    });
    var templateSiteId;
    //  使用模板的时候
    $('.template-use').click(function () {
        templateSiteId= $(this).parents(".template-site").attr("data-id");
        // 需要弹出一个对话框 确认使用的级别
        useTemplateModal.modal();
    });
    $('#confirmBtn').click(function(){
        var type=$(".modal-body").find("input[name='type']:checked").val();
        if(customerSiteId==null)//原型测试环境
            return;
        $.ajax({
            url:'/manage/template/use/'+templateSiteId+'/'+customerSiteId,
            data:{
                mode:type
            },
            type:'post',
            success:function(data){//应该是iframe内跳转
                window.location.href="/manage/site";
            }
        })
    });
    $('.template-preview').click(function () {
        // 效果应该是等同点击附近的 a
        $(this).closest('.site-items').find('a').get(0).click();
        return false;
    });

    $(document.body).find('.site-items').on('mouseenter', function () {
        $(this).children('.site-preview').stop().fadeIn();
    }).on('mouseleave', function () {
        $(this).children('.site-preview').stop().fadeOut();
    });
});
