package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.IngredientTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.IngredientType}.
 */
public interface IngredientTypeService {

    /**
     * Save a ingredientType.
     *
     * @param ingredientTypeDTO the entity to save.
     * @return the persisted entity.
     */
    IngredientTypeDTO save(IngredientTypeDTO ingredientTypeDTO);

    /**
     * Get all the ingredientTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IngredientTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ingredientType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngredientTypeDTO> findOne(Long id);

    /**
     * Delete the "id" ingredientType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
