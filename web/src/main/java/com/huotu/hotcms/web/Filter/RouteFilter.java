package com.huotu.hotcms.web.Filter;

import com.huotu.hotcms.service.entity.Route;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.web.service.RouteResolverService;
import com.huotu.hotcms.web.service.SiteResolveService;
import com.huotu.hotcms.web.util.PatternMatchUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     解析过滤器过滤器
 * </p>
 *
 * @author xhl
 *
 * @since 1.0.0
 *
 */
public class RouteFilter implements Filter {
    private static final Log log = LogFactory.getLog(RouteFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request1 = ((HttpServletRequest) request);
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            SiteResolveService siteResolveService = (SiteResolveService) applicationContext.getBean("siteResolveService");
            Site site = siteResolveService.getCurrentSite(request1);
            String servletPath=PatternMatchUtil.getServletPath(site,request1);//获得ServletPath 国际化带语言参数经一步处理(移除国际化参数信息)得到的跟配置的路由一致
            String langParam=PatternMatchUtil.getEffecterLangParam(request1, site);//获得国际化参数(url上带上的语言地区参数信息)
            if(PatternMatchUtil.isMatchFilter(servletPath)) {
                RouteResolverService routeResolverService = (RouteResolverService) applicationContext.getBean("routeResolverService");
                if (site != null) {
                    Route route = routeResolverService.getRoute(site, servletPath);
                    if (route == null&&!site.isCustom()) {
                        request.getRequestDispatcher("/template/" + site.getCustomerId() + servletPath).forward(request, response);
                    } else {
                        if(!StringUtils.isEmpty(langParam)) {//语言参数不为空追加上语言参数并做服务端forward
                            request.getRequestDispatcher("/web/"+langParam+servletPath).forward(request, response);
                        }else{
                            request.getRequestDispatcher("/web"+servletPath).forward(request, response);
                        }
                    }
                    return;
                } else {
                    chain.doFilter(request,response);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
