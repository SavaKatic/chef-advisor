package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.DishTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.DishType}.
 */
public interface DishTypeService {

    /**
     * Save a dishType.
     *
     * @param dishTypeDTO the entity to save.
     * @return the persisted entity.
     */
    DishTypeDTO save(DishTypeDTO dishTypeDTO);

    /**
     * Get all the dishTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DishTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dishType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DishTypeDTO> findOne(Long id);

    /**
     * Delete the "id" dishType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
