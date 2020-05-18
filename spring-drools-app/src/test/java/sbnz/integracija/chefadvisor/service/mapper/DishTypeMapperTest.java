package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTypeMapperTest {

    private DishTypeMapper dishTypeMapper;

    @BeforeEach
    public void setUp() {
        dishTypeMapper = new DishTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dishTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dishTypeMapper.fromId(null)).isNull();
    }
}
