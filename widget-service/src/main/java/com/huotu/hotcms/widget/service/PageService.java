/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.service;

import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.page.Page;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 页面服务
 *
 * @author CJ
 */
public interface PageService {

    /**
     * 生成一个页面的html
     * 这个结果无需缓存,因为上下文的变化应该是比较大的。
     *
     * @param page 页面
     * @return html
     * @throws IOException
     */
    String generateHTML(Page page, CMSContext context) throws IOException;
    //, TemplateException, ComponentPropertiesException

    void parsePageToXMlAndSave(Page page, String pageId) throws IOException, URISyntaxException;
    /**
     * 解析保存了{@link com.huotu.hotcms.widget.page.Page}信息的XML
     * @param pageId pageId
     * @return  {@link com.huotu.hotcms.widget.page.Page}
     * @throws IOException 其他异常
     */
    Page getPageFromXMLConfig(String pageId) throws IOException;

    /**
     * 删除相关页面信息
     * @param ownerId  ownerId
     * @param pageId 页面ID
     * @throws IOException 其他异常
     */
    void deletePage(long ownerId,String pageId) throws IOException;


}
