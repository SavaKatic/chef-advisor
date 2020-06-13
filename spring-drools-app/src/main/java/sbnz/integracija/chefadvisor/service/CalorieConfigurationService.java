package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.repository.CalorieConfigurationRepository;
import sbnz.integracija.chefadvisor.service.dto.CalorieConfigurationDTO;
import sbnz.integracija.chefadvisor.service.mapper.CalorieConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CalorieConfiguration}.
 */
@Service
@Transactional
public class CalorieConfigurationService {

    private final Logger log = LoggerFactory.getLogger(CalorieConfigurationService.class);

    private final CalorieConfigurationRepository calorieConfigurationRepository;

    private final CalorieConfigurationMapper calorieConfigurationMapper;

    public CalorieConfigurationService(CalorieConfigurationRepository calorieConfigurationRepository, CalorieConfigurationMapper calorieConfigurationMapper) {
        this.calorieConfigurationRepository = calorieConfigurationRepository;
        this.calorieConfigurationMapper = calorieConfigurationMapper;
    }

    /**
     * Save a calorieConfiguration.
     *
     * @param calorieConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public CalorieConfigurationDTO save(CalorieConfigurationDTO calorieConfigurationDTO) {
        log.debug("Request to save CalorieConfiguration : {}", calorieConfigurationDTO);
        CalorieConfiguration calorieConfiguration = calorieConfigurationMapper.toEntity(calorieConfigurationDTO);
        calorieConfiguration = calorieConfigurationRepository.save(calorieConfiguration);
        return calorieConfigurationMapper.toDto(calorieConfiguration);
    }

    /**
     * Get all the calorieConfigurations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CalorieConfigurationDTO> findAll() {
        log.debug("Request to get all CalorieConfigurations");
        return calorieConfigurationRepository.findAll().stream()
            .map(calorieConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one calorieConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CalorieConfigurationDTO> findOne(Long id) {
        log.debug("Request to get CalorieConfiguration : {}", id);
        return calorieConfigurationRepository.findById(id)
            .map(calorieConfigurationMapper::toDto);
    }

    /**
     * Delete the calorieConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CalorieConfiguration : {}", id);
        calorieConfigurationRepository.deleteById(id);
    }
    
    public CalorieConfiguration findByUserIsCurrentUser() {
    	return this.calorieConfigurationRepository.findByUserIsCurrentUser().get(0);
    }
    
}
