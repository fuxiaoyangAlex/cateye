package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.CinemaService;
import com.aiyun.project2.domain.Cinema;
import com.aiyun.project2.repository.CinemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cinema}.
 */
@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    private final Logger log = LoggerFactory.getLogger(CinemaServiceImpl.class);

    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    /**
     * Save a cinema.
     *
     * @param cinema the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cinema save(Cinema cinema) {
        log.debug("Request to save Cinema : {}", cinema);
        return cinemaRepository.save(cinema);
    }

    /**
     * Get all the cinemas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cinema> findAll(Pageable pageable) {
        log.debug("Request to get all Cinemas");
        return cinemaRepository.findAll(pageable);
    }


    /**
     * Get one cinema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cinema> findOne(Long id) {
        log.debug("Request to get Cinema : {}", id);
        return cinemaRepository.findById(id);
    }

    /**
     * Delete the cinema by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cinema : {}", id);
        cinemaRepository.deleteById(id);
    }
}
