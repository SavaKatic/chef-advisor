package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.IngredientType;
import sbnz.integracija.chefadvisor.repository.IngredientTypeRepository;
import sbnz.integracija.chefadvisor.service.IngredientTypeService;
import sbnz.integracija.chefadvisor.service.dto.IngredientTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.IngredientTypeMapper;

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
 * Integration tests for the {@link IngredientTypeResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class IngredientTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    private IngredientTypeMapper ingredientTypeMapper;

    @Autowired
    private IngredientTypeService ingredientTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientTypeMockMvc;

    private IngredientType ingredientType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientType createEntity(EntityManager em) {
        IngredientType ingredientType = new IngredientType()
            .name(DEFAULT_NAME);
        return ingredientType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientType createUpdatedEntity(EntityManager em) {
        IngredientType ingredientType = new IngredientType()
            .name(UPDATED_NAME);
        return ingredientType;
    }

    @BeforeEach
    public void initTest() {
        ingredientType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientType() throws Exception {
        int databaseSizeBeforeCreate = ingredientTypeRepository.findAll().size();

        // Create the IngredientType
        IngredientTypeDTO ingredientTypeDTO = ingredientTypeMapper.toDto(ingredientType);
        restIngredientTypeMockMvc.perform(post("/api/ingredient-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createIngredientTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientTypeRepository.findAll().size();

        // Create the IngredientType with an existing ID
        ingredientType.setId(1L);
        IngredientTypeDTO ingredientTypeDTO = ingredientTypeMapper.toDto(ingredientType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientTypeMockMvc.perform(post("/api/ingredient-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIngredientTypes() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        // Get all the ingredientTypeList
        restIngredientTypeMockMvc.perform(get("/api/ingredient-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        // Get the ingredientType
        restIngredientTypeMockMvc.perform(get("/api/ingredient-types/{id}", ingredientType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientType() throws Exception {
        // Get the ingredientType
        restIngredientTypeMockMvc.perform(get("/api/ingredient-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();

        // Update the ingredientType
        IngredientType updatedIngredientType = ingredientTypeRepository.findById(ingredientType.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientType are not directly saved in db
        em.detach(updatedIngredientType);
        updatedIngredientType
            .name(UPDATED_NAME);
        IngredientTypeDTO ingredientTypeDTO = ingredientTypeMapper.toDto(updatedIngredientType);

        restIngredientTypeMockMvc.perform(put("/api/ingredient-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTypeDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
        IngredientType testIngredientType = ingredientTypeList.get(ingredientTypeList.size() - 1);
        assertThat(testIngredientType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientType() throws Exception {
        int databaseSizeBeforeUpdate = ingredientTypeRepository.findAll().size();

        // Create the IngredientType
        IngredientTypeDTO ingredientTypeDTO = ingredientTypeMapper.toDto(ingredientType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientTypeMockMvc.perform(put("/api/ingredient-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientType in the database
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientType() throws Exception {
        // Initialize the database
        ingredientTypeRepository.saveAndFlush(ingredientType);

        int databaseSizeBeforeDelete = ingredientTypeRepository.findAll().size();

        // Delete the ingredientType
        restIngredientTypeMockMvc.perform(delete("/api/ingredient-types/{id}", ingredientType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientType> ingredientTypeList = ingredientTypeRepository.findAll();
        assertThat(ingredientTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
