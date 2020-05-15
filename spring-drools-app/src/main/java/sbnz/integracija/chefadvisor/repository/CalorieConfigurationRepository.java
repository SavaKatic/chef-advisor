package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalorieConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalorieConfigurationRepository extends JpaRepository<CalorieConfiguration, Long> {
}
