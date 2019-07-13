package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.CollectService;
import com.aiyun.project2.domain.Collect;
import com.aiyun.project2.repository.CollectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Collect}.
 */
@Service
@Transactional
public class CollectServiceImpl implements CollectService {

    private final Logger log = LoggerFactory.getLogger(CollectServiceImpl.class);

    private final CollectRepository collectRepository;

    public CollectServiceImpl(CollectRepository collectRepository) {
        this.collectRepository = collectRepository;
    }

    /**
     * Save a collect.
     *
     * @param collect the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Collect save(Collect collect) {
        log.debug("Request to save Collect : {}", collect);
        return collectRepository.save(collect);
    }

    /**
     * Get all the collects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Collect> findAll(Pageable pageable) {
        log.debug("Request to get all Collects");
        return collectRepository.findAll(pageable);
    }


    /**
     * Get one collect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Collect> findOne(Long id) {
        log.debug("Request to get Collect : {}", id);
        return collectRepository.findById(id);
    }

    /**
     * Delete the collect by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collect : {}", id);
        collectRepository.deleteById(id);
    }
}
