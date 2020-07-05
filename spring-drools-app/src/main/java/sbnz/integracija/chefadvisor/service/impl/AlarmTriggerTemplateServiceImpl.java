package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.AlarmTriggerTemplateService;
import sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate;
import sbnz.integracija.chefadvisor.repository.AlarmTriggerTemplateRepository;
import sbnz.integracija.chefadvisor.service.dto.AlarmTriggerTemplateDTO;
import sbnz.integracija.chefadvisor.service.mapper.AlarmTriggerTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AlarmTriggerTemplate}.
 */
@Service
@Transactional
public class AlarmTriggerTemplateServiceImpl implements AlarmTriggerTemplateService {

    private final Logger log = LoggerFactory.getLogger(AlarmTriggerTemplateServiceImpl.class);

    private final AlarmTriggerTemplateRepository alarmTriggerTemplateRepository;

    private final AlarmTriggerTemplateMapper alarmTriggerTemplateMapper;

    public AlarmTriggerTemplateServiceImpl(AlarmTriggerTemplateRepository alarmTriggerTemplateRepository, AlarmTriggerTemplateMapper alarmTriggerTemplateMapper) {
        this.alarmTriggerTemplateRepository = alarmTriggerTemplateRepository;
        this.alarmTriggerTemplateMapper = alarmTriggerTemplateMapper;
    }

    /**
     * Save a alarmTriggerTemplate.
     *
     * @param alarmTriggerTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AlarmTriggerTemplateDTO save(AlarmTriggerTemplateDTO alarmTriggerTemplateDTO) {
        log.debug("Request to save AlarmTriggerTemplate : {}", alarmTriggerTemplateDTO);
        AlarmTriggerTemplate alarmTriggerTemplate = alarmTriggerTemplateMapper.toEntity(alarmTriggerTemplateDTO);
        alarmTriggerTemplate = alarmTriggerTemplateRepository.save(alarmTriggerTemplate);
        return alarmTriggerTemplateMapper.toDto(alarmTriggerTemplate);
    }

    /**
     * Get all the alarmTriggerTemplates.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlarmTriggerTemplateDTO> findAll() {
        log.debug("Request to get all AlarmTriggerTemplates");
        return alarmTriggerTemplateRepository.findAll().stream()
            .map(alarmTriggerTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one alarmTriggerTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlarmTriggerTemplateDTO> findOne(Long id) {
        log.debug("Request to get AlarmTriggerTemplate : {}", id);
        return alarmTriggerTemplateRepository.findById(id)
            .map(alarmTriggerTemplateMapper::toDto);
    }

    /**
     * Delete the alarmTriggerTemplate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlarmTriggerTemplate : {}", id);
        alarmTriggerTemplateRepository.deleteById(id);
    }
}
