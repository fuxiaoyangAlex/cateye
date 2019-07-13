package com.aiyun.project2.web.rest;

import com.aiyun.project2.Project2App;
import com.aiyun.project2.domain.Play;
import com.aiyun.project2.repository.PlayRepository;
import com.aiyun.project2.service.PlayService;
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
 * Integration tests for the {@Link PlayResource} REST controller.
 */
@SpringBootTest(classes = Project2App.class)
public class PlayResourceIT {

    @Autowired
    private PlayRepository playRepository;

    @Autowired
    private PlayService playService;

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

    private MockMvc restPlayMockMvc;

    private Play play;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayResource playResource = new PlayResource(playService);
        this.restPlayMockMvc = MockMvcBuilders.standaloneSetup(playResource)
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
    public static Play createEntity(EntityManager em) {
        Play play = new Play();
        return play;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Play createUpdatedEntity(EntityManager em) {
        Play play = new Play();
        return play;
    }

    @BeforeEach
    public void initTest() {
        play = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlay() throws Exception {
        int databaseSizeBeforeCreate = playRepository.findAll().size();

        // Create the Play
        restPlayMockMvc.perform(post("/api/plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isCreated());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeCreate + 1);
        Play testPlay = playList.get(playList.size() - 1);
    }

    @Test
    @Transactional
    public void createPlayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playRepository.findAll().size();

        // Create the Play with an existing ID
        play.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayMockMvc.perform(post("/api/plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlays() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        // Get all the playList
        restPlayMockMvc.perform(get("/api/plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(play.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPlay() throws Exception {
        // Initialize the database
        playRepository.saveAndFlush(play);

        // Get the play
        restPlayMockMvc.perform(get("/api/plays/{id}", play.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(play.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlay() throws Exception {
        // Get the play
        restPlayMockMvc.perform(get("/api/plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlay() throws Exception {
        // Initialize the database
        playService.save(play);

        int databaseSizeBeforeUpdate = playRepository.findAll().size();

        // Update the play
        Play updatedPlay = playRepository.findById(play.getId()).get();
        // Disconnect from session so that the updates on updatedPlay are not directly saved in db
        em.detach(updatedPlay);

        restPlayMockMvc.perform(put("/api/plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlay)))
            .andExpect(status().isOk());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
        Play testPlay = playList.get(playList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPlay() throws Exception {
        int databaseSizeBeforeUpdate = playRepository.findAll().size();

        // Create the Play

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayMockMvc.perform(put("/api/plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(play)))
            .andExpect(status().isBadRequest());

        // Validate the Play in the database
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlay() throws Exception {
        // Initialize the database
        playService.save(play);

        int databaseSizeBeforeDelete = playRepository.findAll().size();

        // Delete the play
        restPlayMockMvc.perform(delete("/api/plays/{id}", play.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Play> playList = playRepository.findAll();
        assertThat(playList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Play.class);
        Play play1 = new Play();
        play1.setId(1L);
        Play play2 = new Play();
        play2.setId(play1.getId());
        assertThat(play1).isEqualTo(play2);
        play2.setId(2L);
        assertThat(play1).isNotEqualTo(play2);
        play1.setId(null);
        assertThat(play1).isNotEqualTo(play2);
    }
}
