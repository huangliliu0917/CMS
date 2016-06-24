/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.service;

import com.huotu.hotcms.service.config.ServiceTestConfig;
import com.huotu.hotcms.service.entity.Article;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.service.entity.Template;
import com.huotu.hotcms.service.repository.ArticleRepository;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.SiteRepository;
import com.huotu.hotcms.service.repository.TemplateRepository;
import com.huotu.hotcms.service.util.SerialUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * Created by fawzi on 2016/5/12.
 * 用于site相关测试
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
@WebAppConfiguration
@Transactional
public class SiteTest {
    @Autowired SiteService siteService;

    @Autowired
    SiteRepository siteRepository;
    /**
     * 注意：需要先部署整个项目才可以启动该单元测试。不然无法复制资源文件
     */
    @Autowired
    ArticleRepository articleRepository;
    @Qualifier("templateRepository")
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void localTest() {
        // only Lang
        Locale locale1 = new Locale("zh");
        System.out.println(locale1.getLanguage());
        System.out.println(locale1.getCountry());
        System.out.println(locale1.getVariant());

        Locale locale2 = new Locale("zh", "CN");
        System.out.println(locale2.getLanguage());
        System.out.println(locale2.getCountry());
        System.out.println(locale2.getVariant());
    }

    /**
     * 站点复制测试
     */
    @Test
    public void testSiteCopy() throws Exception {
        long templateID=3;
        long siteId=71;
        Site customerSite=siteRepository.findOne(siteId);
        siteService.siteCopy(templateID,customerSite);
    }

    @Test
    public void testQuery(){
        long siteID=4471;
        Article templateSiteArticle=articleRepository.findArticleByCategory_Site_SiteId(siteID);
        long id=templateSiteArticle.getId();
    }

    @Test
    public void testDeepCopy(){
        long templateID=3;
        long siteId=71;
        Site customerSite=siteRepository.findOne(siteId);
        Template template=templateRepository.findOne(templateID);
        Site templateSite=template.getSite();

        List<Category> categories=categoryRepository.findBySite(templateSite);
        try{
            for(Category category:categories){
                category.setSerial(SerialUtil.formatSerial(customerSite));
                category.setSite(customerSite);
                category.setId(null);
                category.setSite(customerSite);
                category=categoryRepository.save(category);
            }
        }catch (Throwable e){
            e.printStackTrace();
        }

    }

}
