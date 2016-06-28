/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget;

import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryList;

import java.util.List;

/**
 * Created by lhx on 2016/6/28.
 */

public interface CMSDataSourceService {

    /**
     *
     * @param gallery 图库模型
     * @return 返回当前站点指定图库模型下的图库列表
     */
    List<GalleryList> findByGallery(Gallery gallery);

}