package com.cosmos.app.web.rest;

import com.cosmos.app.TruckersLoyaltyProgramApp;

import com.cosmos.app.domain.Scheme;
import com.cosmos.app.domain.Driver;
import com.cosmos.app.repository.SchemeRepository;
import com.cosmos.app.service.SchemeService;
import com.cosmos.app.service.dto.SchemeDTO;
import com.cosmos.app.service.mapper.SchemeMapper;
import com.cosmos.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchemeResource REST controller.
 *
 * @see SchemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckersLoyaltyProgramApp.class)
public class SchemeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TARGET_VOLUME = 1;
    private static final Integer UPDATED_TARGET_VOLUME = 2;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeMapper schemeMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchemeMockMvc;

    private Scheme scheme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchemeResource schemeResource = new SchemeResource(schemeService);
        this.restSchemeMockMvc = MockMvcBuilders.standaloneSetup(schemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheme createEntity(EntityManager em) {
        Scheme scheme = new Scheme()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .targetVolume(DEFAULT_TARGET_VOLUME);
        // Add required entity
        Driver driver = DriverResourceIntTest.createEntity(em);
        em.persist(driver);
        em.flush();
        scheme.setDriver(driver);
        return scheme;
    }

    @Before
    public void initTest() {
        scheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheme() throws Exception {
        int databaseSizeBeforeCreate = schemeRepository.findAll().size();

        // Create the Scheme
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isCreated());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeCreate + 1);
        Scheme testScheme = schemeList.get(schemeList.size() - 1);
        assertThat(testScheme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScheme.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testScheme.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testScheme.getTargetVolume()).isEqualTo(DEFAULT_TARGET_VOLUME);
    }

    @Test
    @Transactional
    public void createSchemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemeRepository.findAll().size();

        // Create the Scheme with an existing ID
        scheme.setId(1L);
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setStartDate(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setEndDate(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTargetVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setTargetVolume(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemes() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList
        restSchemeMockMvc.perform(get("/api/schemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].targetVolume").value(hasItem(DEFAULT_TARGET_VOLUME)));
    }

    @Test
    @Transactional
    public void getScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", scheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scheme.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.targetVolume").value(DEFAULT_TARGET_VOLUME));
    }

    @Test
    @Transactional
    public void getNonExistingScheme() throws Exception {
        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);
        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();

        // Update the scheme
        Scheme updatedScheme = schemeRepository.findOne(scheme.getId());
        updatedScheme
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .targetVolume(UPDATED_TARGET_VOLUME);
        SchemeDTO schemeDTO = schemeMapper.toDto(updatedScheme);

        restSchemeMockMvc.perform(put("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isOk());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate);
        Scheme testScheme = schemeList.get(schemeList.size() - 1);
        assertThat(testScheme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScheme.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testScheme.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testScheme.getTargetVolume()).isEqualTo(UPDATED_TARGET_VOLUME);
    }

    @Test
    @Transactional
    public void updateNonExistingScheme() throws Exception {
        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();

        // Create the Scheme
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchemeMockMvc.perform(put("/api/schemes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isCreated());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);
        int databaseSizeBeforeDelete = schemeRepository.findAll().size();

        // Get the scheme
        restSchemeMockMvc.perform(delete("/api/schemes/{id}", scheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheme.class);
        Scheme scheme1 = new Scheme();
        scheme1.setId(1L);
        Scheme scheme2 = new Scheme();
        scheme2.setId(scheme1.getId());
        assertThat(scheme1).isEqualTo(scheme2);
        scheme2.setId(2L);
        assertThat(scheme1).isNotEqualTo(scheme2);
        scheme1.setId(null);
        assertThat(scheme1).isNotEqualTo(scheme2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemeDTO.class);
        SchemeDTO schemeDTO1 = new SchemeDTO();
        schemeDTO1.setId(1L);
        SchemeDTO schemeDTO2 = new SchemeDTO();
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
        schemeDTO2.setId(schemeDTO1.getId());
        assertThat(schemeDTO1).isEqualTo(schemeDTO2);
        schemeDTO2.setId(2L);
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
        schemeDTO1.setId(null);
        assertThat(schemeDTO1).isNotEqualTo(schemeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schemeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schemeMapper.fromId(null)).isNull();
    }
}
