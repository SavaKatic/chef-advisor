package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.IngredientTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngredientType} and its DTO {@link IngredientTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IngredientTypeMapper extends EntityMapper<IngredientTypeDTO, IngredientType> {


    @Mapping(target = "ingredientModels", ignore = true)
    @Mapping(target = "removeIngredientModels", ignore = true)
    IngredientType toEntity(IngredientTypeDTO ingredientTypeDTO);

    default IngredientType fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientType ingredientType = new IngredientType();
        ingredientType.setId(id);
        return ingredientType;
    }
}
