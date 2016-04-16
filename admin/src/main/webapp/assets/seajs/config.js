var version="1.56893.3";
seajs.config({
	alias: {
		"jquery": "js/jquery-1.9.1.min.js",
		"bootstrap": "js/bootstrap.min.js",
		"validate": "libs/validate/jquery.validate.min.js",
		"message": "libs/validate/jquery.validate.addMethod.js",
		"common":"js/page/common.js?v="+version,
		"main":"js/page/main.js?v="+version,
		"home":"js/page/home.js?v="+version,
		"JGrid":"libs/JGrid/jquery.JGrid.js?v="+version,
		"layer":"libs/layer/layer.js?v="+version,
		"updateModel":"js/page/system/updateModel.js?v="+version,
		"addModel":"js/page/system/addModel.js?v="+version,
		"modelList":"js/page/system/modelList.js?v="+version,
		"addRegion":"js/page/system/addRegion.js?v="+version,
		"updateRegion":"js/page/system/updateRegion.js?v="+version,
		"regionList":"js/page/system/regionList.js?v="+version,
		"noticeList":"js/page/contents/noticeList.js?v="+version,
		"addNotice":"js/page/contents/addNotice.js?v="+version,
		"updateNotice":"js/page/contents/updateNotice.js?v="+version,
		"videoList":"js/page/contents/videoList.js?v="+version,
		"addVideo":"js/page/contents/addVideo.js?v="+version,
		"updateVideo":"js/page/contents/updateVideo.js?v="+version,
		"articleList":"js/page/contents/articleList.js?v="+version,
		"addArticle":"js/page/contents/addArticle.js?v="+version,
		"updateArticle":"js/page/contents/updateArticle.js?v="+version,
		"linkList":"js/page/contents/linkList.js?v="+version,
		"addLink":"js/page/contents/addLink.js?v="+version,
		"updateLink":"js/page/contents/updateLink.js?v="+version,
		"downloadList":"js/page/contents/downloadList.js?v="+version,
		"addDownload":"js/page/contents/addDownload.js?v="+version,
		"updateDownload":"js/page/contents/updateDownload.js?v="+version,
		"addGallery":"js/page/contents/addGallery.js?v="+version,
		"addGalleryList":"js/page/contents/addGalleryList.js?v="+version,
		"galleryList":"js/page/contents/galleryList.js?v="+version,
		"updateGallery":"js/page/contents/updateGallery.js?v="+version,
		"updateGalleryList":"js/page/contents/updateGalleryList.js?v="+version,
		"addContents":"js/page/contents/addContents.js?v="+version,
		"siteList":"js/page/web/siteList.js?v="+version,
		"routeList":"js/page/web/routeList.js?v="+version,
		"addRoute":"js/page/web/addRoute.js?v="+version,
		"updateRoute":"js/page/web/updateRoute.js?v="+version,
		"addSite":"js/page/web/addSite.js?v="+version,
		"updateSite":"js/page/web/updateSite.js?v="+version,
		"addCategory":"js/page/section/addCategory.js?v="+version,
		"categoryList":"js/page/section/categoryList.js?v="+version,
		"updateCategory":"js/page/section/updateCategory.js??v="+version,
		"contentsList":"js/page/contents/contentsList.js?v="+version,
		"widgetTypeList":"js/page/widget/widgetTypeList.js?v="+version,
		"addWidgetType":"js/page/widget/addWidgetType.js?v="+version,
		"updateWidgetType":"js/page/widget/updateWidgetType.js?v="+version,
		"widgetMainsList":"js/page/widget/widgetMainsList.js?v="+version,
		"addWidgetMains":"js/page/widget/addWidgetMains.js?v="+version,
		"updateWidgetMains":"js/page/widget/updateWidgetMains.js?v="+version,
		"widgetUploadRead":"js/page/widget/widgetUploadRead.js?v="+version,
		"widgetUploadEdit":"js/page/widget/widgetUploadEdit.js?v="+version,

		"ajaxfileupload":"libs/ajaxfileupload.js?v="+version,
		"jupload":"libs/upload/jackson-upload.js?v="+version,
		"ueditorConfig":"libs/ueditor/ueditor.config.js?v="+version,
		"ueditorAll":"libs/ueditor/ueditor.all.js?v="+version,
		"kindeditor":"libs/richText/kindeditor-min.js?v="+version,
		"init":"libs/richText/init.js?v="+version,
		"zh_CN":"libs/richText/lang/zh_CN.js?v="+version,

		"jqxcore":"libs/jqwidgets/jqxcore.js?v="+version,
		"jqxdata":"libs/jqwidgets/jqxdata.js?v="+version,
		"jqxbuttons":"libs/jqwidgets/jqxbuttons.js?v="+version,
		"jqxscrollbar":"libs/jqwidgets/jqxscrollbar.js?v="+version,
		"jqxdatatable":"libs/jqwidgets/jqxdatatable.js?v="+version,
		"jqxtreegrid":"libs/jqwidgets/jqxtreegrid.js?v="+version,
		"jqxlistbox":"libs/jqwidgets/jqxlistbox.js?v="+version,
		"jqxdropdownlist":"libs/jqwidgets/jqxdropdownlist.js?v="+version,
		"categoryCommon":"js/page/categoryCommon.js?v="+version,

		"selectPlug":"libs/select/jackson-select-ui.min.js",
        "tabPlug":"libs/tab/jackson.tabs.min.js",

		"pagesList":"js/page/pages/list.js?v="+version,
		"pagesDefaults":"js/page/pages/defaults.js?v="+version,
		"pagesEditMain":"js/page/edit/main.js?v="+version,

		"widgetTooBar":"widget/toobar/toobar.js?v="+version,
		"widgetPageBack":"widget/toobar/pageBack.js?v="+version,

		"spectrumColor":"libs/JColor/spectrum.js?v="+version,
		"widgetColor":"libs/JColor/hotColor.js?v="+version,

		"widgetPageModel":"js/widgetPageModel.js?v="+version,

		"widgetSelect":"widget/select.js?v="+version,
		"cmsQueue":"js/jquery.cms.queue.js?v="+version,
		"widgetData":"widget/widgetData.js?v="+version,
	},
	preload: ['jquery']
});