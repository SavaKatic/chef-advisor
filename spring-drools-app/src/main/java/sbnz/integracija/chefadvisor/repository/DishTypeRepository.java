package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.DishType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DishType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DishTypeRepository extends JpaRepository<DishType, Long> {
}
