package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.DishTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DishType} and its DTO {@link DishTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DishTypeMapper extends EntityMapper<DishTypeDTO, DishType> {


    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "removeDish", ignore = true)
    DishType toEntity(DishTypeDTO dishTypeDTO);

    default DishType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DishType dishType = new DishType();
        dishType.setId(id);
        return dishType;
    }
}
