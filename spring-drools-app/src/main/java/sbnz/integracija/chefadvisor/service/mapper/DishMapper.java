package sbnz.integracija.chefadvisor.service.mapper;


import sbnz.integracija.chefadvisor.domain.*;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dish} and its DTO {@link DishDTO}.
 */
@Mapper(componentModel = "spring", uses = {DishTypeMapper.class, UserMapper.class})
public interface DishMapper extends EntityMapper<DishDTO, Dish> {


    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "removeIngredient", ignore = true)
    @Mapping(target = "removeTypes", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
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
