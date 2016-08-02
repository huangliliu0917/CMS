/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.entity;

import com.huotu.hotcms.service.model.LinkModel;
import com.huotu.hotcms.service.util.ImageHelper;
import com.huotu.hotcms.service.util.SerialUtil;
import lombok.Getter;
import lombok.Setter;
import me.jiangcai.lib.resource.service.ResourceService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * 链接模型
 * Created by cwb on 2015/12/22.
 */
@Entity
@Table(name = "cms_link")
@Getter
@Setter
public class Link extends AbstractContent {

//    /**
//     * 标题
//     */
//    @Column(name = "title")
//    private String title;

//    /**
//     * 描述
//     */
//    @Column(name = "description")
//    private String description;

    /**
     * 缩略图uri
     */
    @Column(name = "thumbUri")
    private String thumbUri;

    /**
     * 链接地址
     */
    @Column(name = "linkUrl")
    private String linkUrl;

    public static LinkModel getLinkModel(Link link) {
        LinkModel linkModel = new LinkModel();
        linkModel.setLinkUrl(link.getLinkUrl());
        linkModel.setThumbUri(link.getThumbUri());
        return linkModel;
    }

    @Override
    public Link copy() {
        Link link = new Link();
        link.setThumbUri(thumbUri);
        link.setDescription(getDescription());
        link.setOrderWeight(getOrderWeight());
        link.setTitle(getTitle());
        link.setCreateTime(LocalDateTime.now());
        link.setUpdateTime(LocalDateTime.now());
        link.setDeleted(isDeleted());
        return link;
    }

    @Override
    public Link copy(Site site, Category category) {
        Link link = copy();
        link.setCategory(category);
        link.setSerial(SerialUtil.formatSerial(site));
        return link;
    }

    @Override
    public String[] getImagePaths() {
        return new String[]{thumbUri};
    }

    @Override
    public void updateImage(int index, ResourceService resourceService, InputStream stream) throws IOException, IllegalArgumentException {
        if (thumbUri != null) {
            resourceService.deleteResource(thumbUri);
        }
        thumbUri = ImageHelper.storeAsImage("png", resourceService, stream);
    }

//    /**
//     * 所属栏目
//     */
//    @Basic
//    @ManyToOne
//    @JoinColumn(name = "categoryId")
//    private Category category;
}
