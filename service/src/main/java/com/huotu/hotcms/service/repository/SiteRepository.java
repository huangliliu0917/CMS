/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.service.repository;

import com.huotu.hotcms.service.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface SiteRepository extends JpaRepository<Site, Long>, JpaSpecificationExecutor<Site> {

    /**
     * 根据商户查询所有站点
     */
    List<Site> findByOwner_IdAndDeletedOrderBySiteIdDesc(long ownerId, boolean deleted);

    Set<Site> findByOwner_IdAndDeleted(long owner, boolean deleted);

    Site findByRecommendDomain(String recommendDomain);

}
