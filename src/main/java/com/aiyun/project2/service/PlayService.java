package com.aiyun.project2.service;

import com.aiyun.project2.domain.Play;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Play}.
 */
public interface PlayService {

    /**
     * Save a play.
     *
     * @param play the entity to save.
     * @return the persisted entity.
     */
    Play save(Play play);

    /**
     * Get all the plays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Play> findAll(Pageable pageable);


    /**
     * Get the "id" play.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Play> findOne(Long id);

    /**
     * Delete the "id" play.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
