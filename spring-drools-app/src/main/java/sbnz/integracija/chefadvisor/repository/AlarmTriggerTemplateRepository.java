package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlarmTriggerTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmTriggerTemplateRepository extends JpaRepository<AlarmTriggerTemplate, Long> {
}
