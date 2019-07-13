package com.aiyun.project2.web.rest;

import com.aiyun.project2.Project2App;
import com.aiyun.project2.domain.Cinema;
import com.aiyun.project2.repository.CinemaRepository;
import com.aiyun.project2.service.CinemaService;
import com.aiyun.project2.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.aiyun.project2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CinemaResource} REST controller.
 */
@SpringBootTest(classes = Project2App.class)
public class CinemaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCinemaMockMvc;

    private Cinema cinema;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CinemaResource cinemaResource = new CinemaResource(cinemaService);
        this.restCinemaMockMvc = MockMvcBuilders.standaloneSetup(cinemaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinema createEntity(EntityManager em) {
        Cinema cinema = new Cinema()
            .name(DEFAULT_NAME);
        return cinema;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cinema createUpdatedEntity(EntityManager em) {
        Cinema cinema = new Cinema()
            .name(UPDATED_NAME);
        return cinema;
    }

    @BeforeEach
    public void initTest() {
        cinema = createEntity(em);
    }

    @Test
    @Transactional
    public void createCinema() throws Exception {
        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();

        // Create the Cinema
        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinema)))
            .andExpect(status().isCreated());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate + 1);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCinemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cinemaRepository.findAll().size();

        // Create the Cinema with an existing ID
        cinema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinema)))
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cinemaRepository.findAll().size();
        // set the field null
        cinema.setName(null);

        // Create the Cinema, which fails.

        restCinemaMockMvc.perform(post("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinema)))
            .andExpect(status().isBadRequest());

        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCinemas() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get all the cinemaList
        restCinemaMockMvc.perform(get("/api/cinemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cinema.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCinema() throws Exception {
        // Initialize the database
        cinemaRepository.saveAndFlush(cinema);

        // Get the cinema
        restCinemaMockMvc.perform(get("/api/cinemas/{id}", cinema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cinema.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCinema() throws Exception {
        // Get the cinema
        restCinemaMockMvc.perform(get("/api/cinemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCinema() throws Exception {
        // Initialize the database
        cinemaService.save(cinema);

        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Update the cinema
        Cinema updatedCinema = cinemaRepository.findById(cinema.getId()).get();
        // Disconnect from session so that the updates on updatedCinema are not directly saved in db
        em.detach(updatedCinema);
        updatedCinema
            .name(UPDATED_NAME);

        restCinemaMockMvc.perform(put("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCinema)))
            .andExpect(status().isOk());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
        Cinema testCinema = cinemaList.get(cinemaList.size() - 1);
        assertThat(testCinema.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCinema() throws Exception {
        int databaseSizeBeforeUpdate = cinemaRepository.findAll().size();

        // Create the Cinema

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCinemaMockMvc.perform(put("/api/cinemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cinema)))
            .andExpect(status().isBadRequest());

        // Validate the Cinema in the database
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCinema() throws Exception {
        // Initialize the database
        cinemaService.save(cinema);

        int databaseSizeBeforeDelete = cinemaRepository.findAll().size();

        // Delete the cinema
        restCinemaMockMvc.perform(delete("/api/cinemas/{id}", cinema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cinema> cinemaList = cinemaRepository.findAll();
        assertThat(cinemaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cinema.class);
        Cinema cinema1 = new Cinema();
        cinema1.setId(1L);
        Cinema cinema2 = new Cinema();
        cinema2.setId(cinema1.getId());
        assertThat(cinema1).isEqualTo(cinema2);
        cinema2.setId(2L);
        assertThat(cinema1).isNotEqualTo(cinema2);
        cinema1.setId(null);
        assertThat(cinema1).isNotEqualTo(cinema2);
    }
}
