package sbnz.integracija.chefadvisor.repository;

import sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SpamDetectionTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpamDetectionTemplateRepository extends JpaRepository<SpamDetectionTemplate, Long> {
}
