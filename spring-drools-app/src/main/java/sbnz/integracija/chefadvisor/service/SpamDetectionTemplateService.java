package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.SpamDetectionTemplateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate}.
 */
public interface SpamDetectionTemplateService {

    /**
     * Save a spamDetectionTemplate.
     *
     * @param spamDetectionTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    SpamDetectionTemplateDTO save(SpamDetectionTemplateDTO spamDetectionTemplateDTO);

    /**
     * Get all the spamDetectionTemplates.
     *
     * @return the list of entities.
     */
    List<SpamDetectionTemplateDTO> findAll();

    /**
     * Get the "id" spamDetectionTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpamDetectionTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" spamDetectionTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
