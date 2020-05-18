package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.IngredientType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IngredientType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {
}
