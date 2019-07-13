package com.aiyun.project2.web.rest;

import com.aiyun.project2.domain.Collect;
import com.aiyun.project2.service.CollectService;
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
 * REST controller for managing {@link com.aiyun.project2.domain.Collect}.
 */
@RestController
@RequestMapping("/api")
public class CollectResource {

    private final Logger log = LoggerFactory.getLogger(CollectResource.class);

    private static final String ENTITY_NAME = "collect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectService collectService;

    public CollectResource(CollectService collectService) {
        this.collectService = collectService;
    }

    /**
     * {@code POST  /collects} : Create a new collect.
     *
     * @param collect the collect to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collect, or with status {@code 400 (Bad Request)} if the collect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collects")
    public ResponseEntity<Collect> createCollect(@RequestBody Collect collect) throws URISyntaxException {
        log.debug("REST request to save Collect : {}", collect);
        if (collect.getId() != null) {
            throw new BadRequestAlertException("A new collect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Collect result = collectService.save(collect);
        return ResponseEntity.created(new URI("/api/collects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collects} : Updates an existing collect.
     *
     * @param collect the collect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collect,
     * or with status {@code 400 (Bad Request)} if the collect is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collects")
    public ResponseEntity<Collect> updateCollect(@RequestBody Collect collect) throws URISyntaxException {
        log.debug("REST request to update Collect : {}", collect);
        if (collect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Collect result = collectService.save(collect);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collect.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /collects} : get all the collects.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collects in body.
     */
    @GetMapping("/collects")
    public ResponseEntity<List<Collect>> getAllCollects(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Collects");
        Page<Collect> page = collectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collects/:id} : get the "id" collect.
     *
     * @param id the id of the collect to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collect, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collects/{id}")
    public ResponseEntity<Collect> getCollect(@PathVariable Long id) {
        log.debug("REST request to get Collect : {}", id);
        Optional<Collect> collect = collectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collect);
    }

    /**
     * {@code DELETE  /collects/:id} : delete the "id" collect.
     *
     * @param id the id of the collect to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collects/{id}")
    public ResponseEntity<Void> deleteCollect(@PathVariable Long id) {
        log.debug("REST request to delete Collect : {}", id);
        collectService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
