package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DishMapperTest {

    private DishMapper dishMapper;

    @BeforeEach
    public void setUp() {
        dishMapper = new DishMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dishMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dishMapper.fromId(null)).isNull();
    }
}
