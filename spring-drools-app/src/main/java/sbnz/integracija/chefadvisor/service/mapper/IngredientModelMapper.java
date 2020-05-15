package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.IngredientModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IngredientModel} and its DTO {@link IngredientModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitTypeMapper.class, IngredientTypeMapper.class})
public interface IngredientModelMapper extends EntityMapper<IngredientModelDTO, IngredientModel> {


    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "removeIngredient", ignore = true)
    @Mapping(target = "removeUnitTypes", ignore = true)
    @Mapping(target = "removeIngredientTypes", ignore = true)
    IngredientModel toEntity(IngredientModelDTO ingredientModelDTO);

    default IngredientModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        IngredientModel ingredientModel = new IngredientModel();
        ingredientModel.setId(id);
        return ingredientModel;
    }
}
