/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huotu.hotcms.service.FilterBehavioral;
import com.huotu.hotcms.service.entity.AbstractContent;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.service.entity.Template;
import com.huotu.hotcms.service.exception.PageNotFoundException;
import com.huotu.hotcms.service.repository.ContentRepository;
import com.huotu.hotcms.service.service.TemplateService;
import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.Component;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.InstalledWidget;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetLocateService;
import com.huotu.hotcms.widget.WidgetResolveService;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.entity.PageInfo;
import com.huotu.hotcms.widget.page.Layout;
import com.huotu.hotcms.widget.page.PageElement;
import com.huotu.hotcms.widget.page.PageLayout;
import com.huotu.hotcms.widget.service.PageService;
import me.jiangcai.lib.resource.Resource;
import me.jiangcai.lib.resource.service.ResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

/**
 * 用户获取page页面html Code 页面服务相关
 * <p>新增preview相关</p>
 * Created by lhx on 2016/7/2.
 */
@Controller
public class FrontController implements FilterBehavioral {

    private static final Log log = LogFactory.getLog(FrontController.class);
    @Autowired
    WidgetLocateService widgetLocateService;
    @Autowired(required = false)
    private ContentRepository contentRepository;
    @Autowired
    private PageService pageService;
    @Autowired
    private WidgetResolveService widgetResolveService;
    @Autowired
    private TemplateService templateService;

    @Autowired
    private ResourceService resourceService;

    /**
     * 参考<a href="https://huobanplus.quip.com/Y9mVAeo9KnTh">可用的CSS 资源</a>
     *
     * @param pageId 页面id
     * @param id     组件id
     * @return css内容
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/previewCss/{pageId}/{componentId}.css"}
            , produces = "text/css")
    public ResponseEntity previewCss(@PathVariable("pageId") long pageId, @PathVariable("componentId") String id)
            throws IOException {
        try {
            PageInfo pageInfo = pageService.getPage(pageId);
            // 寻找控件了
            for (Layout layout : PageLayout.NoNullLayout(pageInfo.getLayout())) {
                Component component = findComponent(layout, id);
                if (component != null) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    widgetResolveService.widgetDependencyContent(CMSContext.RequestContext()
                            , component.getInstalledWidget().getWidget(), Widget.CSS, layout, buffer);
                    return ResponseEntity
                            .ok()
                            .contentType(Widget.CSS)
                            .body(buffer.toByteArray());
                }
            }
            return ResponseEntity.notFound().build();
        } catch (PageNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * 获取指定控件,指定样式,的控件预览视图htmlCode
     * <p>
     * 成功：状态200，并返回控件 previewHtml Code
     * 失败：状态404 无htmlCode
     * <p>
     * widgetIdentifier {@link com.huotu.hotcms.service.entity.support.WidgetIdentifier}
     * styleId          样式id
     * properties       控件参数
     */
    @RequestMapping(value = "/preview/component", method = RequestMethod.POST)
    public ResponseEntity previewHtml(@RequestBody String json) throws IOException {
        return getPreviewComponentResponseEntity(json);
    }

