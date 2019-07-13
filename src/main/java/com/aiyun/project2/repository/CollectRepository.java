package com.aiyun.project2.repository;

import com.aiyun.project2.domain.Collect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Collect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

}
