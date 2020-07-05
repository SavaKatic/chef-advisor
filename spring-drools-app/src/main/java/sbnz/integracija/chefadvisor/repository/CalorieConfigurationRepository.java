package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.domain.Dish;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CalorieConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalorieConfigurationRepository extends JpaRepository<CalorieConfiguration, Long> {
	@Query("select config from CalorieConfiguration config inner join config.user u where u.login = ?#{principal.username}")
    List<CalorieConfiguration> findByUserIsCurrentUser();
	
}
