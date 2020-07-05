package sbnz.integracija.chefadvisor.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import sbnz.integracija.chefadvisor.domain.User;
import sbnz.integracija.chefadvisor.repository.UserRepository;
import sbnz.integracija.chefadvisor.service.IngredientService;
import sbnz.integracija.chefadvisor.service.dto.IngredientDTO;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.Ingredient}.
 */
@RestController
@RequestMapping("/api")
public class IngredientResource {

    private final Logger log = LoggerFactory.getLogger(IngredientResource.class);

    private static final String ENTITY_NAME = "ingredient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientService ingredientService;
    
    private final UserRepository userRepository;

    public IngredientResource(IngredientService ingredientService, UserRepository userRepository) {
        this.ingredientService = ingredientService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /ingredients} : Create a new ingredient.
     *
     * @param ingredientDTO the ingredientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientDTO, or with status {@code 400 (Bad Request)} if the ingredient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredients")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO, @AuthenticationPrincipal UserDetails loggedUser, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Ingredient : {}", ingredientDTO);
        if (request.isUserInRole("ROLE_USER")) {
        	User user = userRepository.findOneByLogin(loggedUser.getUsername()).orElse(null);
        	ingredientDTO.setUserId(user.getId());
        	ingredientDTO.setUserLogin(user.getLogin());
        }

        if (ingredientDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientDTO result = ingredientService.save(ingredientDTO);
        return ResponseEntity.created(new URI("/api/ingredients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredients} : Updates an existing ingredient.
     *
     * @param ingredientDTO the ingredientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientDTO,
     * or with status {@code 400 (Bad Request)} if the ingredientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredients")
    public ResponseEntity<IngredientDTO> updateIngredient(@RequestBody IngredientDTO ingredientDTO) throws URISyntaxException {
        log.debug("REST request to update Ingredient : {}", ingredientDTO);
        if (ingredientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientDTO result = ingredientService.save(ingredientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientDTO.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/ingredients/dish/{id}")
    public ResponseEntity<List<IngredientDTO>> getDishIngredients(Pageable pageable, @PathVariable Long id) {
    	Page<IngredientDTO> page = ingredientService.findByDish(pageable, id);
    	HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredients} : get all the ingredients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredients in body.
     */
    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDTO>> getIngredients(Pageable pageable, HttpServletRequest request) {
        log.debug("REST request to get a page of Ingredients");
        Page<IngredientDTO> page = null;
        if (request.isUserInRole("ROLE_ADMIN")) {
        	page = ingredientService.findAll(pageable);
        }
        else {
        	System.out.println(request.isUserInRole("ROLE_USER"));
        	page = ingredientService.findByUserIsCurrentUser(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /ingredients} : get all the ingredients.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredients in body.
     */
    @GetMapping("/fridge")
    public ResponseEntity<List<IngredientDTO>> getUserFridge(Pageable pageable) {
        log.debug("REST request to get a page of User Ingredients");
        Page<IngredientDTO> page = ingredientService.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredients/:id} : get the "id" ingredient.
     *
     * @param id the id of the ingredientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable Long id) {
        log.debug("REST request to get Ingredient : {}", id);
        Optional<IngredientDTO> ingredientDTO = ingredientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientDTO);
    }

    /**
     * {@code DELETE  /ingredients/:id} : delete the "id" ingredient.
     *
     * @param id the id of the ingredientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        log.debug("REST request to delete Ingredient : {}", id);
        ingredientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
