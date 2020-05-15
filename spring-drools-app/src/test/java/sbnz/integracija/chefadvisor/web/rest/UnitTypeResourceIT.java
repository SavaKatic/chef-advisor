package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.UnitType;
import sbnz.integracija.chefadvisor.repository.UnitTypeRepository;
import sbnz.integracija.chefadvisor.service.UnitTypeService;
import sbnz.integracija.chefadvisor.service.dto.UnitTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.UnitTypeMapper;

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
 * Integration tests for the {@link UnitTypeResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UnitTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private UnitTypeRepository unitTypeRepository;

    @Autowired
    private UnitTypeMapper unitTypeMapper;

    @Autowired
    private UnitTypeService unitTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitTypeMockMvc;

    private UnitType unitType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitType createEntity(EntityManager em) {
        UnitType unitType = new UnitType()
            .name(DEFAULT_NAME);
        return unitType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitType createUpdatedEntity(EntityManager em) {
        UnitType unitType = new UnitType()
            .name(UPDATED_NAME);
        return unitType;
    }

    @BeforeEach
    public void initTest() {
        unitType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitType() throws Exception {
        int databaseSizeBeforeCreate = unitTypeRepository.findAll().size();

        // Create the UnitType
        UnitTypeDTO unitTypeDTO = unitTypeMapper.toDto(unitType);
        restUnitTypeMockMvc.perform(post("/api/unit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UnitType testUnitType = unitTypeList.get(unitTypeList.size() - 1);
        assertThat(testUnitType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createUnitTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitTypeRepository.findAll().size();

        // Create the UnitType with an existing ID
        unitType.setId(1L);
        UnitTypeDTO unitTypeDTO = unitTypeMapper.toDto(unitType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitTypeMockMvc.perform(post("/api/unit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUnitTypes() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        // Get all the unitTypeList
        restUnitTypeMockMvc.perform(get("/api/unit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        // Get the unitType
        restUnitTypeMockMvc.perform(get("/api/unit-types/{id}", unitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unitType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingUnitType() throws Exception {
        // Get the unitType
        restUnitTypeMockMvc.perform(get("/api/unit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        int databaseSizeBeforeUpdate = unitTypeRepository.findAll().size();

        // Update the unitType
        UnitType updatedUnitType = unitTypeRepository.findById(unitType.getId()).get();
        // Disconnect from session so that the updates on updatedUnitType are not directly saved in db
        em.detach(updatedUnitType);
        updatedUnitType
            .name(UPDATED_NAME);
        UnitTypeDTO unitTypeDTO = unitTypeMapper.toDto(updatedUnitType);

        restUnitTypeMockMvc.perform(put("/api/unit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeDTO)))
            .andExpect(status().isOk());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeUpdate);
        UnitType testUnitType = unitTypeList.get(unitTypeList.size() - 1);
        assertThat(testUnitType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitType() throws Exception {
        int databaseSizeBeforeUpdate = unitTypeRepository.findAll().size();

        // Create the UnitType
        UnitTypeDTO unitTypeDTO = unitTypeMapper.toDto(unitType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitTypeMockMvc.perform(put("/api/unit-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        int databaseSizeBeforeDelete = unitTypeRepository.findAll().size();

        // Delete the unitType
        restUnitTypeMockMvc.perform(delete("/api/unit-types/{id}", unitType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
