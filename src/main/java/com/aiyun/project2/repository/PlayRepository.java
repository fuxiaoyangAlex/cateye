package com.aiyun.project2.repository;

import com.aiyun.project2.domain.Play;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Play entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {

}
