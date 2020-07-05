package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.SpamDetectionTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpamDetectionTemplate} and its DTO {@link SpamDetectionTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpamDetectionTemplateMapper extends EntityMapper<SpamDetectionTemplateDTO, SpamDetectionTemplate> {



    default SpamDetectionTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        SpamDetectionTemplate spamDetectionTemplate = new SpamDetectionTemplate();
        spamDetectionTemplate.setId(id);
        return spamDetectionTemplate;
    }
}
