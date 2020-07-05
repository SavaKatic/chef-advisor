package sbnz.integracija.chefadvisor.web.rest;

import sbnz.integracija.chefadvisor.service.SpamDetectionTemplateService;
import sbnz.integracija.chefadvisor.web.rest.errors.BadRequestAlertException;
import sbnz.integracija.chefadvisor.service.dto.SpamDetectionTemplateDTO;

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
 * REST controller for managing {@link sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate}.
 */
@RestController
@RequestMapping("/api")
public class SpamDetectionTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SpamDetectionTemplateResource.class);

    private static final String ENTITY_NAME = "spamDetectionTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpamDetectionTemplateService spamDetectionTemplateService;

    public SpamDetectionTemplateResource(SpamDetectionTemplateService spamDetectionTemplateService) {
        this.spamDetectionTemplateService = spamDetectionTemplateService;
    }

    /**
     * {@code POST  /spam-detection-templates} : Create a new spamDetectionTemplate.
     *
     * @param spamDetectionTemplateDTO the spamDetectionTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spamDetectionTemplateDTO, or with status {@code 400 (Bad Request)} if the spamDetectionTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spam-detection-templates")
    public ResponseEntity<SpamDetectionTemplateDTO> createSpamDetectionTemplate(@RequestBody SpamDetectionTemplateDTO spamDetectionTemplateDTO) throws URISyntaxException {
        log.debug("REST request to save SpamDetectionTemplate : {}", spamDetectionTemplateDTO);
        if (spamDetectionTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new spamDetectionTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpamDetectionTemplateDTO result = spamDetectionTemplateService.save(spamDetectionTemplateDTO);
        return ResponseEntity.created(new URI("/api/spam-detection-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spam-detection-templates} : Updates an existing spamDetectionTemplate.
     *
     * @param spamDetectionTemplateDTO the spamDetectionTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spamDetectionTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the spamDetectionTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spamDetectionTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spam-detection-templates")
    public ResponseEntity<SpamDetectionTemplateDTO> updateSpamDetectionTemplate(@RequestBody SpamDetectionTemplateDTO spamDetectionTemplateDTO) throws URISyntaxException {
        log.debug("REST request to update SpamDetectionTemplate : {}", spamDetectionTemplateDTO);
        if (spamDetectionTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpamDetectionTemplateDTO result = spamDetectionTemplateService.save(spamDetectionTemplateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, spamDetectionTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /spam-detection-templates} : get all the spamDetectionTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spamDetectionTemplates in body.
     */
    @GetMapping("/spam-detection-templates")
    public List<SpamDetectionTemplateDTO> getAllSpamDetectionTemplates() {
        log.debug("REST request to get all SpamDetectionTemplates");
        return spamDetectionTemplateService.findAll();
    }

    /**
     * {@code GET  /spam-detection-templates/:id} : get the "id" spamDetectionTemplate.
     *
     * @param id the id of the spamDetectionTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spamDetectionTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spam-detection-templates/{id}")
    public ResponseEntity<SpamDetectionTemplateDTO> getSpamDetectionTemplate(@PathVariable Long id) {
        log.debug("REST request to get SpamDetectionTemplate : {}", id);
        Optional<SpamDetectionTemplateDTO> spamDetectionTemplateDTO = spamDetectionTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spamDetectionTemplateDTO);
    }

    /**
     * {@code DELETE  /spam-detection-templates/:id} : delete the "id" spamDetectionTemplate.
     *
     * @param id the id of the spamDetectionTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spam-detection-templates/{id}")
    public ResponseEntity<Void> deleteSpamDetectionTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SpamDetectionTemplate : {}", id);
        spamDetectionTemplateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
