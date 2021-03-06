CMSWidgets.pushNextWidgetIdentity('com.huotu.hotcms.widget.friendshipLink-friendshipLink:1.0-SNAPSHOT');
/**
 * ????
 * ??????target ???_blank by CJ
 * Created by Li Huaixin on 2016/6/23.
 */
CMSWidgets.initWidget({
    editor: {
        properties: null,
        addLink: function () {
            $(".linkbox").on("click", ".addLink", function () {
                var htmlElement = $(".LinkRowHtml").clone();
                htmlElement.css("display", "block");
                $(".linkbox").append(htmlElement.html());
            });
        },
        removerLinkItem: function () {
            var me = this;
            $(".linkbox").on("click", ".removerLinkItem", function () {
                var itemObj = $(this).parent().parent();
                var title;
                var url;
                $.each($(itemObj).find(".linkTitle"), function (i, v) {
                    title = $(v).val();
                });
                $.each($(itemObj).find(".linkUrl"), function (i, v) {
                    url = $(v).val();
                });
                itemObj.remove();
                $.grep(me.properties.linkList, function (obj, i) {
                    if (obj != '' && obj.title == title && obj.url == url) {
                        me.properties.linkList.splice(i, 1);
                        return true;
                    }
                });
            });
        },
        saveComponent: function (onSuccess, onFailed) {
            console.log($.getTreeViewData());
            return this.properties;
        },
        initProperties: function () {
            this.properties.linkList = [];
            $('.js-addEditBtn').addEdit({
                debug: true,
                hasImage: true,
                hasParagraph: true,
                hasUrl: true,
                hasTextArea: true
            });
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.removerLinkItem();
            this.addLink();
        },
        close: function (globalId) {
            $('.linkbox').off("click", ".addLink");
            $('.linkbox').off("click", ".removerLinkItem");
        }
    }
});