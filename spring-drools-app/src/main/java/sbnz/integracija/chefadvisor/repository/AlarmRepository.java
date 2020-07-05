package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.Alarm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alarm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
