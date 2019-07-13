package com.aiyun.project2.web.rest;

import com.aiyun.project2.domain.Cinema;
import com.aiyun.project2.service.CinemaService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.aiyun.project2.domain.Cinema}.
 */
@RestController
@RequestMapping("/api")
public class CinemaResource {

    private final Logger log = LoggerFactory.getLogger(CinemaResource.class);

    private static final String ENTITY_NAME = "cinema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CinemaService cinemaService;

    public CinemaResource(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    /**
     * {@code POST  /cinemas} : Create a new cinema.
     *
     * @param cinema the cinema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cinema, or with status {@code 400 (Bad Request)} if the cinema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cinemas")
    public ResponseEntity<Cinema> createCinema(@Valid @RequestBody Cinema cinema) throws URISyntaxException {
        log.debug("REST request to save Cinema : {}", cinema);
        if (cinema.getId() != null) {
            throw new BadRequestAlertException("A new cinema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cinema result = cinemaService.save(cinema);
        return ResponseEntity.created(new URI("/api/cinemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cinemas} : Updates an existing cinema.
     *
     * @param cinema the cinema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cinema,
     * or with status {@code 400 (Bad Request)} if the cinema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cinema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cinemas")
    public ResponseEntity<Cinema> updateCinema(@Valid @RequestBody Cinema cinema) throws URISyntaxException {
        log.debug("REST request to update Cinema : {}", cinema);
        if (cinema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cinema result = cinemaService.save(cinema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cinema.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cinemas} : get all the cinemas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cinemas in body.
     */
    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Cinemas");
        Page<Cinema> page = cinemaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cinemas/:id} : get the "id" cinema.
     *
     * @param id the id of the cinema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cinema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cinemas/{id}")
    public ResponseEntity<Cinema> getCinema(@PathVariable Long id) {
        log.debug("REST request to get Cinema : {}", id);
        Optional<Cinema> cinema = cinemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cinema);
    }

    /**
     * {@code DELETE  /cinemas/:id} : delete the "id" cinema.
     *
     * @param id the id of the cinema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cinemas/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        log.debug("REST request to delete Cinema : {}", id);
        cinemaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
