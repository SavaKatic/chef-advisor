package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.AlarmTriggerTemplateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate}.
 */
public interface AlarmTriggerTemplateService {

    /**
     * Save a alarmTriggerTemplate.
     *
     * @param alarmTriggerTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    AlarmTriggerTemplateDTO save(AlarmTriggerTemplateDTO alarmTriggerTemplateDTO);

    /**
     * Get all the alarmTriggerTemplates.
     *
     * @return the list of entities.
     */
    List<AlarmTriggerTemplateDTO> findAll();

    /**
     * Get the "id" alarmTriggerTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlarmTriggerTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" alarmTriggerTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
