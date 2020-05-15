package sbnz.integracija.chefadvisor.service;

import sbnz.integracija.chefadvisor.service.dto.UnitTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sbnz.integracija.chefadvisor.domain.UnitType}.
 */
public interface UnitTypeService {

    /**
     * Save a unitType.
     *
     * @param unitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    UnitTypeDTO save(UnitTypeDTO unitTypeDTO);

    /**
     * Get all the unitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" unitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitTypeDTO> findOne(Long id);

    /**
     * Delete the "id" unitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
