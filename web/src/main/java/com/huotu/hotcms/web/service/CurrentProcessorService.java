package com.huotu.hotcms.web.service;

import com.huotu.hotcms.web.common.DialectTypeEnum;
import com.huotu.hotcms.web.service.factory.ArticleCurrentProcessorFactory;
import com.huotu.hotcms.web.service.factory.SiteCurrentProcessorFactory;
import org.thymeleaf.context.ITemplateContext;

/**
 * Created by Administrator xhl 2016/1/6.
 */
public class CurrentProcessorService extends BaseProcessorService {
    @Override
    public Object resolveDataByAttr(String attributeValue, ITemplateContext context) {
        if (dialectPrefix.equals(DialectTypeEnum.ARTICLE.getDialectPrefix())) {
            return new ArticleCurrentProcessorFactory().resolveDataByAttr(attributeValue, context);
        }
        if (dialectPrefix.equals(DialectTypeEnum.SITE.getDialectPrefix())) {
            return new SiteCurrentProcessorFactory().resolveDataByAttr(attributeValue, context);
        }
        if(dialectPrefix.equals(DialectTypeEnum.CATEGORY.getDialectPrefix())) {
            return new SiteCurrentProcessorFactory().resolveDataByAttr(attributeValue, context);
        }
        if(dialectPrefix.equals(DialectTypeEnum.DOWNLOAD.getDialectPrefix()))
        {
            return null;
        }
        if(dialectPrefix.equals(DialectTypeEnum.GALLERY.getDialectPrefix()))
        {
            return null;
        }
        if(dialectPrefix.equals(DialectTypeEnum.LINK.getDialectPrefix()))
        {
            return null;
        }
        if(dialectPrefix.equals(DialectTypeEnum.NOTICE.getDialectPrefix()))
        {
            return null;
        }
        return null;
    }
}