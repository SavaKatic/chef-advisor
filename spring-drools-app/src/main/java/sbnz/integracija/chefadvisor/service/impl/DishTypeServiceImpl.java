package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.DishTypeService;
import sbnz.integracija.chefadvisor.domain.DishType;
import sbnz.integracija.chefadvisor.repository.DishTypeRepository;
import sbnz.integracija.chefadvisor.service.dto.DishTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DishType}.
 */
@Service
@Transactional
public class DishTypeServiceImpl implements DishTypeService {

    private final Logger log = LoggerFactory.getLogger(DishTypeServiceImpl.class);

    private final DishTypeRepository dishTypeRepository;

    private final DishTypeMapper dishTypeMapper;

    public DishTypeServiceImpl(DishTypeRepository dishTypeRepository, DishTypeMapper dishTypeMapper) {
        this.dishTypeRepository = dishTypeRepository;
        this.dishTypeMapper = dishTypeMapper;
    }

    /**
     * Save a dishType.
     *
     * @param dishTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DishTypeDTO save(DishTypeDTO dishTypeDTO) {
        log.debug("Request to save DishType : {}", dishTypeDTO);
        DishType dishType = dishTypeMapper.toEntity(dishTypeDTO);
        dishType = dishTypeRepository.save(dishType);
        return dishTypeMapper.toDto(dishType);
    }

    /**
     * Get all the dishTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DishTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DishTypes");
        return dishTypeRepository.findAll(pageable)
            .map(dishTypeMapper::toDto);
    }

    /**
     * Get one dishType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DishTypeDTO> findOne(Long id) {
        log.debug("Request to get DishType : {}", id);
        return dishTypeRepository.findById(id)
            .map(dishTypeMapper::toDto);
    }

    /**
     * Delete the dishType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DishType : {}", id);
        dishTypeRepository.deleteById(id);
    }
}
