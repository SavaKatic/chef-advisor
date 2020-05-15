package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientTypeMapperTest {

    private IngredientTypeMapper ingredientTypeMapper;

    @BeforeEach
    public void setUp() {
        ingredientTypeMapper = new IngredientTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ingredientTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ingredientTypeMapper.fromId(null)).isNull();
    }
}
