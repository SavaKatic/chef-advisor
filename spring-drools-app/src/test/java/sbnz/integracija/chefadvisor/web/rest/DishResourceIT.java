package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.ChefadvisorApp;
import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.repository.DishRepository;
import sbnz.integracija.chefadvisor.service.DishService;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishMapper;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;
/**
 * Integration tests for the {@link DishResource} REST controller.
 */
@SpringBootTest(classes = ChefadvisorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DishResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DishCategory DEFAULT_CATEGORY = DishCategory.BREAKFAST;
    private static final DishCategory UPDATED_CATEGORY = DishCategory.LUNCH;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_AVERAGE_RATING = 1D;
    private static final Double UPDATED_AVERAGE_RATING = 2D;

    @Autowired
    private DishRepository dishRepository;

    @Mock
    private DishRepository dishRepositoryMock;

    @Autowired
    private DishMapper dishMapper;

    @Mock
    private DishService dishServiceMock;

    @Autowired
    private DishService dishService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDishMockMvc;

    private Dish dish;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dish createEntity(EntityManager em) {
        Dish dish = new Dish()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .averageRating(DEFAULT_AVERAGE_RATING);
        return dish;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dish createUpdatedEntity(EntityManager em) {
        Dish dish = new Dish()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .averageRating(UPDATED_AVERAGE_RATING);
        return dish;
    }

    @BeforeEach
    public void initTest() {
        dish = createEntity(em);
    }

    @Test
    @Transactional
    public void createDish() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish
        DishDTO dishDTO = dishMapper.toDto(dish);
        restDishMockMvc.perform(post("/api/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isCreated());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeCreate + 1);
        Dish testDish = dishList.get(dishList.size() - 1);
        assertThat(testDish.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDish.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testDish.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDish.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testDish.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDish.getAverageRating()).isEqualTo(DEFAULT_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void createDishWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dishRepository.findAll().size();

        // Create the Dish with an existing ID
        dish.setId(1L);
        DishDTO dishDTO = dishMapper.toDto(dish);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDishMockMvc.perform(post("/api/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDishes() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get all the dishList
        restDishMockMvc.perform(get("/api/dishes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dish.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].averageRating").value(hasItem(DEFAULT_AVERAGE_RATING.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDishesWithEagerRelationshipsIsEnabled() throws Exception {
        when(dishServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDishMockMvc.perform(get("/api/dishes?eagerload=true"))
            .andExpect(status().isOk());

        verify(dishServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDishesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dishServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDishMockMvc.perform(get("/api/dishes?eagerload=true"))
            .andExpect(status().isOk());

        verify(dishServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        // Get the dish
        restDishMockMvc.perform(get("/api/dishes/{id}", dish.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dish.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.averageRating").value(DEFAULT_AVERAGE_RATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDish() throws Exception {
        // Get the dish
        restDishMockMvc.perform(get("/api/dishes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Update the dish
        Dish updatedDish = dishRepository.findById(dish.getId()).get();
        // Disconnect from session so that the updates on updatedDish are not directly saved in db
        em.detach(updatedDish);
        updatedDish
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .averageRating(UPDATED_AVERAGE_RATING);
        DishDTO dishDTO = dishMapper.toDto(updatedDish);

        restDishMockMvc.perform(put("/api/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isOk());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeUpdate);
        Dish testDish = dishList.get(dishList.size() - 1);
        assertThat(testDish.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDish.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testDish.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDish.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testDish.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDish.getAverageRating()).isEqualTo(UPDATED_AVERAGE_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingDish() throws Exception {
        int databaseSizeBeforeUpdate = dishRepository.findAll().size();

        // Create the Dish
        DishDTO dishDTO = dishMapper.toDto(dish);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDishMockMvc.perform(put("/api/dishes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dishDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dish in the database
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDish() throws Exception {
        // Initialize the database
        dishRepository.saveAndFlush(dish);

        int databaseSizeBeforeDelete = dishRepository.findAll().size();

        // Delete the dish
        restDishMockMvc.perform(delete("/api/dishes/{id}", dish.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dish> dishList = dishRepository.findAll();
        assertThat(dishList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
