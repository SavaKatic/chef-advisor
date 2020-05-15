package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CalorieConfigurationMapperTest {

    private CalorieConfigurationMapper calorieConfigurationMapper;

    @BeforeEach
    public void setUp() {
        calorieConfigurationMapper = new CalorieConfigurationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(calorieConfigurationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(calorieConfigurationMapper.fromId(null)).isNull();
    }
}
