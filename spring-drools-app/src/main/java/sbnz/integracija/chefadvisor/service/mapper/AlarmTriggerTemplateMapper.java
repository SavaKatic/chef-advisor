package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.AlarmTriggerTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlarmTriggerTemplate} and its DTO {@link AlarmTriggerTemplateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlarmTriggerTemplateMapper extends EntityMapper<AlarmTriggerTemplateDTO, AlarmTriggerTemplate> {



    default AlarmTriggerTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlarmTriggerTemplate alarmTriggerTemplate = new AlarmTriggerTemplate();
        alarmTriggerTemplate.setId(id);
        return alarmTriggerTemplate;
    }
}
