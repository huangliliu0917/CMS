/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.widget.test;

import com.huotu.hotcms.widget.Widget;
import com.huotu.widget.test.bean.WidgetHolder;
import com.huotu.widget.test.service.WidgetServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Set;

/**
 * @author CJ
 */
@EnableWebMvc
@Import({WidgetTestConfig.ViewResolver.class, WidgetServiceConfig.class})
@ComponentScan("com.huotu.widget.test.bean")
public class WidgetTestConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ThymeleafViewResolver normalViewResolver;


    public static String WidgetIdentity(Widget widget) {
        return widget.widgetId().replace('-', '.');
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(normalViewResolver);
    }

//    @Bean
//    public SpringTemplateEngine springTemplateEngine(){
//        return new SpringTemplateEngine();
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/editor")
//                .setViewName("editor");
//    }

    @DependsOn("widgetHolder")
    @ComponentScan("com.huotu.widget.test.thymeleaf")
    static class ViewResolver {
        @Autowired
        private WidgetHolder widgetHolder;
        @Autowired
        private ApplicationContext applicationContext;
        //        @Autowired
//        private WidgetTemplateResolver widgetTemplateResolver;
        @Autowired
        private Set<IDialect> dialectSet;

        @Bean
        public ThymeleafViewResolver normalViewResolver() {
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setCharacterEncoding("UTF-8");
            templateResolver.setApplicationContext(applicationContext);
            templateResolver.setPrefix("classpath:/testPages/");
            templateResolver.setSuffix(".html");

//            widgetTemplateResolver.setOrder(1);

            SpringTemplateEngine engine = new SpringTemplateEngine();
            engine.addTemplateResolver(templateResolver);
//            engine.addTemplateResolver(widgetTemplateResolver);
            engine.setAdditionalDialects(dialectSet);

            ThymeleafViewResolver resolver = new ThymeleafViewResolver();
            resolver.setCharacterEncoding("UTF-8");
            resolver.setContentType("text/html;charset=UTF-8");
            resolver.setTemplateEngine(engine);

            return resolver;
        }
    }
}