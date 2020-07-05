package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class SpamDetectionTemplateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpamDetectionTemplateDTO.class);
        SpamDetectionTemplateDTO spamDetectionTemplateDTO1 = new SpamDetectionTemplateDTO();
        spamDetectionTemplateDTO1.setId(1L);
        SpamDetectionTemplateDTO spamDetectionTemplateDTO2 = new SpamDetectionTemplateDTO();
        assertThat(spamDetectionTemplateDTO1).isNotEqualTo(spamDetectionTemplateDTO2);
        spamDetectionTemplateDTO2.setId(spamDetectionTemplateDTO1.getId());
        assertThat(spamDetectionTemplateDTO1).isEqualTo(spamDetectionTemplateDTO2);
        spamDetectionTemplateDTO2.setId(2L);
        assertThat(spamDetectionTemplateDTO1).isNotEqualTo(spamDetectionTemplateDTO2);
        spamDetectionTemplateDTO1.setId(null);
        assertThat(spamDetectionTemplateDTO1).isNotEqualTo(spamDetectionTemplateDTO2);
    }
}
