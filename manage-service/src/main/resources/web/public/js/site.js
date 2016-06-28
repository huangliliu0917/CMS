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
            },
            regionId: {
                selrequired: "-1"
            }
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
            },
            regionId: {
                selrequired: "请选择地区"
            }
        }
    });


    function logoUploaded(id, name, responseJSON) {
        // newUuid is the path
        console.log(responseJSON.newUuid);
    }

    function logoOnUpload() {
        //maybe in protype
        console.log('logoOnUpload');
        console.log.apply(console, arguments);
    }

    $('#logo-uploader').fineUploader({
        template: top.$('#qq-template').get(0),
        request: {
            inputName: 'file',
            // endpoint: 'http://mycms.51flashmall.com:8080/manage/upload/fine'
            endpoint: uploadFileUrl
        },
        thumbnails: {
            placeholders: {
                waitingPath: 'http://resali.huobanplus.com/cdn/jquery-fine-uploader/5.10.0/placeholders/waiting-generic.png',
                notAvailablePath: 'http://resali.huobanplus.com/cdn/jquery-fine-uploader/5.10.0/placeholders/not_available-generic.png'
            }
        },
        validation: {
            allowedExtensions: ['jpeg', 'jpg', 'png', 'bmp'],
            itemLimit: 1,
            sizeLimit: 3 * 1024 * 1024
        },
        onComplete: logoUploaded,
        onError: logoOnUpload,
        onSubmit: logoOnUpload,
        onCancel: logoOnUpload,
        onValidate: logoOnUpload
    });
});