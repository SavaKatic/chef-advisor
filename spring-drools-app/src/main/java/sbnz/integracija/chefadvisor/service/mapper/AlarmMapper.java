package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.AlarmDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alarm} and its DTO {@link AlarmDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AlarmMapper extends EntityMapper<AlarmDTO, Alarm> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AlarmDTO toDto(Alarm alarm);

    @Mapping(source = "userId", target = "user")
    Alarm toEntity(AlarmDTO alarmDTO);

    default Alarm fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alarm alarm = new Alarm();
        alarm.setId(id);
        return alarm;
    }
}
