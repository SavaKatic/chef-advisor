package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate;
import sbnz.integracija.chefadvisor.repository.AlarmTriggerTemplateRepository;
import sbnz.integracija.chefadvisor.service.AlarmTriggerTemplateService;
import sbnz.integracija.chefadvisor.service.dto.AlarmTriggerTemplateDTO;
import sbnz.integracija.chefadvisor.service.mapper.AlarmTriggerTemplateMapper;

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
 * Integration tests for the {@link AlarmTriggerTemplateResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AlarmTriggerTemplateResourceIT {

    private static final Integer DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS = 2;

    private static final String DEFAULT_WARNING_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_WARNING_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private AlarmTriggerTemplateRepository alarmTriggerTemplateRepository;

    @Autowired
    private AlarmTriggerTemplateMapper alarmTriggerTemplateMapper;

    @Autowired
    private AlarmTriggerTemplateService alarmTriggerTemplateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmTriggerTemplateMockMvc;

    private AlarmTriggerTemplate alarmTriggerTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmTriggerTemplate createEntity(EntityManager em) {
        AlarmTriggerTemplate alarmTriggerTemplate = new AlarmTriggerTemplate()
            .numberOfSuspiciousActions(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS)
            .warningMessage(DEFAULT_WARNING_MESSAGE);
        return alarmTriggerTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmTriggerTemplate createUpdatedEntity(EntityManager em) {
        AlarmTriggerTemplate alarmTriggerTemplate = new AlarmTriggerTemplate()
            .numberOfSuspiciousActions(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS)
            .warningMessage(UPDATED_WARNING_MESSAGE);
        return alarmTriggerTemplate;
    }

    @BeforeEach
    public void initTest() {
        alarmTriggerTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmTriggerTemplate() throws Exception {
        int databaseSizeBeforeCreate = alarmTriggerTemplateRepository.findAll().size();

        // Create the AlarmTriggerTemplate
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO = alarmTriggerTemplateMapper.toDto(alarmTriggerTemplate);
        restAlarmTriggerTemplateMockMvc.perform(post("/api/alarm-trigger-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmTriggerTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the AlarmTriggerTemplate in the database
        List<AlarmTriggerTemplate> alarmTriggerTemplateList = alarmTriggerTemplateRepository.findAll();
        assertThat(alarmTriggerTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmTriggerTemplate testAlarmTriggerTemplate = alarmTriggerTemplateList.get(alarmTriggerTemplateList.size() - 1);
        assertThat(testAlarmTriggerTemplate.getNumberOfSuspiciousActions()).isEqualTo(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS);
        assertThat(testAlarmTriggerTemplate.getWarningMessage()).isEqualTo(DEFAULT_WARNING_MESSAGE);
    }

    @Test
    @Transactional
    public void createAlarmTriggerTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmTriggerTemplateRepository.findAll().size();

        // Create the AlarmTriggerTemplate with an existing ID
        alarmTriggerTemplate.setId(1L);
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO = alarmTriggerTemplateMapper.toDto(alarmTriggerTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmTriggerTemplateMockMvc.perform(post("/api/alarm-trigger-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmTriggerTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmTriggerTemplate in the database
        List<AlarmTriggerTemplate> alarmTriggerTemplateList = alarmTriggerTemplateRepository.findAll();
        assertThat(alarmTriggerTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmTriggerTemplates() throws Exception {
        // Initialize the database
        alarmTriggerTemplateRepository.saveAndFlush(alarmTriggerTemplate);

        // Get all the alarmTriggerTemplateList
        restAlarmTriggerTemplateMockMvc.perform(get("/api/alarm-trigger-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmTriggerTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfSuspiciousActions").value(hasItem(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS)))
            .andExpect(jsonPath("$.[*].warningMessage").value(hasItem(DEFAULT_WARNING_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void getAlarmTriggerTemplate() throws Exception {
        // Initialize the database
        alarmTriggerTemplateRepository.saveAndFlush(alarmTriggerTemplate);

        // Get the alarmTriggerTemplate
        restAlarmTriggerTemplateMockMvc.perform(get("/api/alarm-trigger-templates/{id}", alarmTriggerTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmTriggerTemplate.getId().intValue()))
            .andExpect(jsonPath("$.numberOfSuspiciousActions").value(DEFAULT_NUMBER_OF_SUSPICIOUS_ACTIONS))
            .andExpect(jsonPath("$.warningMessage").value(DEFAULT_WARNING_MESSAGE));
    }

    @Test
    @Transactional
    public void getNonExistingAlarmTriggerTemplate() throws Exception {
        // Get the alarmTriggerTemplate
        restAlarmTriggerTemplateMockMvc.perform(get("/api/alarm-trigger-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmTriggerTemplate() throws Exception {
        // Initialize the database
        alarmTriggerTemplateRepository.saveAndFlush(alarmTriggerTemplate);

        int databaseSizeBeforeUpdate = alarmTriggerTemplateRepository.findAll().size();

        // Update the alarmTriggerTemplate
        AlarmTriggerTemplate updatedAlarmTriggerTemplate = alarmTriggerTemplateRepository.findById(alarmTriggerTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmTriggerTemplate are not directly saved in db
        em.detach(updatedAlarmTriggerTemplate);
        updatedAlarmTriggerTemplate
            .numberOfSuspiciousActions(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS)
            .warningMessage(UPDATED_WARNING_MESSAGE);
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO = alarmTriggerTemplateMapper.toDto(updatedAlarmTriggerTemplate);

        restAlarmTriggerTemplateMockMvc.perform(put("/api/alarm-trigger-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmTriggerTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the AlarmTriggerTemplate in the database
        List<AlarmTriggerTemplate> alarmTriggerTemplateList = alarmTriggerTemplateRepository.findAll();
        assertThat(alarmTriggerTemplateList).hasSize(databaseSizeBeforeUpdate);
        AlarmTriggerTemplate testAlarmTriggerTemplate = alarmTriggerTemplateList.get(alarmTriggerTemplateList.size() - 1);
        assertThat(testAlarmTriggerTemplate.getNumberOfSuspiciousActions()).isEqualTo(UPDATED_NUMBER_OF_SUSPICIOUS_ACTIONS);
        assertThat(testAlarmTriggerTemplate.getWarningMessage()).isEqualTo(UPDATED_WARNING_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmTriggerTemplate() throws Exception {
        int databaseSizeBeforeUpdate = alarmTriggerTemplateRepository.findAll().size();

        // Create the AlarmTriggerTemplate
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO = alarmTriggerTemplateMapper.toDto(alarmTriggerTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmTriggerTemplateMockMvc.perform(put("/api/alarm-trigger-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmTriggerTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmTriggerTemplate in the database
        List<AlarmTriggerTemplate> alarmTriggerTemplateList = alarmTriggerTemplateRepository.findAll();
        assertThat(alarmTriggerTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmTriggerTemplate() throws Exception {
        // Initialize the database
        alarmTriggerTemplateRepository.saveAndFlush(alarmTriggerTemplate);

        int databaseSizeBeforeDelete = alarmTriggerTemplateRepository.findAll().size();

        // Delete the alarmTriggerTemplate
        restAlarmTriggerTemplateMockMvc.perform(delete("/api/alarm-trigger-templates/{id}", alarmTriggerTemplate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmTriggerTemplate> alarmTriggerTemplateList = alarmTriggerTemplateRepository.findAll();
        assertThat(alarmTriggerTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
