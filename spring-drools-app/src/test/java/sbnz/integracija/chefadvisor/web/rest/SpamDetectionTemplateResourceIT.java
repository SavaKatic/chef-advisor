package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate;
import sbnz.integracija.chefadvisor.repository.SpamDetectionTemplateRepository;
import sbnz.integracija.chefadvisor.service.SpamDetectionTemplateService;
import sbnz.integracija.chefadvisor.service.dto.SpamDetectionTemplateDTO;
import sbnz.integracija.chefadvisor.service.mapper.SpamDetectionTemplateMapper;

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
 * Integration tests for the {@link SpamDetectionTemplateResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SpamDetectionTemplateResourceIT {

    private static final Integer DEFAULT_NUMBER_OF_COMMENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_COMMENTS = 2;

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private SpamDetectionTemplateRepository spamDetectionTemplateRepository;

    @Autowired
    private SpamDetectionTemplateMapper spamDetectionTemplateMapper;

    @Autowired
    private SpamDetectionTemplateService spamDetectionTemplateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpamDetectionTemplateMockMvc;

    private SpamDetectionTemplate spamDetectionTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpamDetectionTemplate createEntity(EntityManager em) {
        SpamDetectionTemplate spamDetectionTemplate = new SpamDetectionTemplate()
            .numberOfComments(DEFAULT_NUMBER_OF_COMMENTS)
            .rating(DEFAULT_RATING)
            .reason(DEFAULT_REASON);
        return spamDetectionTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpamDetectionTemplate createUpdatedEntity(EntityManager em) {
        SpamDetectionTemplate spamDetectionTemplate = new SpamDetectionTemplate()
            .numberOfComments(UPDATED_NUMBER_OF_COMMENTS)
            .rating(UPDATED_RATING)
            .reason(UPDATED_REASON);
        return spamDetectionTemplate;
    }

    @BeforeEach
    public void initTest() {
        spamDetectionTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpamDetectionTemplate() throws Exception {
        int databaseSizeBeforeCreate = spamDetectionTemplateRepository.findAll().size();

        // Create the SpamDetectionTemplate
        SpamDetectionTemplateDTO spamDetectionTemplateDTO = spamDetectionTemplateMapper.toDto(spamDetectionTemplate);
        restSpamDetectionTemplateMockMvc.perform(post("/api/spam-detection-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spamDetectionTemplateDTO)))
            .andExpect(status().isCreated());

        // Validate the SpamDetectionTemplate in the database
        List<SpamDetectionTemplate> spamDetectionTemplateList = spamDetectionTemplateRepository.findAll();
        assertThat(spamDetectionTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SpamDetectionTemplate testSpamDetectionTemplate = spamDetectionTemplateList.get(spamDetectionTemplateList.size() - 1);
        assertThat(testSpamDetectionTemplate.getNumberOfComments()).isEqualTo(DEFAULT_NUMBER_OF_COMMENTS);
        assertThat(testSpamDetectionTemplate.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testSpamDetectionTemplate.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createSpamDetectionTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spamDetectionTemplateRepository.findAll().size();

        // Create the SpamDetectionTemplate with an existing ID
        spamDetectionTemplate.setId(1L);
        SpamDetectionTemplateDTO spamDetectionTemplateDTO = spamDetectionTemplateMapper.toDto(spamDetectionTemplate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpamDetectionTemplateMockMvc.perform(post("/api/spam-detection-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spamDetectionTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpamDetectionTemplate in the database
        List<SpamDetectionTemplate> spamDetectionTemplateList = spamDetectionTemplateRepository.findAll();
        assertThat(spamDetectionTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpamDetectionTemplates() throws Exception {
        // Initialize the database
        spamDetectionTemplateRepository.saveAndFlush(spamDetectionTemplate);

        // Get all the spamDetectionTemplateList
        restSpamDetectionTemplateMockMvc.perform(get("/api/spam-detection-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spamDetectionTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfComments").value(hasItem(DEFAULT_NUMBER_OF_COMMENTS)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)));
    }
    
    @Test
    @Transactional
    public void getSpamDetectionTemplate() throws Exception {
        // Initialize the database
        spamDetectionTemplateRepository.saveAndFlush(spamDetectionTemplate);

        // Get the spamDetectionTemplate
        restSpamDetectionTemplateMockMvc.perform(get("/api/spam-detection-templates/{id}", spamDetectionTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spamDetectionTemplate.getId().intValue()))
            .andExpect(jsonPath("$.numberOfComments").value(DEFAULT_NUMBER_OF_COMMENTS))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON));
    }

    @Test
    @Transactional
    public void getNonExistingSpamDetectionTemplate() throws Exception {
        // Get the spamDetectionTemplate
        restSpamDetectionTemplateMockMvc.perform(get("/api/spam-detection-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpamDetectionTemplate() throws Exception {
        // Initialize the database
        spamDetectionTemplateRepository.saveAndFlush(spamDetectionTemplate);

        int databaseSizeBeforeUpdate = spamDetectionTemplateRepository.findAll().size();

        // Update the spamDetectionTemplate
        SpamDetectionTemplate updatedSpamDetectionTemplate = spamDetectionTemplateRepository.findById(spamDetectionTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedSpamDetectionTemplate are not directly saved in db
        em.detach(updatedSpamDetectionTemplate);
        updatedSpamDetectionTemplate
            .numberOfComments(UPDATED_NUMBER_OF_COMMENTS)
            .rating(UPDATED_RATING)
            .reason(UPDATED_REASON);
        SpamDetectionTemplateDTO spamDetectionTemplateDTO = spamDetectionTemplateMapper.toDto(updatedSpamDetectionTemplate);

        restSpamDetectionTemplateMockMvc.perform(put("/api/spam-detection-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spamDetectionTemplateDTO)))
            .andExpect(status().isOk());

        // Validate the SpamDetectionTemplate in the database
        List<SpamDetectionTemplate> spamDetectionTemplateList = spamDetectionTemplateRepository.findAll();
        assertThat(spamDetectionTemplateList).hasSize(databaseSizeBeforeUpdate);
        SpamDetectionTemplate testSpamDetectionTemplate = spamDetectionTemplateList.get(spamDetectionTemplateList.size() - 1);
        assertThat(testSpamDetectionTemplate.getNumberOfComments()).isEqualTo(UPDATED_NUMBER_OF_COMMENTS);
        assertThat(testSpamDetectionTemplate.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testSpamDetectionTemplate.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingSpamDetectionTemplate() throws Exception {
        int databaseSizeBeforeUpdate = spamDetectionTemplateRepository.findAll().size();

        // Create the SpamDetectionTemplate
        SpamDetectionTemplateDTO spamDetectionTemplateDTO = spamDetectionTemplateMapper.toDto(spamDetectionTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpamDetectionTemplateMockMvc.perform(put("/api/spam-detection-templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spamDetectionTemplateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpamDetectionTemplate in the database
        List<SpamDetectionTemplate> spamDetectionTemplateList = spamDetectionTemplateRepository.findAll();
        assertThat(spamDetectionTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpamDetectionTemplate() throws Exception {
        // Initialize the database
        spamDetectionTemplateRepository.saveAndFlush(spamDetectionTemplate);

        int databaseSizeBeforeDelete = spamDetectionTemplateRepository.findAll().size();

        // Delete the spamDetectionTemplate
        restSpamDetectionTemplateMockMvc.perform(delete("/api/spam-detection-templates/{id}", spamDetectionTemplate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpamDetectionTemplate> spamDetectionTemplateList = spamDetectionTemplateRepository.findAll();
        assertThat(spamDetectionTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
