package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class DishDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishDTO.class);
        DishDTO dishDTO1 = new DishDTO();
        dishDTO1.setId(1L);
        DishDTO dishDTO2 = new DishDTO();
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
        dishDTO2.setId(dishDTO1.getId());
        assertThat(dishDTO1).isEqualTo(dishDTO2);
        dishDTO2.setId(2L);
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
        dishDTO1.setId(null);
        assertThat(dishDTO1).isNotEqualTo(dishDTO2);
    }
}
