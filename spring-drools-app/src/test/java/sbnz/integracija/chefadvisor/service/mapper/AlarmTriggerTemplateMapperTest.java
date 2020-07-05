package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlarmTriggerTemplateMapperTest {

    private AlarmTriggerTemplateMapper alarmTriggerTemplateMapper;

    @BeforeEach
    public void setUp() {
        alarmTriggerTemplateMapper = new AlarmTriggerTemplateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alarmTriggerTemplateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alarmTriggerTemplateMapper.fromId(null)).isNull();
    }
}
