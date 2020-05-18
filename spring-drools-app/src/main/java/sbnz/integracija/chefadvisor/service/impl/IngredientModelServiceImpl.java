package sbnz.integracija.chefadvisor.service.impl;

import sbnz.integracija.chefadvisor.service.IngredientModelService;
import sbnz.integracija.chefadvisor.domain.IngredientModel;
import sbnz.integracija.chefadvisor.repository.IngredientModelRepository;
import sbnz.integracija.chefadvisor.service.dto.IngredientModelDTO;
import sbnz.integracija.chefadvisor.service.mapper.IngredientModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IngredientModel}.
 */
@Service
@Transactional
public class IngredientModelServiceImpl implements IngredientModelService {

    private final Logger log = LoggerFactory.getLogger(IngredientModelServiceImpl.class);

    private final IngredientModelRepository ingredientModelRepository;

    private final IngredientModelMapper ingredientModelMapper;

    public IngredientModelServiceImpl(IngredientModelRepository ingredientModelRepository, IngredientModelMapper ingredientModelMapper) {
        this.ingredientModelRepository = ingredientModelRepository;
        this.ingredientModelMapper = ingredientModelMapper;
    }

    /**
     * Save a ingredientModel.
     *
     * @param ingredientModelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IngredientModelDTO save(IngredientModelDTO ingredientModelDTO) {
        log.debug("Request to save IngredientModel : {}", ingredientModelDTO);
        IngredientModel ingredientModel = ingredientModelMapper.toEntity(ingredientModelDTO);
        ingredientModel = ingredientModelRepository.save(ingredientModel);
        return ingredientModelMapper.toDto(ingredientModel);
    }

    /**
     * Get all the ingredientModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IngredientModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IngredientModels");
        return ingredientModelRepository.findAll(pageable)
            .map(ingredientModelMapper::toDto);
    }

    /**
     * Get all the ingredientModels with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<IngredientModelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ingredientModelRepository.findAllWithEagerRelationships(pageable).map(ingredientModelMapper::toDto);
    }

    /**
     * Get one ingredientModel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IngredientModelDTO> findOne(Long id) {
        log.debug("Request to get IngredientModel : {}", id);
        return ingredientModelRepository.findOneWithEagerRelationships(id)
            .map(ingredientModelMapper::toDto);
    }

    /**
     * Delete the ingredientModel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IngredientModel : {}", id);
        ingredientModelRepository.deleteById(id);
    }
}
