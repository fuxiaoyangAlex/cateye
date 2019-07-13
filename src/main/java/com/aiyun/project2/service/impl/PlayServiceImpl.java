package com.aiyun.project2.service.impl;

import com.aiyun.project2.service.PlayService;
import com.aiyun.project2.domain.Play;
import com.aiyun.project2.repository.PlayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Play}.
 */
@Service
@Transactional
public class PlayServiceImpl implements PlayService {

    private final Logger log = LoggerFactory.getLogger(PlayServiceImpl.class);

    private final PlayRepository playRepository;

    public PlayServiceImpl(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    /**
     * Save a play.
     *
     * @param play the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Play save(Play play) {
        log.debug("Request to save Play : {}", play);
        return playRepository.save(play);
    }

    /**
     * Get all the plays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Play> findAll(Pageable pageable) {
        log.debug("Request to get all Plays");
        return playRepository.findAll(pageable);
    }


    /**
     * Get one play by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Play> findOne(Long id) {
        log.debug("Request to get Play : {}", id);
        return playRepository.findById(id);
    }

    /**
     * Delete the play by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Play : {}", id);
        playRepository.deleteById(id);
    }
}
