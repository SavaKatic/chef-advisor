package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class CalorieConfigurationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalorieConfigurationDTO.class);
        CalorieConfigurationDTO calorieConfigurationDTO1 = new CalorieConfigurationDTO();
        calorieConfigurationDTO1.setId(1L);
        CalorieConfigurationDTO calorieConfigurationDTO2 = new CalorieConfigurationDTO();
        assertThat(calorieConfigurationDTO1).isNotEqualTo(calorieConfigurationDTO2);
        calorieConfigurationDTO2.setId(calorieConfigurationDTO1.getId());
        assertThat(calorieConfigurationDTO1).isEqualTo(calorieConfigurationDTO2);
        calorieConfigurationDTO2.setId(2L);
        assertThat(calorieConfigurationDTO1).isNotEqualTo(calorieConfigurationDTO2);
        calorieConfigurationDTO1.setId(null);
        assertThat(calorieConfigurationDTO1).isNotEqualTo(calorieConfigurationDTO2);
    }
}
