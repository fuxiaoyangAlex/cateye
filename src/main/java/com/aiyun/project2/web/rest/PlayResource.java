package com.aiyun.project2.web.rest;

import com.aiyun.project2.domain.Play;
import com.aiyun.project2.service.PlayService;
import com.aiyun.project2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.aiyun.project2.domain.Play}.
 */
@RestController
@RequestMapping("/api")
public class PlayResource {

    private final Logger log = LoggerFactory.getLogger(PlayResource.class);

    private static final String ENTITY_NAME = "play";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlayService playService;

    public PlayResource(PlayService playService) {
        this.playService = playService;
    }

    /**
     * {@code POST  /plays} : Create a new play.
     *
     * @param play the play to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new play, or with status {@code 400 (Bad Request)} if the play has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plays")
    public ResponseEntity<Play> createPlay(@RequestBody Play play) throws URISyntaxException {
        log.debug("REST request to save Play : {}", play);
        if (play.getId() != null) {
            throw new BadRequestAlertException("A new play cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Play result = playService.save(play);
        return ResponseEntity.created(new URI("/api/plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plays} : Updates an existing play.
     *
     * @param play the play to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated play,
     * or with status {@code 400 (Bad Request)} if the play is not valid,
     * or with status {@code 500 (Internal Server Error)} if the play couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plays")
    public ResponseEntity<Play> updatePlay(@RequestBody Play play) throws URISyntaxException {
        log.debug("REST request to update Play : {}", play);
        if (play.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Play result = playService.save(play);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, play.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plays} : get all the plays.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plays in body.
     */
    @GetMapping("/plays")
    public ResponseEntity<List<Play>> getAllPlays(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Plays");
        Page<Play> page = playService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plays/:id} : get the "id" play.
     *
     * @param id the id of the play to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the play, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plays/{id}")
    public ResponseEntity<Play> getPlay(@PathVariable Long id) {
        log.debug("REST request to get Play : {}", id);
        Optional<Play> play = playService.findOne(id);
        return ResponseUtil.wrapOrNotFound(play);
    }

    /**
     * {@code DELETE  /plays/:id} : delete the "id" play.
     *
     * @param id the id of the play to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plays/{id}")
    public ResponseEntity<Void> deletePlay(@PathVariable Long id) {
        log.debug("REST request to delete Play : {}", id);
        playService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
