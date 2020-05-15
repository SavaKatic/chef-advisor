package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class DishTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DishTypeDTO.class);
        DishTypeDTO dishTypeDTO1 = new DishTypeDTO();
        dishTypeDTO1.setId(1L);
        DishTypeDTO dishTypeDTO2 = new DishTypeDTO();
        assertThat(dishTypeDTO1).isNotEqualTo(dishTypeDTO2);
        dishTypeDTO2.setId(dishTypeDTO1.getId());
        assertThat(dishTypeDTO1).isEqualTo(dishTypeDTO2);
        dishTypeDTO2.setId(2L);
        assertThat(dishTypeDTO1).isNotEqualTo(dishTypeDTO2);
        dishTypeDTO1.setId(null);
        assertThat(dishTypeDTO1).isNotEqualTo(dishTypeDTO2);
    }
}
