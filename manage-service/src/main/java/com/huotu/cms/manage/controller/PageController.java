/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.cms.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.huotu.hotcms.service.entity.PageInfo;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.service.exception.PageNotFoundException;
import com.huotu.hotcms.service.repository.SiteRepository;
import com.huotu.hotcms.widget.page.Page;
import com.huotu.hotcms.widget.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 *<b>页面管理服务</b>
 * <p>
 *     <em>响应码说明：</em>
 *     <ul>
 *         <li>202-成功接收客户端发来的请求</li>
 *         <li>502-出现异常，具体待定</li>
 *     </ul>
 * </p>
 *
 * @author wenqi
 * @since v2.0
 */
@Controller
public class PageController {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageService pageService;

    /**
     * <p>获取页面{@link PageInfo}</p>
     * @param siteId 站点ID
     * @return 拿到相应的界面信息列表
     * @see PageInfo
     * @see Page
     * @throws IOException 异常
     */
    @RequestMapping(value = "/manage/{siteId}/pages",method = RequestMethod.GET,produces = "application/json; charset=UTF-8")
    @ResponseBody
    List<PageInfo> getPageList(@PathVariable("siteId") Long siteId) throws IOException{
        Site site=siteRepository.findOne(siteId);
        if(site==null)
            throw new IllegalStateException("读取到的站点为空");
        return pageService.getPageList(site);
    }


    /**
     *  获取某一具体页面
     * @param pageId 页面ID
     * @return 页面信息
     * @throws IOException 其他异常
     */
    @RequestMapping(value = "/manage/pages/{pageId}",method = RequestMethod.GET
            ,produces = "application/json; charset=UTF-8")
    @ResponseBody
    Page getPage(@PathVariable("pageId") Long pageId) throws IOException,PageNotFoundException {
        return pageService.getPage(pageId);
    }

    /**
     * <p>保存界面{@link Page}信息到pageId相关的{@link PageInfo}中</p>
     * @throws IOException 从request中读取请求体时异常
     */

    @RequestMapping(value = "/manage/pages/{pageId}",method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void savePage(HttpServletRequest request,@PathVariable("pageId") Long pageID) throws IOException, URISyntaxException{
        String pageJson= CharStreams.toString(request.getReader());
        ObjectMapper objectMapper=new ObjectMapper();
        Page page=objectMapper.readValue(pageJson, Page.class);
        page.setPageIdentity(pageID);
        pageService.savePage(page,pageID);
    }

    /**
     * <p>删除界面{@link Page}</p>
     * @param pageId 页面ID
     */
    @RequestMapping(value = "/manage/pages/{pageId}",method = RequestMethod.DELETE)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void deletePage(@PathVariable("pageId") Long pageId) throws IOException{
        pageService.deletePage(pageId);
    }

    /**
     * 保存页面部分属性
     * @param pageId 页面ID
     * @param propertyName 要保存的属性名
     */
    @RequestMapping(value = "/manage/pages/{pageId}/{propertyName}",method = RequestMethod.PUT)
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    void savePagePartProperties(@PathVariable("pageId") Long pageId, @PathVariable("propertyName") String propertyName)
            throws IOException, PageNotFoundException {
        Page page=getPage(pageId);
    }


    /**
     * 跳转到CMS编辑界面，用于测试
     * @return url
     */
    @RequestMapping("/manage/page/edit/{pageId}")
    ModelAndView startEdit(@PathVariable("pageId") long pageId){
        // TODO BUG  可能需要更多的处理
        return new ModelAndView("/edit/edit.html");
    }
}
