/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.entity;

import com.huotu.hotcms.common.ModelType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by cwb on 2015/12/22.
 */
@Entity
@Table(name = "cms_dataModel")
@Getter
@Setter
public class DataModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//主键
    private String name;
    private String description;
    private int orderWeight;//排序权重
    private ModelType type;
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
