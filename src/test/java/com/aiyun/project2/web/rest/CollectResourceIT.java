package com.aiyun.project2.web.rest;

import com.aiyun.project2.Project2App;
import com.aiyun.project2.domain.Collect;
import com.aiyun.project2.repository.CollectRepository;
import com.aiyun.project2.service.CollectService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.aiyun.project2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CollectResource} REST controller.
 */
@SpringBootTest(classes = Project2App.class)
public class CollectResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private CollectService collectService;

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

    private MockMvc restCollectMockMvc;

    private Collect collect;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectResource collectResource = new CollectResource(collectService);
        this.restCollectMockMvc = MockMvcBuilders.standaloneSetup(collectResource)
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
    public static Collect createEntity(EntityManager em) {
        Collect collect = new Collect()
            .date(DEFAULT_DATE);
        return collect;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Collect createUpdatedEntity(EntityManager em) {
        Collect collect = new Collect()
            .date(UPDATED_DATE);
        return collect;
    }

    @BeforeEach
    public void initTest() {
        collect = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollect() throws Exception {
        int databaseSizeBeforeCreate = collectRepository.findAll().size();

        // Create the Collect
        restCollectMockMvc.perform(post("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isCreated());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate + 1);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createCollectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectRepository.findAll().size();

        // Create the Collect with an existing ID
        collect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectMockMvc.perform(post("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCollects() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get all the collectList
        restCollectMockMvc.perform(get("/api/collects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collect.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get the collect
        restCollectMockMvc.perform(get("/api/collects/{id}", collect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collect.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollect() throws Exception {
        // Get the collect
        restCollectMockMvc.perform(get("/api/collects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollect() throws Exception {
        // Initialize the database
        collectService.save(collect);

        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Update the collect
        Collect updatedCollect = collectRepository.findById(collect.getId()).get();
        // Disconnect from session so that the updates on updatedCollect are not directly saved in db
        em.detach(updatedCollect);
        updatedCollect
            .date(UPDATED_DATE);

        restCollectMockMvc.perform(put("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCollect)))
            .andExpect(status().isOk());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Create the Collect

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectMockMvc.perform(put("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collect)))
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCollect() throws Exception {
        // Initialize the database
        collectService.save(collect);

        int databaseSizeBeforeDelete = collectRepository.findAll().size();

        // Delete the collect
        restCollectMockMvc.perform(delete("/api/collects/{id}", collect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collect.class);
        Collect collect1 = new Collect();
        collect1.setId(1L);
        Collect collect2 = new Collect();
        collect2.setId(collect1.getId());
        assertThat(collect1).isEqualTo(collect2);
        collect2.setId(2L);
        assertThat(collect1).isNotEqualTo(collect2);
        collect1.setId(null);
        assertThat(collect1).isNotEqualTo(collect2);
    }
}
