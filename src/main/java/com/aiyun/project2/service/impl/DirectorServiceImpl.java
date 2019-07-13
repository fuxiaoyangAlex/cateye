package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.DirectorService;
import com.aiyun.project2.domain.Director;
import com.aiyun.project2.repository.DirectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Director}.
 */
@Service
@Transactional
public class DirectorServiceImpl implements DirectorService {

    private final Logger log = LoggerFactory.getLogger(DirectorServiceImpl.class);

    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    /**
     * Save a director.
     *
     * @param director the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Director save(Director director) {
        log.debug("Request to save Director : {}", director);
        return directorRepository.save(director);
    }

    /**
     * Get all the directors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Director> findAll(Pageable pageable) {
        log.debug("Request to get all Directors");
        return directorRepository.findAll(pageable);
    }


    /**
     * Get one director by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Director> findOne(Long id) {
        log.debug("Request to get Director : {}", id);
        return directorRepository.findById(id);
    }

    /**
     * Delete the director by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Director : {}", id);
        directorRepository.deleteById(id);
    }
}
