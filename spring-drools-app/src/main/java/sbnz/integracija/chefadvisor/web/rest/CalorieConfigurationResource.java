package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.CalorieConfigurationService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.CalorieConfigurationDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.CalorieConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class CalorieConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(CalorieConfigurationResource.class);

    private static final String ENTITY_NAME = "calorieConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalorieConfigurationService calorieConfigurationService;

    public CalorieConfigurationResource(CalorieConfigurationService calorieConfigurationService) {
        this.calorieConfigurationService = calorieConfigurationService;
    }

    /**
     * {@code POST  /calorie-configurations} : Create a new calorieConfiguration.
     *
     * @param calorieConfigurationDTO the calorieConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calorieConfigurationDTO, or with status {@code 400 (Bad Request)} if the calorieConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calorie-configurations")
    public ResponseEntity<CalorieConfigurationDTO> createCalorieConfiguration(@RequestBody CalorieConfigurationDTO calorieConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save CalorieConfiguration : {}", calorieConfigurationDTO);
        if (calorieConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new calorieConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalorieConfigurationDTO result = calorieConfigurationService.save(calorieConfigurationDTO);
        return ResponseEntity.created(new URI("/api/calorie-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calorie-configurations} : Updates an existing calorieConfiguration.
     *
     * @param calorieConfigurationDTO the calorieConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calorieConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the calorieConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calorieConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calorie-configurations")
    public ResponseEntity<CalorieConfigurationDTO> updateCalorieConfiguration(@RequestBody CalorieConfigurationDTO calorieConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update CalorieConfiguration : {}", calorieConfigurationDTO);
        if (calorieConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalorieConfigurationDTO result = calorieConfigurationService.save(calorieConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calorieConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /calorie-configurations} : get all the calorieConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calorieConfigurations in body.
     */
    @GetMapping("/calorie-configurations")
    public List<CalorieConfigurationDTO> getAllCalorieConfigurations() {
        log.debug("REST request to get all CalorieConfigurations");
        return calorieConfigurationService.findAll();
    }

    /**
     * {@code GET  /calorie-configurations/:id} : get the "id" calorieConfiguration.
     *
     * @param id the id of the calorieConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calorieConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calorie-configurations/{id}")
    public ResponseEntity<CalorieConfigurationDTO> getCalorieConfiguration(@PathVariable Long id) {
        log.debug("REST request to get CalorieConfiguration : {}", id);
        Optional<CalorieConfigurationDTO> calorieConfigurationDTO = calorieConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calorieConfigurationDTO);
    }

    /**
     * {@code DELETE  /calorie-configurations/:id} : delete the "id" calorieConfiguration.
     *
     * @param id the id of the calorieConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calorie-configurations/{id}")
    public ResponseEntity<Void> deleteCalorieConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete CalorieConfiguration : {}", id);
        calorieConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
