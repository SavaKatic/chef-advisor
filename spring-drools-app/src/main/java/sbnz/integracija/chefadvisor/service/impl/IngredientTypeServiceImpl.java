package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.IngredientTypeService;
import sbnz.integracija.chefadvisor.domain.IngredientType;
import sbnz.integracija.chefadvisor.repository.IngredientTypeRepository;
import sbnz.integracija.chefadvisor.service.dto.IngredientTypeDTO;
import sbnz.integracija.chefadvisor.service.mapper.IngredientTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IngredientType}.
 */
@Service
@Transactional
public class IngredientTypeServiceImpl implements IngredientTypeService {

    private final Logger log = LoggerFactory.getLogger(IngredientTypeServiceImpl.class);

    private final IngredientTypeRepository ingredientTypeRepository;

    private final IngredientTypeMapper ingredientTypeMapper;

    public IngredientTypeServiceImpl(IngredientTypeRepository ingredientTypeRepository, IngredientTypeMapper ingredientTypeMapper) {
        this.ingredientTypeRepository = ingredientTypeRepository;
        this.ingredientTypeMapper = ingredientTypeMapper;
    }

    /**
     * Save a ingredientType.
     *
     * @param ingredientTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IngredientTypeDTO save(IngredientTypeDTO ingredientTypeDTO) {
        log.debug("Request to save IngredientType : {}", ingredientTypeDTO);
        IngredientType ingredientType = ingredientTypeMapper.toEntity(ingredientTypeDTO);
        ingredientType = ingredientTypeRepository.save(ingredientType);
        return ingredientTypeMapper.toDto(ingredientType);
    }

    /**
     * Get all the ingredientTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientTypes");
        return ingredientTypeRepository.findAll(pageable)
            .map(ingredientTypeMapper::toDto);
    }

    /**
     * Get one ingredientType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientTypeDTO> findOne(Long id) {
        log.debug("Request to get IngredientType : {}", id);
        return ingredientTypeRepository.findById(id)
            .map(ingredientTypeMapper::toDto);
    }

    /**
     * Delete the ingredientType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientType : {}", id);
        ingredientTypeRepository.deleteById(id);
    }
}
