package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class SpamDetectionTemplateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpamDetectionTemplate.class);
        SpamDetectionTemplate spamDetectionTemplate1 = new SpamDetectionTemplate();
        spamDetectionTemplate1.setId(1L);
        SpamDetectionTemplate spamDetectionTemplate2 = new SpamDetectionTemplate();
        spamDetectionTemplate2.setId(spamDetectionTemplate1.getId());
        assertThat(spamDetectionTemplate1).isEqualTo(spamDetectionTemplate2);
        spamDetectionTemplate2.setId(2L);
        assertThat(spamDetectionTemplate1).isNotEqualTo(spamDetectionTemplate2);
        spamDetectionTemplate1.setId(null);
        assertThat(spamDetectionTemplate1).isNotEqualTo(spamDetectionTemplate2);
    }
}
