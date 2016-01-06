/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.web.service.factory;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;

/**
 * Created by cwb on 2016/1/6.
 */
public class LinkForeachProcessorFactory {

    private static LinkForeachProcessorFactory instance;

    public static LinkForeachProcessorFactory getInstance() {
        if(instance == null) {
            instance = new LinkForeachProcessorFactory();
        }
        return instance;
    }

    public Object process(IProcessableElementTag elementTag, ITemplateContext context) {
        return null;
    }
}
