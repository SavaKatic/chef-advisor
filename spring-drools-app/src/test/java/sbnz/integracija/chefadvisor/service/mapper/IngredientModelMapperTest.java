package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IngredientModelMapperTest {

    private IngredientModelMapper ingredientModelMapper;

    @BeforeEach
    public void setUp() {
        ingredientModelMapper = new IngredientModelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ingredientModelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ingredientModelMapper.fromId(null)).isNull();
    }
}
