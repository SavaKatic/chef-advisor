package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.DishTypeService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.DishTypeDTO;

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
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.DishType}.
 */
@RestController
@RequestMapping("/api")
public class DishTypeResource {

    private final Logger log = LoggerFactory.getLogger(DishTypeResource.class);

    private static final String ENTITY_NAME = "dishType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DishTypeService dishTypeService;

    public DishTypeResource(DishTypeService dishTypeService) {
        this.dishTypeService = dishTypeService;
    }

    /**
     * {@code POST  /dish-types} : Create a new dishType.
     *
     * @param dishTypeDTO the dishTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dishTypeDTO, or with status {@code 400 (Bad Request)} if the dishType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dish-types")
    public ResponseEntity<DishTypeDTO> createDishType(@RequestBody DishTypeDTO dishTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DishType : {}", dishTypeDTO);
        if (dishTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dishType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DishTypeDTO result = dishTypeService.save(dishTypeDTO);
        return ResponseEntity.created(new URI("/api/dish-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dish-types} : Updates an existing dishType.
     *
     * @param dishTypeDTO the dishTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dishTypeDTO,
     * or with status {@code 400 (Bad Request)} if the dishTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dishTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dish-types")
    public ResponseEntity<DishTypeDTO> updateDishType(@RequestBody DishTypeDTO dishTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DishType : {}", dishTypeDTO);
        if (dishTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DishTypeDTO result = dishTypeService.save(dishTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dishTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dish-types} : get all the dishTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dishTypes in body.
     */
    @GetMapping("/dish-types")
    public ResponseEntity<List<DishTypeDTO>> getAllDishTypes(Pageable pageable) {
        log.debug("REST request to get a page of DishTypes");
        Page<DishTypeDTO> page = dishTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dish-types/:id} : get the "id" dishType.
     *
     * @param id the id of the dishTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dishTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dish-types/{id}")
    public ResponseEntity<DishTypeDTO> getDishType(@PathVariable Long id) {
        log.debug("REST request to get DishType : {}", id);
        Optional<DishTypeDTO> dishTypeDTO = dishTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dishTypeDTO);
    }

    /**
     * {@code DELETE  /dish-types/:id} : delete the "id" dishType.
     *
     * @param id the id of the dishTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dish-types/{id}")
    public ResponseEntity<Void> deleteDishType(@PathVariable Long id) {
        log.debug("REST request to delete DishType : {}", id);
        dishTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
