package com.huotu.hotcms.repository;

import com.huotu.hotcms.entity.DataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @since 1.0.0
 * @author xhl
 * @time 2015/15/25
 */
public interface ModelRepository extends JpaRepository<DataModel, Long>,JpaSpecificationExecutor {
//    Page<DataModel> findByName(Pageable pageRequest,Specification<DataModel> specification);

//    Page<DataModel> findByName(String name,Pageable pageRequest);
}
