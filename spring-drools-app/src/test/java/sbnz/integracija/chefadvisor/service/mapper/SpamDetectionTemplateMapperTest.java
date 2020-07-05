package sbnz.integracija.chefadvisor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SpamDetectionTemplateMapperTest {

    private SpamDetectionTemplateMapper spamDetectionTemplateMapper;

    @BeforeEach
    public void setUp() {
        spamDetectionTemplateMapper = new SpamDetectionTemplateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(spamDetectionTemplateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(spamDetectionTemplateMapper.fromId(null)).isNull();
    }
}
