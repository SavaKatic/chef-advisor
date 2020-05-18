package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.UnitType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UnitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {
}
