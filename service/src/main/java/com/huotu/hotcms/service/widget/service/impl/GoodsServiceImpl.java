/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.service.widget.service.impl;

import com.huotu.hotcms.service.service.HttpService;
import com.huotu.hotcms.service.util.ApiResult;
import com.huotu.hotcms.service.widget.model.GoodsModel;
import com.huotu.hotcms.service.widget.model.GoodsPage;
import com.huotu.hotcms.service.widget.model.GoodsSearcher;
import com.huotu.hotcms.service.widget.service.GoodsService;
import com.huotu.huobanplus.common.entity.Goods;
import com.huotu.huobanplus.sdk.common.repository.GoodsRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品组件服务具体实现
 * Created by cwb on 2016/3/17.
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private HttpService httpService;
    @Autowired
    private GoodsRestRepository goodsRestRepository;

    @Override
    public GoodsPage searchGoods(int customerId, GoodsSearcher goodsSearcher) throws Exception{
        Sort.Direction direction = getSortDirection(goodsSearcher);
        String[] properties = getSortProperties(goodsSearcher);
        int page = goodsSearcher.getPage()==null ? 0 : goodsSearcher.getPage();
        PageRequest pageRequest =
            properties == null ?
                    new PageRequest(page,20,direction,"orderWeight") :
                    new PageRequest(page, 20,direction, properties);
        Page<Goods> goodses = goodsRestRepository.search(customerId,
                goodsSearcher.getGoodsCatId(),
                goodsSearcher.getGoodsTypeId(),
                goodsSearcher.getBrandId(),
                goodsSearcher.getMinPrice(),
                goodsSearcher.getMaxPrice(),
                goodsSearcher.getUserId(),
                goodsSearcher.getKeyword(),
                pageRequest);
        GoodsPage goodsPage = new GoodsPage();
        goodsPage.setPageNo(goodses.getNumber());
        goodsPage.setTotalPages(goodses.getTotalPages());
        goodsPage.setTotalRecords(goodses.getTotalElements());
        List<GoodsModel> goodsModels = new ArrayList<>();
        int iterCount = 1;
        for(Goods goods : goodses) {
            GoodsModel goodsModel = new GoodsModel();
            goodsModel.setId(goods.getId());
            goodsModel.setTitle(goods.getTitle());
            goodsModel.setShelveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(goods.getAutoMarketDate()));
            goodsModel.setSales(goods.getSalesCount());
            goodsModel.setMarketPrice(goods.getMarketPrice());
            goodsModel.setPrice(goods.getPrice());
            goodsModel.setVipPrice(0);//TODO 等待用户接口
            goodsModel.setThumbnail(goods.getThumbnailPic().getValue());
            goodsModel.setSmallPic(goods.getSmallPic().getValue());
            goodsModel.setBigPic(goods.getBigPic().getValue());
            goodsModel.setIterCount(iterCount);
            goodsModels.add(goodsModel);
            iterCount ++;
        }
        goodsPage.setGoodses(goodsModels);
        return goodsPage;
    }

    private String[] getSortProperties(GoodsSearcher goodsSearcher) {
        String sortStr = goodsSearcher.getSort();
        if(sortStr == null) {
            return null;
        }
        String sortDir = sortStr.substring(sortStr.lastIndexOf(",")+1);
        return isSpecifiedDir(sortDir) ?
                sortStr.substring(0,sortStr.lastIndexOf(",")).split(",") :sortStr.split(",");
    }

    private boolean isSpecifiedDir(String sortDir) {
        return "desc".equals(sortDir) || "asc".equals(sortDir);
    }

    private Sort.Direction getSortDirection(GoodsSearcher goodsSearcher) {
        String sortStr = goodsSearcher.getSort();
        if(sortStr == null) {
            return null;
        }
        String sortDir = sortStr.substring(sortStr.lastIndexOf(",")+1);
        return "desc".equals(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    @Override
    public List<GoodsModel> getHotGoodsList(int customerId) throws Exception{
        List<Goods> goodses = goodsRestRepository.searchTop10Sales(customerId);
        List<GoodsModel> goodsModels = new ArrayList<>();
        int iterCount = 1;
        for(Goods goods : goodses) {
            GoodsModel goodsModel = new GoodsModel();
            goodsModel.setId(goods.getId());
            goodsModel.setTitle(goods.getTitle());
            goodsModel.setShelveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(goods.getAutoMarketDate()));
            goodsModel.setSales(goods.getSalesCount());
            goodsModel.setMarketPrice(goods.getMarketPrice());
            goodsModel.setPrice(goods.getPrice());
            goodsModel.setVipPrice(0);//TODO 等待用户接口
            goodsModel.setThumbnail(goods.getThumbnailPic().getValue());
            goodsModel.setSmallPic(goods.getSmallPic().getValue());
            goodsModel.setBigPic(goods.getBigPic().getValue());
            goodsModel.setIterCount(iterCount);
            goodsModels.add(goodsModel);
            iterCount ++;
        }
        return goodsModels;
    }

    private ApiResult<String> invokeGoodsSearchProce(int customerId, GoodsSearcher goodsSearcher) throws Exception{
        Map<String,Object> params = buildSortedParams(customerId,goodsSearcher);
        return httpService.httpGet_prod("http", "api.open.fancat.cn", 8081, "/goodses/search/findByMixed", params);
    }

    private Map<String, Object> buildSortedParams(int customerId, GoodsSearcher goodsSearcher) throws Exception{
        Map<String,Object> params = new TreeMap<>();
        params.put("merchantId",customerId);
        Class clazz = goodsSearcher.getClass();
        Field[] fields = clazz.getFields();
        for(Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
            Method method = pd.getReadMethod();
            params.put(field.getName(),method.invoke(goodsSearcher));
        }
        return params;
    }

    private ApiResult<String> invokeHotGoodsProce(int customerId) throws Exception{
        Map<String,Object> params = new TreeMap<>();
        params.put("merchantId",customerId);
        return httpService.httpGet_prod("http", "api.open.fancat.cn", 8081, "/goodses/search/findTop10BySales", params);
    }
}