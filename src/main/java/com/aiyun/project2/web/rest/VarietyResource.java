package com.aiyun.project2.web.rest;

import com.aiyun.project2.domain.Variety;
import com.aiyun.project2.service.VarietyService;
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
 * REST controller for managing {@link com.aiyun.project2.domain.Variety}.
 */
@RestController
@RequestMapping("/api")
public class VarietyResource {

    private final Logger log = LoggerFactory.getLogger(VarietyResource.class);

    private static final String ENTITY_NAME = "variety";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VarietyService varietyService;

    public VarietyResource(VarietyService varietyService) {
        this.varietyService = varietyService;
    }

    /**
     * {@code POST  /varieties} : Create a new variety.
     *
     * @param variety the variety to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variety, or with status {@code 400 (Bad Request)} if the variety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/varieties")
    public ResponseEntity<Variety> createVariety(@RequestBody Variety variety) throws URISyntaxException {
        log.debug("REST request to save Variety : {}", variety);
        if (variety.getId() != null) {
            throw new BadRequestAlertException("A new variety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Variety result = varietyService.save(variety);
        return ResponseEntity.created(new URI("/api/varieties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /varieties} : Updates an existing variety.
     *
     * @param variety the variety to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variety,
     * or with status {@code 400 (Bad Request)} if the variety is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variety couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/varieties")
    public ResponseEntity<Variety> updateVariety(@RequestBody Variety variety) throws URISyntaxException {
        log.debug("REST request to update Variety : {}", variety);
        if (variety.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Variety result = varietyService.save(variety);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variety.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /varieties} : get all the varieties.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of varieties in body.
     */
    @GetMapping("/varieties")
    public ResponseEntity<List<Variety>> getAllVarieties(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Varieties");
        Page<Variety> page = varietyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /varieties/:id} : get the "id" variety.
     *
     * @param id the id of the variety to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variety, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/varieties/{id}")
    public ResponseEntity<Variety> getVariety(@PathVariable Long id) {
        log.debug("REST request to get Variety : {}", id);
        Optional<Variety> variety = varietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variety);
    }

    /**
     * {@code DELETE  /varieties/:id} : delete the "id" variety.
     *
     * @param id the id of the variety to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/varieties/{id}")
    public ResponseEntity<Void> deleteVariety(@PathVariable Long id) {
        log.debug("REST request to delete Variety : {}", id);
        varietyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
