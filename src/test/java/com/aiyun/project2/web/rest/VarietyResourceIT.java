package com.aiyun.project2.web.rest;

import com.aiyun.project2.Project2App;
import com.aiyun.project2.domain.Variety;
import com.aiyun.project2.repository.VarietyRepository;
import com.aiyun.project2.service.VarietyService;
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
 * Integration tests for the {@Link VarietyResource} REST controller.
 */
@SpringBootTest(classes = Project2App.class)
public class VarietyResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private VarietyService varietyService;

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

    private MockMvc restVarietyMockMvc;

    private Variety variety;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VarietyResource varietyResource = new VarietyResource(varietyService);
        this.restVarietyMockMvc = MockMvcBuilders.standaloneSetup(varietyResource)
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
    public static Variety createEntity(EntityManager em) {
        Variety variety = new Variety()
            .label(DEFAULT_LABEL);
        return variety;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Variety createUpdatedEntity(EntityManager em) {
        Variety variety = new Variety()
            .label(UPDATED_LABEL);
        return variety;
    }

    @BeforeEach
    public void initTest() {
        variety = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariety() throws Exception {
        int databaseSizeBeforeCreate = varietyRepository.findAll().size();

        // Create the Variety
        restVarietyMockMvc.perform(post("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isCreated());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeCreate + 1);
        Variety testVariety = varietyList.get(varietyList.size() - 1);
        assertThat(testVariety.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createVarietyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varietyRepository.findAll().size();

        // Create the Variety with an existing ID
        variety.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarietyMockMvc.perform(post("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isBadRequest());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVarieties() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get all the varietyList
        restVarietyMockMvc.perform(get("/api/varieties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variety.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    @Transactional
    public void getVariety() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get the variety
        restVarietyMockMvc.perform(get("/api/varieties/{id}", variety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variety.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVariety() throws Exception {
        // Get the variety
        restVarietyMockMvc.perform(get("/api/varieties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariety() throws Exception {
        // Initialize the database
        varietyService.save(variety);

        int databaseSizeBeforeUpdate = varietyRepository.findAll().size();

        // Update the variety
        Variety updatedVariety = varietyRepository.findById(variety.getId()).get();
        // Disconnect from session so that the updates on updatedVariety are not directly saved in db
        em.detach(updatedVariety);
        updatedVariety
            .label(UPDATED_LABEL);

        restVarietyMockMvc.perform(put("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariety)))
            .andExpect(status().isOk());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeUpdate);
        Variety testVariety = varietyList.get(varietyList.size() - 1);
        assertThat(testVariety.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingVariety() throws Exception {
        int databaseSizeBeforeUpdate = varietyRepository.findAll().size();

        // Create the Variety

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarietyMockMvc.perform(put("/api/varieties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variety)))
            .andExpect(status().isBadRequest());

        // Validate the Variety in the database
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVariety() throws Exception {
        // Initialize the database
        varietyService.save(variety);

        int databaseSizeBeforeDelete = varietyRepository.findAll().size();

        // Delete the variety
        restVarietyMockMvc.perform(delete("/api/varieties/{id}", variety.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Variety> varietyList = varietyRepository.findAll();
        assertThat(varietyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variety.class);
        Variety variety1 = new Variety();
        variety1.setId(1L);
        Variety variety2 = new Variety();
        variety2.setId(variety1.getId());
        assertThat(variety1).isEqualTo(variety2);
        variety2.setId(2L);
        assertThat(variety1).isNotEqualTo(variety2);
        variety1.setId(null);
        assertThat(variety1).isNotEqualTo(variety2);
    }
}
