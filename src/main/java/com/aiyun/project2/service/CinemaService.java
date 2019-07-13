package com.aiyun.project2.service;

import com.aiyun.project2.domain.Cinema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cinema}.
 */
public interface CinemaService {

    /**
     * Save a cinema.
     *
     * @param cinema the entity to save.
     * @return the persisted entity.
     */
    Cinema save(Cinema cinema);

    /**
     * Get all the cinemas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cinema> findAll(Pageable pageable);


    /**
     * Get the "id" cinema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cinema> findOne(Long id);

    /**
     * Delete the "id" cinema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
