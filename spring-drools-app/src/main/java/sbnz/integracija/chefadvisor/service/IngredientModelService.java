package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.IngredientModelDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.IngredientModel}.
 */
public interface IngredientModelService {

    /**
     * Save a ingredientModel.
     *
     * @param ingredientModelDTO the entity to save.
     * @return the persisted entity.
     */
    IngredientModelDTO save(IngredientModelDTO ingredientModelDTO);

    /**
     * Get all the ingredientModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IngredientModelDTO> findAll(Pageable pageable);

    /**
     * Get all the ingredientModels with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<IngredientModelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ingredientModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngredientModelDTO> findOne(Long id);

    /**
     * Delete the "id" ingredientModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
