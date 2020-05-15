package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.DishType;
import sbnz.integracija.chefadvisor.repository.DishTypeRepository;
import sbnz.integracija.chefadvisor.service.DishTypeService;
import sbnz.integracija.chefadvisor.service.dto.DishTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishTypeMapper;

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
 * Integration tests for the {@link DishTypeResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DishTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DishTypeRepository dishTypeRepository;

    @Autowired
    private DishTypeMapper dishTypeMapper;

    @Autowired
    private DishTypeService dishTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDishTypeMockMvc;

    private DishType dishType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DishType createEntity(EntityManager em) {
        DishType dishType = new DishType()
            .name(DEFAULT_NAME);
        return dishType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DishType createUpdatedEntity(EntityManager em) {
        DishType dishType = new DishType()
            .name(UPDATED_NAME);
        return dishType;
    }

    @BeforeEach
    public void initTest() {
        dishType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDishType() throws Exception {
        int databaseSizeBeforeCreate = dishTypeRepository.findAll().size();

        // Create the DishType
        DishTypeDTO dishTypeDTO = dishTypeMapper.toDto(dishType);
        restDishTypeMockMvc.perform(post("/api/dish-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DishType in the database
        List<DishType> dishTypeList = dishTypeRepository.findAll();
        assertThat(dishTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DishType testDishType = dishTypeList.get(dishTypeList.size() - 1);
        assertThat(testDishType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDishTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishTypeRepository.findAll().size();

        // Create the DishType with an existing ID
        dishType.setId(1L);
        DishTypeDTO dishTypeDTO = dishTypeMapper.toDto(dishType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishTypeMockMvc.perform(post("/api/dish-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DishType in the database
        List<DishType> dishTypeList = dishTypeRepository.findAll();
        assertThat(dishTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDishTypes() throws Exception {
        // Initialize the database
        dishTypeRepository.saveAndFlush(dishType);

        // Get all the dishTypeList
        restDishTypeMockMvc.perform(get("/api/dish-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dishType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDishType() throws Exception {
        // Initialize the database
        dishTypeRepository.saveAndFlush(dishType);

        // Get the dishType
        restDishTypeMockMvc.perform(get("/api/dish-types/{id}", dishType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dishType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingDishType() throws Exception {
        // Get the dishType
        restDishTypeMockMvc.perform(get("/api/dish-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDishType() throws Exception {
        // Initialize the database
        dishTypeRepository.saveAndFlush(dishType);

        int databaseSizeBeforeUpdate = dishTypeRepository.findAll().size();

        // Update the dishType
        DishType updatedDishType = dishTypeRepository.findById(dishType.getId()).get();
        // Disconnect from session so that the updates on updatedDishType are not directly saved in db
        em.detach(updatedDishType);
        updatedDishType
            .name(UPDATED_NAME);
        DishTypeDTO dishTypeDTO = dishTypeMapper.toDto(updatedDishType);

        restDishTypeMockMvc.perform(put("/api/dish-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DishType in the database
        List<DishType> dishTypeList = dishTypeRepository.findAll();
        assertThat(dishTypeList).hasSize(databaseSizeBeforeUpdate);
        DishType testDishType = dishTypeList.get(dishTypeList.size() - 1);
        assertThat(testDishType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDishType() throws Exception {
        int databaseSizeBeforeUpdate = dishTypeRepository.findAll().size();

        // Create the DishType
        DishTypeDTO dishTypeDTO = dishTypeMapper.toDto(dishType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishTypeMockMvc.perform(put("/api/dish-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DishType in the database
        List<DishType> dishTypeList = dishTypeRepository.findAll();
        assertThat(dishTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDishType() throws Exception {
        // Initialize the database
        dishTypeRepository.saveAndFlush(dishType);

        int databaseSizeBeforeDelete = dishTypeRepository.findAll().size();

        // Delete the dishType
        restDishTypeMockMvc.perform(delete("/api/dish-types/{id}", dishType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DishType> dishTypeList = dishTypeRepository.findAll();
        assertThat(dishTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
