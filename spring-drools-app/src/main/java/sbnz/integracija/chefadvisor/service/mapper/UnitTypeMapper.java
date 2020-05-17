package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.UnitTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UnitType} and its DTO {@link UnitTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitTypeMapper extends EntityMapper<UnitTypeDTO, UnitType> {


    @Mapping(target = "ingredientModels", ignore = true)
    @Mapping(target = "removeIngredientModel", ignore = true)
    UnitType toEntity(UnitTypeDTO unitTypeDTO);

    default UnitType fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnitType unitType = new UnitType();
        unitType.setId(id);
        return unitType;
    }
}
