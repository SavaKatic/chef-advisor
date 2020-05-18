package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.IngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ingredient} and its DTO {@link IngredientDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, IngredientModelMapper.class, DishMapper.class})
public interface IngredientMapper extends EntityMapper<IngredientDTO, Ingredient> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "ingredientModel.id", target = "ingredientModelId")
    @Mapping(source = "ingredientModel.name", target = "ingredientModelName")
    @Mapping(source = "dish.id", target = "dishId")
    @Mapping(source = "dish.name", target = "dishName")
    IngredientDTO toDto(Ingredient ingredient);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "ingredientModelId", target = "ingredientModel")
    @Mapping(source = "dishId", target = "dish")
    Ingredient toEntity(IngredientDTO ingredientDTO);

    default Ingredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        return ingredient;
    }
}
