/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.web.service.factory;

import com.huotu.hotcms.service.entity.Download;
import com.huotu.hotcms.service.model.thymeleaf.foreach.DownloadForeachParam;
import com.huotu.hotcms.service.service.DownloadService;
import com.huotu.hotcms.web.thymeleaf.expression.DialectAttributeFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;

import java.util.List;

/**
 * Created by cwb on 2016/1/18.
 */
public class DownloadForeachProcessorFactory {

    private final int DEFAULT_PAGE_SIZE = 5;

    private static Log log = LogFactory.getLog(DownloadForeachProcessorFactory.class);

    private static DownloadForeachProcessorFactory instance;

    private DownloadForeachProcessorFactory(){}

    public static DownloadForeachProcessorFactory getInstance() {
        if(instance == null) {
            instance = new DownloadForeachProcessorFactory();
        }
        return instance;
    }

    public List<Download> process(IProcessableElementTag elementTag,ITemplateContext context) {
        try {
            DownloadForeachParam downloadForeachParam = DialectAttributeFactory.getInstance().getForeachParam(elementTag, DownloadForeachParam.class);
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            DownloadService downloadService = (DownloadService)applicationContext.getBean("downloadServiceImpl");
            //根据指定id获取栏目列表
            if(downloadForeachParam.getSpecifyids()!=null) {
                return downloadService.getSpecifyDownloads(downloadForeachParam.getSpecifyids());
            }
            if(StringUtils.isEmpty(downloadForeachParam.getCategoryid())) {
                throw new Exception("栏目id没有指定");
            }
            if(downloadForeachParam.getSize()==null) {
                downloadForeachParam.setSize(DEFAULT_PAGE_SIZE);
            }
            return downloadService.getDownloadList(downloadForeachParam);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());//TODO 上线时删除
        }
        return null;
    }
}