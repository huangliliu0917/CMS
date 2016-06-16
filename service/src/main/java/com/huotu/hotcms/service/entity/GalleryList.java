/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "cms_galleryList",uniqueConstraints = {@UniqueConstraint(columnNames = {"siteId,serial"})})
@Getter
@Setter
public class GalleryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 站点ID
     */
    @JoinColumn(name = "siteId")
    private Site site;

    /**
     * 序列号
     */
    private String serial;

    /**
     * 排序权重
     */
    @Column(name = "orderWeight")
    private int orderWeight;

    /**
     * 图片规格大小,比如：98x100
     * */
    @Column(name = "size")
    private String size;

    /**
     * 图片
     */
    @Column(name = "thumbUri")
    private String thumbUri;


    /**
     * 是否已删除
     */
    @Column(name = "deleted")
    private boolean deleted = false;

    /**
     * 所属图库记录ID
     */
    @ManyToOne
    @JoinColumn(name = "galleryId")
    private Gallery gallery;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private LocalDateTime updateTime;

}
