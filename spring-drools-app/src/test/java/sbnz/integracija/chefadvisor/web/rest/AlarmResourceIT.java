package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.Alarm;
import sbnz.integracija.chefadvisor.repository.AlarmRepository;
import sbnz.integracija.chefadvisor.service.AlarmService;
import sbnz.integracija.chefadvisor.service.dto.AlarmDTO;
import sbnz.integracija.chefadvisor.service.mapper.AlarmMapper;

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
 * Integration tests for the {@link AlarmResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AlarmResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS = 2;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmMockMvc;

    private Alarm alarm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarm createEntity(EntityManager em) {
        Alarm alarm = new Alarm()
            .message(DEFAULT_MESSAGE)
            .numberOfSuspiciousActions(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS);
        return alarm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarm createUpdatedEntity(EntityManager em) {
        Alarm alarm = new Alarm()
            .message(UPDATED_MESSAGE)
            .numberOfSuspiciousActions(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS);
        return alarm;
    }

    @BeforeEach
    public void initTest() {
        alarm = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarm() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);
        restAlarmMockMvc.perform(post("/api/alarms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isCreated());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate + 1);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAlarm.getNumberOfSuspiciousActions()).isEqualTo(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS);
    }

    @Test
    @Transactional
    public void createAlarmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmRepository.findAll().size();

        // Create the Alarm with an existing ID
        alarm.setId(1L);
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmMockMvc.perform(post("/api/alarms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarms() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get all the alarmList
        restAlarmMockMvc.perform(get("/api/alarms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarm.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].numberOfSuspiciousActions").value(hasItem(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS)));
    }
    
    @Test
    @Transactional
    public void getAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", alarm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarm.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.numberOfSuspiciousActions").value(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS));
    }

    @Test
    @Transactional
    public void getNonExistingAlarm() throws Exception {
        // Get the alarm
        restAlarmMockMvc.perform(get("/api/alarms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Update the alarm
        Alarm updatedAlarm = alarmRepository.findById(alarm.getId()).get();
        // Disconnect from session so that the updates on updatedAlarm are not directly saved in db
        em.detach(updatedAlarm);
        updatedAlarm
            .message(UPDATED_MESSAGE)
            .numberOfSuspiciousActions(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS);
        AlarmDTO alarmDTO = alarmMapper.toDto(updatedAlarm);

        restAlarmMockMvc.perform(put("/api/alarms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isOk());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);
        Alarm testAlarm = alarmList.get(alarmList.size() - 1);
        assertThat(testAlarm.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAlarm.getNumberOfSuspiciousActions()).isEqualTo(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarm() throws Exception {
        int databaseSizeBeforeUpdate = alarmRepository.findAll().size();

        // Create the Alarm
        AlarmDTO alarmDTO = alarmMapper.toDto(alarm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmMockMvc.perform(put("/api/alarms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alarm in the database
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarm() throws Exception {
        // Initialize the database
        alarmRepository.saveAndFlush(alarm);

        int databaseSizeBeforeDelete = alarmRepository.findAll().size();

        // Delete the alarm
        restAlarmMockMvc.perform(delete("/api/alarms/{id}", alarm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alarm> alarmList = alarmRepository.findAll();
        assertThat(alarmList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
