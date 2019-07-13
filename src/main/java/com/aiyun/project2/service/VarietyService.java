package com.aiyun.project2.service;

import com.aiyun.project2.domain.Variety;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Variety}.
 */
public interface VarietyService {

    /**
     * Save a variety.
     *
     * @param variety the entity to save.
     * @return the persisted entity.
     */
    Variety save(Variety variety);

    /**
     * Get all the varieties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Variety> findAll(Pageable pageable);


    /**
     * Get the "id" variety.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Variety> findOne(Long id);

    /**
     * Delete the "id" variety.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
