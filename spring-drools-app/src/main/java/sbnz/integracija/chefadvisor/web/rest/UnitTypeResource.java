package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.UnitTypeService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.UnitTypeDTO;

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
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.UnitType}.
 */
@RestController
@RequestMapping("/api")
public class UnitTypeResource {

    private final Logger log = LoggerFactory.getLogger(UnitTypeResource.class);

    private static final String ENTITY_NAME = "unitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitTypeService unitTypeService;

    public UnitTypeResource(UnitTypeService unitTypeService) {
        this.unitTypeService = unitTypeService;
    }

    /**
     * {@code POST  /unit-types} : Create a new unitType.
     *
     * @param unitTypeDTO the unitTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitTypeDTO, or with status {@code 400 (Bad Request)} if the unitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unit-types")
    public ResponseEntity<UnitTypeDTO> createUnitType(@RequestBody UnitTypeDTO unitTypeDTO) throws URISyntaxException {
        log.debug("REST request to save UnitType : {}", unitTypeDTO);
        if (unitTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new unitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitTypeDTO result = unitTypeService.save(unitTypeDTO);
        return ResponseEntity.created(new URI("/api/unit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unit-types} : Updates an existing unitType.
     *
     * @param unitTypeDTO the unitTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitTypeDTO,
     * or with status {@code 400 (Bad Request)} if the unitTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unit-types")
    public ResponseEntity<UnitTypeDTO> updateUnitType(@RequestBody UnitTypeDTO unitTypeDTO) throws URISyntaxException {
        log.debug("REST request to update UnitType : {}", unitTypeDTO);
        if (unitTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitTypeDTO result = unitTypeService.save(unitTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unitTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unit-types} : get all the unitTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unitTypes in body.
     */
    @GetMapping("/unit-types")
    public ResponseEntity<List<UnitTypeDTO>> getAllUnitTypes(Pageable pageable) {
        log.debug("REST request to get a page of UnitTypes");
        Page<UnitTypeDTO> page = unitTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unit-types/:id} : get the "id" unitType.
     *
     * @param id the id of the unitTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unit-types/{id}")
    public ResponseEntity<UnitTypeDTO> getUnitType(@PathVariable Long id) {
        log.debug("REST request to get UnitType : {}", id);
        Optional<UnitTypeDTO> unitTypeDTO = unitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitTypeDTO);
    }

    /**
     * {@code DELETE  /unit-types/:id} : delete the "id" unitType.
     *
     * @param id the id of the unitTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unit-types/{id}")
    public ResponseEntity<Void> deleteUnitType(@PathVariable Long id) {
        log.debug("REST request to delete UnitType : {}", id);
        unitTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
