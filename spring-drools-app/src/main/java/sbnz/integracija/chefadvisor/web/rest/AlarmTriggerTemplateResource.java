package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.AlarmTriggerTemplateService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.AlarmTriggerTemplateDTO;

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
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate}.
 */
@RestController
@RequestMapping("/api")
public class AlarmTriggerTemplateResource {

    private final Logger log = LoggerFactory.getLogger(AlarmTriggerTemplateResource.class);

    private static final String ENTITY_NAME = "alarmTriggerTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmTriggerTemplateService alarmTriggerTemplateService;

    public AlarmTriggerTemplateResource(AlarmTriggerTemplateService alarmTriggerTemplateService) {
        this.alarmTriggerTemplateService = alarmTriggerTemplateService;
    }

    /**
     * {@code POST  /alarm-trigger-templates} : Create a new alarmTriggerTemplate.
     *
     * @param alarmTriggerTemplateDTO the alarmTriggerTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmTriggerTemplateDTO, or with status {@code 400 (Bad Request)} if the alarmTriggerTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-trigger-templates")
    public ResponseEntity<AlarmTriggerTemplateDTO> createAlarmTriggerTemplate(@RequestBody AlarmTriggerTemplateDTO alarmTriggerTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save AlarmTriggerTemplate : {}", alarmTriggerTemplateDTO);
        if (alarmTriggerTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new alarmTriggerTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmTriggerTemplateDTO result = alarmTriggerTemplateService.save(alarmTriggerTemplateDTO);
        return ResponseEntity.created(new URI("/api/alarm-trigger-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-trigger-templates} : Updates an existing alarmTriggerTemplate.
     *
     * @param alarmTriggerTemplateDTO the alarmTriggerTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmTriggerTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the alarmTriggerTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmTriggerTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-trigger-templates")
    public ResponseEntity<AlarmTriggerTemplateDTO> updateAlarmTriggerTemplate(@RequestBody AlarmTriggerTemplateDTO alarmTriggerTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update AlarmTriggerTemplate : {}", alarmTriggerTemplateDTO);
        if (alarmTriggerTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmTriggerTemplateDTO result = alarmTriggerTemplateService.save(alarmTriggerTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alarmTriggerTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-trigger-templates} : get all the alarmTriggerTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmTriggerTemplates in body.
     */
    @GetMapping("/alarm-trigger-templates")
    public List<AlarmTriggerTemplateDTO> getAllAlarmTriggerTemplates() {
        log.debug("REST request to get all AlarmTriggerTemplates");
        return alarmTriggerTemplateService.findAll();
    }

    /**
     * {@code GET  /alarm-trigger-templates/:id} : get the "id" alarmTriggerTemplate.
     *
     * @param id the id of the alarmTriggerTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmTriggerTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-trigger-templates/{id}")
    public ResponseEntity<AlarmTriggerTemplateDTO> getAlarmTriggerTemplate(@PathVariable Long id) {
        log.debug("REST request to get AlarmTriggerTemplate : {}", id);
        Optional<AlarmTriggerTemplateDTO> alarmTriggerTemplateDTO = alarmTriggerTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarmTriggerTemplateDTO);
    }

    /**
     * {@code DELETE  /alarm-trigger-templates/:id} : delete the "id" alarmTriggerTemplate.
     *
     * @param id the id of the alarmTriggerTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-trigger-templates/{id}")
    public ResponseEntity<Void> deleteAlarmTriggerTemplate(@PathVariable Long id) {
        log.debug("REST request to delete AlarmTriggerTemplate : {}", id);
        alarmTriggerTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
