package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.repository.CalorieConfigurationRepository;
import sbnz.integracija.chefadvisor.service.CalorieConfigurationService;
import sbnz.integracija.chefadvisor.service.dto.CalorieConfigurationDTO;
import sbnz.integracija.chefadvisor.service.mapper.CalorieConfigurationMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CalorieConfigurationResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CalorieConfigurationResourceIT {

    private static final Double DEFAULT_BREAKFAST_LOW = 1D;
    private static final Double UPDATED_BREAKFAST_LOW = 2D;

    private static final Double DEFAULT_BREAKFAST_HIGH = 1D;
    private static final Double UPDATED_BREAKFAST_HIGH = 2D;

    private static final Double DEFAULT_LUNCH_LOW = 1D;
    private static final Double UPDATED_LUNCH_LOW = 2D;

    private static final Double DEFAULT_LUNCH_HIGH = 1D;
    private static final Double UPDATED_LUNCH_HIGH = 2D;

    private static final Double DEFAULT_DINNER_LOW = 1D;
    private static final Double UPDATED_DINNER_LOW = 2D;

    private static final Double DEFAULT_DINNER_HIGH = 1D;
    private static final Double UPDATED_DINNER_HIGH = 2D;

    private static final Double DEFAULT_SNACK_LOW = 1D;
    private static final Double UPDATED_SNACK_LOW = 2D;

    private static final Double DEFAULT_SNACK_HIGH = 1D;
    private static final Double UPDATED_SNACK_HIGH = 2D;

    @Autowired
    private CalorieConfigurationRepository calorieConfigurationRepository;

    @Autowired
    private CalorieConfigurationMapper calorieConfigurationMapper;

    @Autowired
    private CalorieConfigurationService calorieConfigurationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalorieConfigurationMockMvc;

    private CalorieConfiguration calorieConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalorieConfiguration createEntity(EntityManager em) {
        CalorieConfiguration calorieConfiguration = new CalorieConfiguration()
            .breakfastLow(DEFAULT_BREAKFAST_LOW)
            .breakfastHigh(DEFAULT_BREAKFAST_HIGH)
            .lunchLow(DEFAULT_LUNCH_LOW)
            .lunchHigh(DEFAULT_LUNCH_HIGH)
            .dinnerLow(DEFAULT_DINNER_LOW)
            .dinnerHigh(DEFAULT_DINNER_HIGH)
            .snackLow(DEFAULT_SNACK_LOW)
            .snackHigh(DEFAULT_SNACK_HIGH);
        return calorieConfiguration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalorieConfiguration createUpdatedEntity(EntityManager em) {
        CalorieConfiguration calorieConfiguration = new CalorieConfiguration()
            .breakfastLow(UPDATED_BREAKFAST_LOW)
            .breakfastHigh(UPDATED_BREAKFAST_HIGH)
            .lunchLow(UPDATED_LUNCH_LOW)
            .lunchHigh(UPDATED_LUNCH_HIGH)
            .dinnerLow(UPDATED_DINNER_LOW)
            .dinnerHigh(UPDATED_DINNER_HIGH)
            .snackLow(UPDATED_SNACK_LOW)
            .snackHigh(UPDATED_SNACK_HIGH);
        return calorieConfiguration;
    }

    @BeforeEach
    public void initTest() {
        calorieConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalorieConfiguration() throws Exception {
        int databaseSizeBeforeCreate = calorieConfigurationRepository.findAll().size();

        // Create the CalorieConfiguration
        CalorieConfigurationDTO calorieConfigurationDTO = calorieConfigurationMapper.toDto(calorieConfiguration);
        restCalorieConfigurationMockMvc.perform(post("/api/calorie-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calorieConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the CalorieConfiguration in the database
        List<CalorieConfiguration> calorieConfigurationList = calorieConfigurationRepository.findAll();
        assertThat(calorieConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        CalorieConfiguration testCalorieConfiguration = calorieConfigurationList.get(calorieConfigurationList.size() - 1);
        assertThat(testCalorieConfiguration.getBreakfastLow()).isEqualTo(DEFAULT_BREAKFAST_LOW);
        assertThat(testCalorieConfiguration.getBreakfastHigh()).isEqualTo(DEFAULT_BREAKFAST_HIGH);
        assertThat(testCalorieConfiguration.getLunchLow()).isEqualTo(DEFAULT_LUNCH_LOW);
        assertThat(testCalorieConfiguration.getLunchHigh()).isEqualTo(DEFAULT_LUNCH_HIGH);
        assertThat(testCalorieConfiguration.getDinnerLow()).isEqualTo(DEFAULT_DINNER_LOW);
        assertThat(testCalorieConfiguration.getDinnerHigh()).isEqualTo(DEFAULT_DINNER_HIGH);
        assertThat(testCalorieConfiguration.getSnackLow()).isEqualTo(DEFAULT_SNACK_LOW);
        assertThat(testCalorieConfiguration.getSnackHigh()).isEqualTo(DEFAULT_SNACK_HIGH);
    }

    @Test
    @Transactional
    public void createCalorieConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calorieConfigurationRepository.findAll().size();

        // Create the CalorieConfiguration with an existing ID
        calorieConfiguration.setId(1L);
        CalorieConfigurationDTO calorieConfigurationDTO = calorieConfigurationMapper.toDto(calorieConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalorieConfigurationMockMvc.perform(post("/api/calorie-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calorieConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalorieConfiguration in the database
        List<CalorieConfiguration> calorieConfigurationList = calorieConfigurationRepository.findAll();
        assertThat(calorieConfigurationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalorieConfigurations() throws Exception {
        // Initialize the database
        calorieConfigurationRepository.saveAndFlush(calorieConfiguration);

        // Get all the calorieConfigurationList
        restCalorieConfigurationMockMvc.perform(get("/api/calorie-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calorieConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].breakfastLow").value(hasItem(DEFAULT_BREAKFAST_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].breakfastHigh").value(hasItem(DEFAULT_BREAKFAST_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].lunchLow").value(hasItem(DEFAULT_LUNCH_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].lunchHigh").value(hasItem(DEFAULT_LUNCH_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].dinnerLow").value(hasItem(DEFAULT_DINNER_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].dinnerHigh").value(hasItem(DEFAULT_DINNER_HIGH.doubleValue())))
            .andExpect(jsonPath("$.[*].snackLow").value(hasItem(DEFAULT_SNACK_LOW.doubleValue())))
            .andExpect(jsonPath("$.[*].snackHigh").value(hasItem(DEFAULT_SNACK_HIGH.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCalorieConfiguration() throws Exception {
        // Initialize the database
        calorieConfigurationRepository.saveAndFlush(calorieConfiguration);

        // Get the calorieConfiguration
        restCalorieConfigurationMockMvc.perform(get("/api/calorie-configurations/{id}", calorieConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calorieConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.breakfastLow").value(DEFAULT_BREAKFAST_LOW.doubleValue()))
            .andExpect(jsonPath("$.breakfastHigh").value(DEFAULT_BREAKFAST_HIGH.doubleValue()))
            .andExpect(jsonPath("$.lunchLow").value(DEFAULT_LUNCH_LOW.doubleValue()))
            .andExpect(jsonPath("$.lunchHigh").value(DEFAULT_LUNCH_HIGH.doubleValue()))
            .andExpect(jsonPath("$.dinnerLow").value(DEFAULT_DINNER_LOW.doubleValue()))
            .andExpect(jsonPath("$.dinnerHigh").value(DEFAULT_DINNER_HIGH.doubleValue()))
            .andExpect(jsonPath("$.snackLow").value(DEFAULT_SNACK_LOW.doubleValue()))
            .andExpect(jsonPath("$.snackHigh").value(DEFAULT_SNACK_HIGH.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCalorieConfiguration() throws Exception {
        // Get the calorieConfiguration
        restCalorieConfigurationMockMvc.perform(get("/api/calorie-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalorieConfiguration() throws Exception {
        // Initialize the database
        calorieConfigurationRepository.saveAndFlush(calorieConfiguration);

        int databaseSizeBeforeUpdate = calorieConfigurationRepository.findAll().size();

        // Update the calorieConfiguration
        CalorieConfiguration updatedCalorieConfiguration = calorieConfigurationRepository.findById(calorieConfiguration.getId()).get();
        // Disconnect from session so that the updates on updatedCalorieConfiguration are not directly saved in db
        em.detach(updatedCalorieConfiguration);
        updatedCalorieConfiguration
            .breakfastLow(UPDATED_BREAKFAST_LOW)
            .breakfastHigh(UPDATED_BREAKFAST_HIGH)
            .lunchLow(UPDATED_LUNCH_LOW)
            .lunchHigh(UPDATED_LUNCH_HIGH)
            .dinnerLow(UPDATED_DINNER_LOW)
            .dinnerHigh(UPDATED_DINNER_HIGH)
            .snackLow(UPDATED_SNACK_LOW)
            .snackHigh(UPDATED_SNACK_HIGH);
        CalorieConfigurationDTO calorieConfigurationDTO = calorieConfigurationMapper.toDto(updatedCalorieConfiguration);

        restCalorieConfigurationMockMvc.perform(put("/api/calorie-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calorieConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the CalorieConfiguration in the database
        List<CalorieConfiguration> calorieConfigurationList = calorieConfigurationRepository.findAll();
        assertThat(calorieConfigurationList).hasSize(databaseSizeBeforeUpdate);
        CalorieConfiguration testCalorieConfiguration = calorieConfigurationList.get(calorieConfigurationList.size() - 1);
        assertThat(testCalorieConfiguration.getBreakfastLow()).isEqualTo(UPDATED_BREAKFAST_LOW);
        assertThat(testCalorieConfiguration.getBreakfastHigh()).isEqualTo(UPDATED_BREAKFAST_HIGH);
        assertThat(testCalorieConfiguration.getLunchLow()).isEqualTo(UPDATED_LUNCH_LOW);
        assertThat(testCalorieConfiguration.getLunchHigh()).isEqualTo(UPDATED_LUNCH_HIGH);
        assertThat(testCalorieConfiguration.getDinnerLow()).isEqualTo(UPDATED_DINNER_LOW);
        assertThat(testCalorieConfiguration.getDinnerHigh()).isEqualTo(UPDATED_DINNER_HIGH);
        assertThat(testCalorieConfiguration.getSnackLow()).isEqualTo(UPDATED_SNACK_LOW);
        assertThat(testCalorieConfiguration.getSnackHigh()).isEqualTo(UPDATED_SNACK_HIGH);
    }

    @Test
    @Transactional
    public void updateNonExistingCalorieConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = calorieConfigurationRepository.findAll().size();

        // Create the CalorieConfiguration
        CalorieConfigurationDTO calorieConfigurationDTO = calorieConfigurationMapper.toDto(calorieConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalorieConfigurationMockMvc.perform(put("/api/calorie-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calorieConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalorieConfiguration in the database
        List<CalorieConfiguration> calorieConfigurationList = calorieConfigurationRepository.findAll();
        assertThat(calorieConfigurationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalorieConfiguration() throws Exception {
        // Initialize the database
        calorieConfigurationRepository.saveAndFlush(calorieConfiguration);

        int databaseSizeBeforeDelete = calorieConfigurationRepository.findAll().size();

        // Delete the calorieConfiguration
        restCalorieConfigurationMockMvc.perform(delete("/api/calorie-configurations/{id}", calorieConfiguration.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalorieConfiguration> calorieConfigurationList = calorieConfigurationRepository.findAll();
        assertThat(calorieConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
