package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class AlarmTriggerTemplateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmTriggerTemplateDTO.class);
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO1 = new AlarmTriggerTemplateDTO();
        alarmTriggerTemplateDTO1.setId(1L);
        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO2 = new AlarmTriggerTemplateDTO();
        assertThat(alarmTriggerTemplateDTO1).isNotEqualTo(alarmTriggerTemplateDTO2);
        alarmTriggerTemplateDTO2.setId(alarmTriggerTemplateDTO1.getId());
        assertThat(alarmTriggerTemplateDTO1).isEqualTo(alarmTriggerTemplateDTO2);
        alarmTriggerTemplateDTO2.setId(2L);
        assertThat(alarmTriggerTemplateDTO1).isNotEqualTo(alarmTriggerTemplateDTO2);
        alarmTriggerTemplateDTO1.setId(null);
        assertThat(alarmTriggerTemplateDTO1).isNotEqualTo(alarmTriggerTemplateDTO2);
    }
}
