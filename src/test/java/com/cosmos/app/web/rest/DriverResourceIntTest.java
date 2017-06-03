package com.cosmos.app.web.rest;

import com.cosmos.app.TruckersLoyaltyProgramApp;

import com.cosmos.app.domain.Driver;
import com.cosmos.app.repository.DriverRepository;
import com.cosmos.app.service.DriverService;
import com.cosmos.app.service.dto.DriverDTO;
import com.cosmos.app.service.mapper.DriverMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.cosmos.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DriverResource REST controller.
 *
 * @see DriverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TruckersLoyaltyProgramApp.class)
public class DriverResourceIntTest {

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ID_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_COMMON_ROUTES = "AAAAAAAAAA";
    private static final String UPDATED_COMMON_ROUTES = "BBBBBBBBBB";

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private DriverService driverService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DriverResource driverResource = new DriverResource(driverService);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
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
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .cardNumber(DEFAULT_CARD_NUMBER)
            .name(DEFAULT_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedOn(DEFAULT_UPDATED_ON)
            .idCardType(DEFAULT_ID_CARD_TYPE)
            .idCardNumber(DEFAULT_ID_CARD_NUMBER)
            .vehicleNumber(DEFAULT_VEHICLE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .commonRoutes(DEFAULT_COMMON_ROUTES);
        return driver;
    }

    @Before
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testDriver.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDriver.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testDriver.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDriver.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testDriver.getIdCardType()).isEqualTo(DEFAULT_ID_CARD_TYPE);
        assertThat(testDriver.getIdCardNumber()).isEqualTo(DEFAULT_ID_CARD_NUMBER);
        assertThat(testDriver.getVehicleNumber()).isEqualTo(DEFAULT_VEHICLE_NUMBER);
        assertThat(testDriver.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDriver.getCommonRoutes()).isEqualTo(DEFAULT_COMMON_ROUTES);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setCardNumber(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setName(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setPhoneNumber(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setCreatedOn(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setUpdatedOn(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVehicleNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = driverRepository.findAll().size();
        // set the field null
        driver.setVehicleNumber(null);

        // Create the Driver, which fails.
        DriverDTO driverDTO = driverMapper.toDto(driver);

        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].idCardType").value(hasItem(DEFAULT_ID_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].idCardNumber").value(hasItem(DEFAULT_ID_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].vehicleNumber").value(hasItem(DEFAULT_VEHICLE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].commonRoutes").value(hasItem(DEFAULT_COMMON_ROUTES.toString())));
    }

    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)))
            .andExpect(jsonPath("$.idCardType").value(DEFAULT_ID_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.idCardNumber").value(DEFAULT_ID_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.vehicleNumber").value(DEFAULT_VEHICLE_NUMBER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.commonRoutes").value(DEFAULT_COMMON_ROUTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findOne(driver.getId());
        updatedDriver
            .cardNumber(UPDATED_CARD_NUMBER)
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .createdOn(UPDATED_CREATED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .idCardType(UPDATED_ID_CARD_TYPE)
            .idCardNumber(UPDATED_ID_CARD_NUMBER)
            .vehicleNumber(UPDATED_VEHICLE_NUMBER)
            .address(UPDATED_ADDRESS)
            .commonRoutes(UPDATED_COMMON_ROUTES);
        DriverDTO driverDTO = driverMapper.toDto(updatedDriver);

        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testDriver.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDriver.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDriver.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDriver.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testDriver.getIdCardType()).isEqualTo(UPDATED_ID_CARD_TYPE);
        assertThat(testDriver.getIdCardNumber()).isEqualTo(UPDATED_ID_CARD_NUMBER);
        assertThat(testDriver.getVehicleNumber()).isEqualTo(UPDATED_VEHICLE_NUMBER);
        assertThat(testDriver.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDriver.getCommonRoutes()).isEqualTo(UPDATED_COMMON_ROUTES);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Get the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = new Driver();
        driver1.setId(1L);
        Driver driver2 = new Driver();
        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);
        driver2.setId(2L);
        assertThat(driver1).isNotEqualTo(driver2);
        driver1.setId(null);
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverDTO.class);
        DriverDTO driverDTO1 = new DriverDTO();
        driverDTO1.setId(1L);
        DriverDTO driverDTO2 = new DriverDTO();
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
        driverDTO2.setId(driverDTO1.getId());
        assertThat(driverDTO1).isEqualTo(driverDTO2);
        driverDTO2.setId(2L);
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
        driverDTO1.setId(null);
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(driverMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(driverMapper.fromId(null)).isNull();
    }
}
