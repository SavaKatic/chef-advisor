package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.CalorieConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalorieConfiguration} and its DTO {@link CalorieConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CalorieConfigurationMapper extends EntityMapper<CalorieConfigurationDTO, CalorieConfiguration> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CalorieConfigurationDTO toDto(CalorieConfiguration calorieConfiguration);

    @Mapping(source = "userId", target = "user")
    CalorieConfiguration toEntity(CalorieConfigurationDTO calorieConfigurationDTO);

    default CalorieConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        CalorieConfiguration calorieConfiguration = new CalorieConfiguration();
        calorieConfiguration.setId(id);
        return calorieConfiguration;
    }
}
