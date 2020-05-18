package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitTypeMapperTest {

    private UnitTypeMapper unitTypeMapper;

    @BeforeEach
    public void setUp() {
        unitTypeMapper = new UnitTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(unitTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unitTypeMapper.fromId(null)).isNull();
    }
}
