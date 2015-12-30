/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.web.controller;

import com.huotu.hotcms.web.model.Seo;
import com.huotu.hotcms.service.entity.Site;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cwb on 2015/12/16.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String testIndex(Site site,Model model){
        model.addAttribute("site",site);
        String viewName = "/view/index.html";
        if(site.isCustom()) {
            viewName = site.getCustomTemplateUrl();
        }
        return viewName;
    }

    @RequestMapping("/*")
    public String testIndex2(Site site,Model model) throws Exception {
        model.addAttribute("site",site);
        String viewName = "/view/index.html";
        if(site.isCustom()) {
            viewName = site.getCustomTemplateUrl();
        }
        return viewName;
    }


    @RequestMapping("/remote")
    public String testRemote(Model model) {
        model.addAttribute("seo",new Seo("Access remote template success!"));
        return "/pc/yun-index.html";
    }

}