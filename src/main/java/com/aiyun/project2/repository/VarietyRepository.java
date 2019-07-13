package com.aiyun.project2.repository;

import com.aiyun.project2.domain.Variety;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Variety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VarietyRepository extends JpaRepository<Variety, Long> {

}
