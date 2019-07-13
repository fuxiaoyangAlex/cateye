package com.aiyun.project2.repository;

import com.aiyun.project2.domain.Round;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Round entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

}
