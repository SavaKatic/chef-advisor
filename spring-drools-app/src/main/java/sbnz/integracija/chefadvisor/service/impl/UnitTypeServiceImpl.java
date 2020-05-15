package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.UnitTypeService;
import sbnz.integracija.chefadvisor.domain.UnitType;
import sbnz.integracija.chefadvisor.repository.UnitTypeRepository;
import sbnz.integracija.chefadvisor.service.dto.UnitTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.UnitTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UnitType}.
 */
@Service
@Transactional
public class UnitTypeServiceImpl implements UnitTypeService {

    private final Logger log = LoggerFactory.getLogger(UnitTypeServiceImpl.class);

    private final UnitTypeRepository unitTypeRepository;

    private final UnitTypeMapper unitTypeMapper;

    public UnitTypeServiceImpl(UnitTypeRepository unitTypeRepository, UnitTypeMapper unitTypeMapper) {
        this.unitTypeRepository = unitTypeRepository;
        this.unitTypeMapper = unitTypeMapper;
    }

    /**
     * Save a unitType.
     *
     * @param unitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnitTypeDTO save(UnitTypeDTO unitTypeDTO) {
        log.debug("Request to save UnitType : {}", unitTypeDTO);
        UnitType unitType = unitTypeMapper.toEntity(unitTypeDTO);
        unitType = unitTypeRepository.save(unitType);
        return unitTypeMapper.toDto(unitType);
    }

    /**
     * Get all the unitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnitTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnitTypes");
        return unitTypeRepository.findAll(pageable)
            .map(unitTypeMapper::toDto);
    }

    /**
     * Get one unitType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitTypeDTO> findOne(Long id) {
        log.debug("Request to get UnitType : {}", id);
        return unitTypeRepository.findById(id)
            .map(unitTypeMapper::toDto);
    }

    /**
     * Delete the unitType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnitType : {}", id);
        unitTypeRepository.deleteById(id);
    }
}
