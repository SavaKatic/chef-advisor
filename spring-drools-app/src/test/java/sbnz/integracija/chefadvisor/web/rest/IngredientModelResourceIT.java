package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.IngredientModel;
import sbnz.integracija.chefadvisor.repository.IngredientModelRepository;
import sbnz.integracija.chefadvisor.service.IngredientModelService;
import sbnz.integracija.chefadvisor.service.dto.IngredientModelDTO;
import sbnz.integracija.chefadvisor.service.mapper.IngredientModelMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IngredientModelResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IngredientModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_CALORIES_PER_UNIT = 1D;
    private static final Double UPDATED_CALORIES_PER_UNIT = 2D;

    @Autowired
    private IngredientModelRepository ingredientModelRepository;

    @Mock
    private IngredientModelRepository ingredientModelRepositoryMock;

    @Autowired
    private IngredientModelMapper ingredientModelMapper;

    @Mock
    private IngredientModelService ingredientModelServiceMock;

    @Autowired
    private IngredientModelService ingredientModelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientModelMockMvc;

    private IngredientModel ingredientModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientModel createEntity(EntityManager em) {
        IngredientModel ingredientModel = new IngredientModel()
            .name(DEFAULT_NAME)
            .caloriesPerUnit(DEFAULT_CALORIES_PER_UNIT);
        return ingredientModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngredientModel createUpdatedEntity(EntityManager em) {
        IngredientModel ingredientModel = new IngredientModel()
            .name(UPDATED_NAME)
            .caloriesPerUnit(UPDATED_CALORIES_PER_UNIT);
        return ingredientModel;
    }

    @BeforeEach
    public void initTest() {
        ingredientModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createIngredientModel() throws Exception {
        int databaseSizeBeforeCreate = ingredientModelRepository.findAll().size();

        // Create the IngredientModel
        IngredientModelDTO ingredientModelDTO = ingredientModelMapper.toDto(ingredientModel);
        restIngredientModelMockMvc.perform(post("/api/ingredient-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientModelDTO)))
            .andExpect(status().isCreated());

        // Validate the IngredientModel in the database
        List<IngredientModel> ingredientModelList = ingredientModelRepository.findAll();
        assertThat(ingredientModelList).hasSize(databaseSizeBeforeCreate + 1);
        IngredientModel testIngredientModel = ingredientModelList.get(ingredientModelList.size() - 1);
        assertThat(testIngredientModel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredientModel.getCaloriesPerUnit()).isEqualTo(DEFAULT_CALORIES_PER_UNIT);
    }

    @Test
    @Transactional
    public void createIngredientModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ingredientModelRepository.findAll().size();

        // Create the IngredientModel with an existing ID
        ingredientModel.setId(1L);
        IngredientModelDTO ingredientModelDTO = ingredientModelMapper.toDto(ingredientModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientModelMockMvc.perform(post("/api/ingredient-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientModel in the database
        List<IngredientModel> ingredientModelList = ingredientModelRepository.findAll();
        assertThat(ingredientModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIngredientModels() throws Exception {
        // Initialize the database
        ingredientModelRepository.saveAndFlush(ingredientModel);

        // Get all the ingredientModelList
        restIngredientModelMockMvc.perform(get("/api/ingredient-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredientModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].caloriesPerUnit").value(hasItem(DEFAULT_CALORIES_PER_UNIT.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllIngredientModelsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ingredientModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIngredientModelMockMvc.perform(get("/api/ingredient-models?eagerload=true"))
            .andExpect(status().isOk());

        verify(ingredientModelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllIngredientModelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ingredientModelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIngredientModelMockMvc.perform(get("/api/ingredient-models?eagerload=true"))
            .andExpect(status().isOk());

        verify(ingredientModelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getIngredientModel() throws Exception {
        // Initialize the database
        ingredientModelRepository.saveAndFlush(ingredientModel);

        // Get the ingredientModel
        restIngredientModelMockMvc.perform(get("/api/ingredient-models/{id}", ingredientModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredientModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.caloriesPerUnit").value(DEFAULT_CALORIES_PER_UNIT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIngredientModel() throws Exception {
        // Get the ingredientModel
        restIngredientModelMockMvc.perform(get("/api/ingredient-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIngredientModel() throws Exception {
        // Initialize the database
        ingredientModelRepository.saveAndFlush(ingredientModel);

        int databaseSizeBeforeUpdate = ingredientModelRepository.findAll().size();

        // Update the ingredientModel
        IngredientModel updatedIngredientModel = ingredientModelRepository.findById(ingredientModel.getId()).get();
        // Disconnect from session so that the updates on updatedIngredientModel are not directly saved in db
        em.detach(updatedIngredientModel);
        updatedIngredientModel
            .name(UPDATED_NAME)
            .caloriesPerUnit(UPDATED_CALORIES_PER_UNIT);
        IngredientModelDTO ingredientModelDTO = ingredientModelMapper.toDto(updatedIngredientModel);

        restIngredientModelMockMvc.perform(put("/api/ingredient-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientModelDTO)))
            .andExpect(status().isOk());

        // Validate the IngredientModel in the database
        List<IngredientModel> ingredientModelList = ingredientModelRepository.findAll();
        assertThat(ingredientModelList).hasSize(databaseSizeBeforeUpdate);
        IngredientModel testIngredientModel = ingredientModelList.get(ingredientModelList.size() - 1);
        assertThat(testIngredientModel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredientModel.getCaloriesPerUnit()).isEqualTo(UPDATED_CALORIES_PER_UNIT);
    }

    @Test
    @Transactional
    public void updateNonExistingIngredientModel() throws Exception {
        int databaseSizeBeforeUpdate = ingredientModelRepository.findAll().size();

        // Create the IngredientModel
        IngredientModelDTO ingredientModelDTO = ingredientModelMapper.toDto(ingredientModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientModelMockMvc.perform(put("/api/ingredient-models")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ingredientModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IngredientModel in the database
        List<IngredientModel> ingredientModelList = ingredientModelRepository.findAll();
        assertThat(ingredientModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIngredientModel() throws Exception {
        // Initialize the database
        ingredientModelRepository.saveAndFlush(ingredientModel);

        int databaseSizeBeforeDelete = ingredientModelRepository.findAll().size();

        // Delete the ingredientModel
        restIngredientModelMockMvc.perform(delete("/api/ingredient-models/{id}", ingredientModel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IngredientModel> ingredientModelList = ingredientModelRepository.findAll();
        assertThat(ingredientModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
