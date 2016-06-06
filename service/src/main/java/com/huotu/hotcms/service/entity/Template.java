package com.huotu.hotcms.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *  模板信息
 */
@Entity
@Table(name = "cms_template")
@Data
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 模板名称
     */
    @Column(name = "tempName")
    private String tempName;

    /**
     * 站点id
     */
    @ManyToOne
    private Site site;

    /**
     * 缩略图
     */
    @Column(name = "thumbUri")
    private String thumbUri;

    /**
     * 预览量
     */
    @Column(name = "previewTimes")
    private int previewTimes;

    /**
     * 浏览量
     */
    @Column(name = "scans")
    private int scans;

    /**
     * 点赞数量
     */
    @Column(name = "lauds")
    private int lauds;

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

    /**
     * 模板类型，eg: 汽车行业，服装行业...
     */
    @ManyToOne
    private TemplateType templateType;


}