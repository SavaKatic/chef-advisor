package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class CalorieConfigurationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalorieConfiguration.class);
        CalorieConfiguration calorieConfiguration1 = new CalorieConfiguration();
        calorieConfiguration1.setId(1L);
        CalorieConfiguration calorieConfiguration2 = new CalorieConfiguration();
        calorieConfiguration2.setId(calorieConfiguration1.getId());
        assertThat(calorieConfiguration1).isEqualTo(calorieConfiguration2);
        calorieConfiguration2.setId(2L);
        assertThat(calorieConfiguration1).isNotEqualTo(calorieConfiguration2);
        calorieConfiguration1.setId(null);
        assertThat(calorieConfiguration1).isNotEqualTo(calorieConfiguration2);
    }
}
