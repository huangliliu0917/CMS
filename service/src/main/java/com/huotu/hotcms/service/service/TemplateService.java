/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.service;

/**
 * Created by wenqi on 2016/7/15.
 */

import java.io.IOException;

/**
 * 模板站点的相关操作
 */
public interface TemplateService {
    /**
     * 点赞
     *
     * @param siteId     站点ID，也就是模板的ID
     * @param customerId 用户ID
     * @return 是否点赞成功，存在网络异常，服务器异常等失误情况
     */
    boolean laud(long siteId, long customerId);

    /**
     * 使用
     *
     * @param templateSiteID 模板站点ID
     * @param customerSiteId 商户站点ID
     * @param mode           模式<ul>
     *                       <li>0为追加模式 - 保持现有数据，再将模板站点下的Category,Page,Content复制到该站点中</li>
     *                       <li>1为替换模式 - 清空原有数据，然后进行复制</li>
     *                       </ul>
     */
    void use(long templateSiteID, long customerSiteId, int mode) throws IOException;
}
