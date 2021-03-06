package sbnz.integracija.chefadvisor.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import sbnz.integracija.chefadvisor.domain.User;
import sbnz.integracija.chefadvisor.repository.UserRepository;
import sbnz.integracija.chefadvisor.service.DishService;
import sbnz.integracija.chefadvisor.service.IngredientService;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;
import sbnz.integracija.chefadvisor.service.dto.UserDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishMapper;
import sbnz.integracija.chefadvisor.service.mapper.UserMapper;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.Dish}.
 */
@RestController
@RequestMapping("/api")
public class DishResource {

    private final Logger log = LoggerFactory.getLogger(DishResource.class);

    private static final String ENTITY_NAME = "dish";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DishService dishService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private DishMapper dishMapper;
    
    private final UserRepository userRepository;
    
    private final IngredientService ingredientService;

    public DishResource(DishService dishService, UserRepository userRepository, IngredientService ingredientService) {
        this.dishService = dishService;
        this.userRepository = userRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * {@code POST  /dishes} : Create a new dish.
     *
     * @param dishDTO the dishDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dishDTO, or with status {@code 400 (Bad Request)} if the dish has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dishes")
    public ResponseEntity<DishDTO> createDish(@RequestBody DishDTO dishDTO) throws URISyntaxException {
        log.debug("REST request to save Dish : {}", dishDTO);
        if (dishDTO.getId() != null) {
            throw new BadRequestAlertException("A new dish cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DishDTO result = dishService.save(dishDTO);
        return ResponseEntity.created(new URI("/api/dishes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    
    @PostMapping("/dishes/addToHistory")
    public ResponseEntity<Void> addToHistory(@RequestBody DishDTO dishDTO, @AuthenticationPrincipal UserDetails loggedUser) {
    	User user = userRepository.findOneByLogin(loggedUser.getUsername()).orElse(null);

        UserDTO userDTO = userMapper.userToUserDTO(user);
        dishDTO.users.add(userDTO);
        dishService.save(dishDTO);
        
        ingredientService.adjustFridgeIngredients(dishMapper.toEntity(dishDTO));
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code PUT  /dishes} : Updates an existing dish.
     *
     * @param dishDTO the dishDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dishDTO,
     * or with status {@code 400 (Bad Request)} if the dishDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dishDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dishes")
    public ResponseEntity<DishDTO> updateDish(@RequestBody DishDTO dishDTO) throws URISyntaxException {
        log.debug("REST request to update Dish : {}", dishDTO);
        if (dishDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DishDTO result = dishService.save(dishDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dishDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dishes} : get all the dishes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dishes in body.
     */
    @GetMapping("/dishes")
    public ResponseEntity<List<DishDTO>> getAllDishes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Dishes");
        Page<DishDTO> page;
        if (eagerload) {
            page = dishService.findAllWithEagerRelationships(pageable);
        } else {
            page = dishService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

            /**
     * {@code GET  /dishes} : get all the dishes that user made.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dishes in body.
     */
    @GetMapping("/dishHistory")
    public ResponseEntity<List<DishDTO>> getUserDishHistory(Pageable pageable) {
        log.debug("REST request to get a page of Dishes by current user");
        Page<DishDTO> page = dishService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    


    /**
     * {@code GET  /dishes/:id} : get the "id" dish.
     *
     * @param id the id of the dishDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dishDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dishes/{id}")
    public ResponseEntity<DishDTO> getDish(@PathVariable Long id) {
        log.debug("REST request to get Dish : {}", id);
        Optional<DishDTO> dishDTO = dishService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dishDTO);
    }

    /**
     * {@code DELETE  /dishes/:id} : delete the "id" dish.
     *
     * @param id the id of the dishDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dishes/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        log.debug("REST request to delete Dish : {}", id);
        dishService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
