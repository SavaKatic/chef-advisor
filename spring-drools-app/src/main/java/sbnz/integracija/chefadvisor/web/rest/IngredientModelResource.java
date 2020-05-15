package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.IngredientModelService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.IngredientModelDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.IngredientModel}.
 */
@RestController
@RequestMapping("/api")
public class IngredientModelResource {

    private final Logger log = LoggerFactory.getLogger(IngredientModelResource.class);

    private static final String ENTITY_NAME = "ingredientModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredientModelService ingredientModelService;

    public IngredientModelResource(IngredientModelService ingredientModelService) {
        this.ingredientModelService = ingredientModelService;
    }

    /**
     * {@code POST  /ingredient-models} : Create a new ingredientModel.
     *
     * @param ingredientModelDTO the ingredientModelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredientModelDTO, or with status {@code 400 (Bad Request)} if the ingredientModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ingredient-models")
    public ResponseEntity<IngredientModelDTO> createIngredientModel(@RequestBody IngredientModelDTO ingredientModelDTO) throws URISyntaxException {
        log.debug("REST request to save IngredientModel : {}", ingredientModelDTO);
        if (ingredientModelDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredientModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredientModelDTO result = ingredientModelService.save(ingredientModelDTO);
        return ResponseEntity.created(new URI("/api/ingredient-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredient-models} : Updates an existing ingredientModel.
     *
     * @param ingredientModelDTO the ingredientModelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredientModelDTO,
     * or with status {@code 400 (Bad Request)} if the ingredientModelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredientModelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ingredient-models")
    public ResponseEntity<IngredientModelDTO> updateIngredientModel(@RequestBody IngredientModelDTO ingredientModelDTO) throws URISyntaxException {
        log.debug("REST request to update IngredientModel : {}", ingredientModelDTO);
        if (ingredientModelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IngredientModelDTO result = ingredientModelService.save(ingredientModelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ingredientModelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ingredient-models} : get all the ingredientModels.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredientModels in body.
     */
    @GetMapping("/ingredient-models")
    public ResponseEntity<List<IngredientModelDTO>> getAllIngredientModels(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of IngredientModels");
        Page<IngredientModelDTO> page;
        if (eagerload) {
            page = ingredientModelService.findAllWithEagerRelationships(pageable);
        } else {
            page = ingredientModelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredient-models/:id} : get the "id" ingredientModel.
     *
     * @param id the id of the ingredientModelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredientModelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ingredient-models/{id}")
    public ResponseEntity<IngredientModelDTO> getIngredientModel(@PathVariable Long id) {
        log.debug("REST request to get IngredientModel : {}", id);
        Optional<IngredientModelDTO> ingredientModelDTO = ingredientModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredientModelDTO);
    }

    /**
     * {@code DELETE  /ingredient-models/:id} : delete the "id" ingredientModel.
     *
     * @param id the id of the ingredientModelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ingredient-models/{id}")
    public ResponseEntity<Void> deleteIngredientModel(@PathVariable Long id) {
        log.debug("REST request to delete IngredientModel : {}", id);
        ingredientModelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
