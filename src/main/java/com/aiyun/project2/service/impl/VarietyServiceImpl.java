package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.VarietyService;
import com.aiyun.project2.domain.Variety;
import com.aiyun.project2.repository.VarietyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Variety}.
 */
@Service
@Transactional
public class VarietyServiceImpl implements VarietyService {

    private final Logger log = LoggerFactory.getLogger(VarietyServiceImpl.class);

    private final VarietyRepository varietyRepository;

    public VarietyServiceImpl(VarietyRepository varietyRepository) {
        this.varietyRepository = varietyRepository;
    }

    /**
     * Save a variety.
     *
     * @param variety the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Variety save(Variety variety) {
        log.debug("Request to save Variety : {}", variety);
        return varietyRepository.save(variety);
    }

    /**
     * Get all the varieties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Variety> findAll(Pageable pageable) {
        log.debug("Request to get all Varieties");
        return varietyRepository.findAll(pageable);
    }


    /**
     * Get one variety by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Variety> findOne(Long id) {
        log.debug("Request to get Variety : {}", id);
        return varietyRepository.findById(id);
    }

    /**
     * Delete the variety by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Variety : {}", id);
        varietyRepository.deleteById(id);
    }
}
