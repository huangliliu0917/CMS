/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.entity;

import com.huotu.hotcms.service.Auditable;
import com.huotu.hotcms.service.ResourcesOwner;
import com.huotu.hotcms.service.common.PageLayoutType;
import com.huotu.hotcms.service.common.PageType;
import com.huotu.hotcms.service.entity.AbstractContent;
import com.huotu.hotcms.service.entity.Site;
import com.huotu.hotcms.widget.page.PageLayout;
import lombok.Getter;
import lombok.Setter;
import me.jiangcai.lib.resource.service.ResourceService;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


/**
 * 用与保存页面信息
 * <p>页面也是内容的一种!</p>
 * 数据源重新定义为「可选的数据源」,主要是用以定位
 * Created by lhx on 2016/7/4.
 */
@Entity
@Table(name = "cms_pageInfo")
@Getter
@Setter
public class PageInfo extends AbstractContent implements Auditable, ResourcesOwner {

    @Column(name = "pagePath", length = 60)
    private String pagePath;

    @ManyToOne(optional = false)
    @JoinColumn(name = "siteId")
    private Site site;

    @Column(name = "pageType", nullable = false)
    private PageType pageType;

    /**
     * 布局类型
     */
    private PageLayoutType pageLayoutType;

    /**
     * 每次随机生成
     */
    @Column(name = "resourceKey", length = 60)
    private String resourceKey;

    /**
     * 页面配置的xml数据
     */
    @Convert(converter = PageLayoutConverter.class)
    @Column(columnDefinition = "text")//略嫌糟糕
    private PageLayout layout;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageInfo)) return false;
        PageInfo pageInfo = (PageInfo) o;
        return Objects.equals(getId(), pageInfo.getId()) &&
                Objects.equals(pagePath, pageInfo.pagePath) &&
                Objects.equals(getTitle(), pageInfo.getTitle()) &&
                pageType == pageInfo.pageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), pagePath, getTitle(), pageType);
    }

    @Override
    public PageInfo copy() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCategory(getCategory());
        pageInfo.setUpdateTime(LocalDateTime.now());
        pageInfo.setCreateTime(LocalDateTime.now());
        pageInfo.setTitle(getTitle());
        pageInfo.setResourceKey(UUID.randomUUID().toString());
//        pageInfo.setPageSetting(pageSetting);
        pageInfo.setLayout(layout);
        pageInfo.setPagePath(pagePath);
        pageInfo.setPageType(pageType);
        pageInfo.setSite(site);
        return pageInfo;
    }

    /**
     * @return ..
     * @param isForClient 是否为在页面上获取静态资源，如果是加上时间戳，避免缓存
     */
    public String getPageCssResourcePath(boolean isForClient) {
        if (!isForClient)
            return "page/resource/css/" + resourceKey + "/" + getId() + ".css";
        else
            return "page/resource/css/" + resourceKey + "/" + getId() + ".css?date=" + getUpdateTime();
    }

    public String getPageCssResourcePath() {
        return getPageCssResourcePath(true);
    }

    @Override
    public String[] getResourcePaths() {
        return new String[]{getPageCssResourcePath(false)};
    }

    @Override
    public void updateResource(int index, String path, ResourceService resourceService) throws IOException {
        throw new NoSuchMethodError("PageInfo update resource itself.");
    }

    @Override
    public String generateResourcePath(int index, ResourceService resourceService, InputStream stream) {
        throw new NoSuchMethodError("PageInfo update resource itself.");
    }
}
