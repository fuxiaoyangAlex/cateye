package com.aiyun.project2.web.rest;

import com.aiyun.project2.Project2App;
import com.aiyun.project2.domain.Round;
import com.aiyun.project2.repository.RoundRepository;
import com.aiyun.project2.service.RoundService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.aiyun.project2.web.rest.TestUtil.sameInstant;
import static com.aiyun.project2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RoundResource} REST controller.
 */
@SpringBootTest(classes = Project2App.class)
public class RoundResourceIT {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundService roundService;

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

    private MockMvc restRoundMockMvc;

    private Round round;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoundResource roundResource = new RoundResource(roundService);
        this.restRoundMockMvc = MockMvcBuilders.standaloneSetup(roundResource)
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
    public static Round createEntity(EntityManager em) {
        Round round = new Round()
            .time(DEFAULT_TIME)
            .duration(DEFAULT_DURATION)
            .price(DEFAULT_PRICE);
        return round;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Round createUpdatedEntity(EntityManager em) {
        Round round = new Round()
            .time(UPDATED_TIME)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);
        return round;
    }

    @BeforeEach
    public void initTest() {
        round = createEntity(em);
    }

    @Test
    @Transactional
    public void createRound() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate + 1);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testRound.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testRound.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createRoundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round with an existing ID
        round.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRounds() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get all the roundList
        restRoundMockMvc.perform(get("/api/rounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(round.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @Test
    @Transactional
    public void getRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", round.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(round.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingRound() throws Exception {
        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRound() throws Exception {
        // Initialize the database
        roundService.save(round);

        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round
        Round updatedRound = roundRepository.findById(round.getId()).get();
        // Disconnect from session so that the updates on updatedRound are not directly saved in db
        em.detach(updatedRound);
        updatedRound
            .time(UPDATED_TIME)
            .duration(UPDATED_DURATION)
            .price(UPDATED_PRICE);

        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRound)))
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testRound.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testRound.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Create the Round

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRound() throws Exception {
        // Initialize the database
        roundService.save(round);

        int databaseSizeBeforeDelete = roundRepository.findAll().size();

        // Delete the round
        restRoundMockMvc.perform(delete("/api/rounds/{id}", round.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Round.class);
        Round round1 = new Round();
        round1.setId(1L);
        Round round2 = new Round();
        round2.setId(round1.getId());
        assertThat(round1).isEqualTo(round2);
        round2.setId(2L);
        assertThat(round1).isNotEqualTo(round2);
        round1.setId(null);
        assertThat(round1).isNotEqualTo(round2);
    }
}
