package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dish} and its DTO {@link DishDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DishTypeMapper.class})
public interface DishMapper extends EntityMapper<DishDTO, Dish> {

    @Mapping(source = "dishType.id", target = "dishTypeId")
    @Mapping(source = "dishType.name", target = "dishTypeName")
    DishDTO toDto(Dish dish);

    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "removeIngredient", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "removeRating", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(source = "dishTypeId", target = "dishType")
    Dish toEntity(DishDTO dishDTO);

    default Dish fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dish dish = new Dish();
        dish.setId(id);
        return dish;
    }
}
