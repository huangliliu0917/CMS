/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.service.widget.service;

import com.huotu.hotcms.service.widget.model.GoodsModel;
import com.huotu.hotcms.service.widget.model.GoodsPage;
import com.huotu.hotcms.service.widget.model.GoodsSearcher;
import com.huotu.huobanplus.common.entity.Goods;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 商品组件服务
 * 系统属性依赖
 * com.huotu.huobanplus.open.api.root
 * 本地调试需配置上述属性为
 * http://api.open.fancat.cn:8081
 * Created by cwb on 2016/3/17.
 */
public interface GoodsService {

    /**
     * 商品检索
     * @param customerId 商户主键id
     * @param goodsSearcher 检索条件
     * @return
     */
    GoodsPage searchGoods(HttpServletRequest request,int customerId, GoodsSearcher goodsSearcher) throws Exception;


    /**
     * 热销产品
     *  搜索销量前十的产品
     * @param customerId
     * @return
     */
    List<GoodsModel> getHotGoodsList(HttpServletRequest request,int customerId) throws Exception;


}
