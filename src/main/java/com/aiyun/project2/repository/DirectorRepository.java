package com.aiyun.project2.repository;

import com.aiyun.project2.domain.Director;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Director entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

}
