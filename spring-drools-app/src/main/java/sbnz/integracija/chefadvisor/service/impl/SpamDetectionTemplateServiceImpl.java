package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.SpamDetectionTemplateService;
import sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate;
import sbnz.integracija.chefadvisor.repository.SpamDetectionTemplateRepository;
import sbnz.integracija.chefadvisor.service.dto.SpamDetectionTemplateDTO;
import sbnz.integracija.chefadvisor.service.mapper.SpamDetectionTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SpamDetectionTemplate}.
 */
@Service
@Transactional
public class SpamDetectionTemplateServiceImpl implements SpamDetectionTemplateService {

    private final Logger log = LoggerFactory.getLogger(SpamDetectionTemplateServiceImpl.class);

    private final SpamDetectionTemplateRepository spamDetectionTemplateRepository;

    private final SpamDetectionTemplateMapper spamDetectionTemplateMapper;

    public SpamDetectionTemplateServiceImpl(SpamDetectionTemplateRepository spamDetectionTemplateRepository, SpamDetectionTemplateMapper spamDetectionTemplateMapper) {
        this.spamDetectionTemplateRepository = spamDetectionTemplateRepository;
        this.spamDetectionTemplateMapper = spamDetectionTemplateMapper;
    }

    /**
     * Save a spamDetectionTemplate.
     *
     * @param spamDetectionTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpamDetectionTemplateDTO save(SpamDetectionTemplateDTO spamDetectionTemplateDTO) {
        log.debug("Request to save SpamDetectionTemplate : {}", spamDetectionTemplateDTO);
        SpamDetectionTemplate spamDetectionTemplate = spamDetectionTemplateMapper.toEntity(spamDetectionTemplateDTO);
        spamDetectionTemplate = spamDetectionTemplateRepository.save(spamDetectionTemplate);
        return spamDetectionTemplateMapper.toDto(spamDetectionTemplate);
    }

    /**
     * Get all the spamDetectionTemplates.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpamDetectionTemplateDTO> findAll() {
        log.debug("Request to get all SpamDetectionTemplates");
        return spamDetectionTemplateRepository.findAll().stream()
            .map(spamDetectionTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one spamDetectionTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpamDetectionTemplateDTO> findOne(Long id) {
        log.debug("Request to get SpamDetectionTemplate : {}", id);
        return spamDetectionTemplateRepository.findById(id)
            .map(spamDetectionTemplateMapper::toDto);
    }

    /**
     * Delete the spamDetectionTemplate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpamDetectionTemplate : {}", id);
        spamDetectionTemplateRepository.deleteById(id);
    }
}