    private ResponseEntity getPreviewComponentResponseEntity(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(json, Map.class);
        String widgetIdentifier = (String) map.get("widgetIdentity");
        String styleId = (String) map.get("styleId");
//        String pageId = (String) map.get("pageId");
        String componentId = (String) map.get("componentId");
        Map properties = (Map) map.get("properties");
        ComponentProperties componentProperties = new ComponentProperties();
        if (properties != null)
            componentProperties.putAll(properties);
        try {
            InstalledWidget installedWidget = widgetLocateService.findWidget(widgetIdentifier);

            {
                //补丁
                styleId = WidgetStyle.styleByID(installedWidget.getWidget(), styleId).id();
            }

            installedWidget.getWidget().valid(styleId, componentProperties);
            String previewHTML = widgetResolveService.previewHTML(installedWidget.getWidget(), styleId
                    , CMSContext.RequestContext(), componentProperties);

            // 生成好的css
            Resource resource = null;
            String resourcePath = null;
            // 决定是否生成css
            org.springframework.core.io.Resource cssResource = installedWidget.getWidget()
                    .widgetDependencyContent(Widget.CSS);

            if (cssResource != null && cssResource.exists()) {
                Path path = Files.createTempFile("tempCss", ".css");
                try {

                    try (OutputStream out = Files.newOutputStream(path)) {
                        Component component = new Component();
                        component.setId(componentId);
                        component.setInstalledWidget(installedWidget);
                        component.setProperties(componentProperties);
                        component.setWidgetIdentity(widgetIdentifier);
                        component.setStyleId(styleId);

                        widgetResolveService.widgetDependencyContent(CMSContext.RequestContext()
                                , installedWidget.getWidget(), Widget.CSS, component, out);
                        out.flush();
                    }

                    try (InputStream is = Files.newInputStream(path)) {
                        resourcePath = "tmp/page/" + UUID.randomUUID().toString() + ".css";
                        resource = resourceService.uploadResource(resourcePath, is);
                    }

                } finally {
                    //noinspection ThrowFromFinallyBlock
                    Files.deleteIfExists(path);
                }
            }

            return ResponseEntity.ok().contentType(Widget.HTML)
                    .header("cssLocation", resource != null ? resource.httpUrl().toString() : "")
                    .header("cssPath", resource != null ? resourcePath : "")
                    .body(previewHTML.getBytes());
        } catch (Exception e) {
            return ResponseEntity.notFound().header("cssLocation", "").build();
        }
    }

    private Component findComponent(PageElement element, String id) {
        if (element instanceof Component) {
            if (id.equals(((Component) element).getId())) {
                return (Component) element;
            }
            return null;
        }
        if (element instanceof Layout) {
            for (PageElement element1 : ((Layout) element).elements()) {
                Component component = findComponent(element1, id);
                if (component != null)
                    return component;
            }
        }
        return null;
    }

    /**
     * 用于支持首页的浏览
     *
     * @param model
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/_web", "/_web/"})
    public PageInfo pageIndex(Model model) throws IOException, PageNotFoundException {
        return pageIndex("", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/_web/{pagePath}"})
    public PageInfo pageIndex(@PathVariable("pagePath") String pagePath, Model model)
            throws PageNotFoundException, IOException {
        CMSContext cmsContext = CMSContext.RequestContext();
        if (cmsContext.getSite() instanceof Template && pagePath.isEmpty()) {
            templateService.preview((Template) cmsContext.getSite());
        }
        model.addAttribute("time", System.currentTimeMillis());
        //查找当前站点下指定pagePath的page
        return pageService.findBySiteAndPagePath(cmsContext.getSite(), pagePath);
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/_web/{pagePath}/{contentId}"})
    public PageInfo pageContent(@PathVariable("pagePath") String pagePath, @PathVariable("contentId") Long contentId
            , Model model) throws IOException, PageNotFoundException {
        CMSContext cmsContext = CMSContext.RequestContext();
        model.addAttribute("time", System.currentTimeMillis());
        //查找数据内容
        AbstractContent content = contentRepository.findOne(contentId);
        if (content != null) {
            cmsContext.setAbstractContent(content);
            //查找当前站点下指定数据源pagePath下最接近的page
            return pageService.getClosestContentPage(content.getCategory(), pagePath);
        } else {
            return pageService.findBySiteAndPagePath(cmsContext.getSite(), pagePath);
        }
    }


    @Override
    public FilterStatus doSiteFilter(Site site, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String targetPath = "/_web" + request.getRequestURI();
            log.debug("Forward to " + targetPath);
            request.getRequestDispatcher(targetPath)
                    .forward(request, response);
        } catch (ServletException e) {
            throw new IOException(e);
        }
        return FilterStatus.STOP;
    }

    @Override
    public int getOrder() {
        // 最低优先级 到了我这  就是改url了
        return Ordered.LOWEST_PRECEDENCE;
    }


}
