package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class AlarmTriggerTemplateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmTriggerTemplate.class);
        AlarmTriggerTemplate alarmTriggerTemplate1 = new AlarmTriggerTemplate();
        alarmTriggerTemplate1.setId(1L);
        AlarmTriggerTemplate alarmTriggerTemplate2 = new AlarmTriggerTemplate();
        alarmTriggerTemplate2.setId(alarmTriggerTemplate1.getId());
        assertThat(alarmTriggerTemplate1).isEqualTo(alarmTriggerTemplate2);
        alarmTriggerTemplate2.setId(2L);
        assertThat(alarmTriggerTemplate1).isNotEqualTo(alarmTriggerTemplate2);
        alarmTriggerTemplate1.setId(null);
        assertThat(alarmTriggerTemplate1).isNotEqualTo(alarmTriggerTemplate2);
    }
}
