/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.cms.manage.service;

import com.huotu.hotcms.service.FilterBehavioral;
import com.huotu.hotcms.service.entity.Site;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 最终前端-页面 过滤行为
 *
 * @author CJ
 */
@Service
public class PageFilterBehavioral implements FilterBehavioral {

    private static final Log log = LogFactory.getLog(PageFilterBehavioral.class);

    private final Pattern pattern = Pattern.compile("^/([_a-zA-Z0-9]+)(/.*)?$");
    /**
     * 保护的path,这些path是系统使用的
     */
    private List<String> protectedPath = Arrays.asList("_web",
            "manage",
            "web",
            "bind",
            "interim",
            "shop",
            "admin"
    );

    /**
     * @param pagePath path
     * @return 是否允许用户使用指定的pagePath
     */
    public boolean ableToUse(String pagePath) {
        // TODO  需要更加细致的比如 manage/ admin/都是不可以的
        return !protectedPath.contains(pagePath);
    }

    @Override
    public FilterStatus doSiteFilter(Site site, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String uri = request.getServletPath();

        String contextUri;
        if (uri.length() > 0)
            contextUri = uri;
        else {
            contextUri = request.getRequestURI().substring(request.getContextPath().length());
        }

        try {
            String firstPath = firstPath(contextUri);

            if (protectedPath.contains(firstPath)) {
                return FilterStatus.CHAIN;
            } else
                return FilterStatus.NEXT;
        } catch (IllegalStateException ex) {
            log.debug("doSiteFilter", ex);
            return FilterStatus.CHAIN;
        }

    }

    /**
     * 从uri中获取第一个path
     * 比如uri=/foo/bar  应该获取foo
     * uri=/foo   应该获取foo
     * uri=       应该获取
     * <a href="https://regexper.com/#%5E%5C%2F(%5B_a-zA-Z0-9%5D%2B)(%5C%2F.*)%3F%24">正则</a>
     *
     * @param uri
     * @return path
     */
    private String firstPath(String uri) {
        if (uri.length() == 0)
            return uri;
        if (uri.equals("/"))
            return "";
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.matches())
            throw new IllegalStateException("Bad Content URI:" + uri);
        return matcher.group(1);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}