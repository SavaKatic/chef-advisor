package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.DishDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.Dish}.
 */
public interface DishService {

    /**
     * Save a dish.
     *
     * @param dishDTO the entity to save.
     * @return the persisted entity.
     */
    DishDTO save(DishDTO dishDTO);

    /**
     * Get all the dishes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DishDTO> findAll(Pageable pageable);

    /**
     * Get all the dishes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<DishDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" dish.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DishDTO> findOne(Long id);

    /**
     * Delete the "id" dish.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all by user is current user
     * 
     * @return the list of entities
     * */
    Page<DishDTO> findByUserIsCurrentUser(Pageable pageable);
}
