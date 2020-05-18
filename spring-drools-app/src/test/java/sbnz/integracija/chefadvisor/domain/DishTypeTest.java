package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class DishTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishType.class);
        DishType dishType1 = new DishType();
        dishType1.setId(1L);
        DishType dishType2 = new DishType();
        dishType2.setId(dishType1.getId());
        assertThat(dishType1).isEqualTo(dishType2);
        dishType2.setId(2L);
        assertThat(dishType1).isNotEqualTo(dishType2);
        dishType1.setId(null);
        assertThat(dishType1).isNotEqualTo(dishType2);
    }
}
