/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.service.model.thymeleaf.foreach;

import com.huotu.hotcms.service.common.RouteType;
import lombok.Getter;
import lombok.Setter;

/**
 * 栏目遍历解析器参数模型
 * Created by cwb on 2016/1/6.
 */
@Getter
@Setter
public class CategoryForeachParam {

    /**
     * 所属站点Id
     */
    @Rename("siteid")
    public Long siteId;

    /**
     * 路由类型
     */
    @Rename("routetype")
    public RouteType routeType;

    /**
     * 父节点id
     */
    @Rename("parentid")
    public Long parentId;

    /**
     * 取得列表大小
     */
    @Rename("size")
    public Integer size;

    /**
     * 获取列表时排除的主键Id(可排除多个，逗号分隔)
     */
    @Rename("excludeids")
    public String[] excludeIds;

    /**
     * 获取指定Id的列表(可指定多个，逗号分隔)
     */
    @Rename("specifyids")
    public String[] specifyIds;
}
