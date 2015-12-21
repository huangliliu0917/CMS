package com.huotu.hotcms.admin.interceptor;

import com.huotu.hotcms.entity.Site;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/12/21.
 */
public class SiteResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == Site.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String submittedSiteId = request.getParameter("siteId");
        if (submittedSiteId!=null){
            return null;// select by HttpServletRequest
        }
        //

//        String host = request.getServerName();
        Site site = new Site();
        site.setTitle("test");
        return site; // select by SiteDomain
    }
}
