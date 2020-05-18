package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class UnitTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitTypeDTO.class);
        UnitTypeDTO unitTypeDTO1 = new UnitTypeDTO();
        unitTypeDTO1.setId(1L);
        UnitTypeDTO unitTypeDTO2 = new UnitTypeDTO();
        assertThat(unitTypeDTO1).isNotEqualTo(unitTypeDTO2);
        unitTypeDTO2.setId(unitTypeDTO1.getId());
        assertThat(unitTypeDTO1).isEqualTo(unitTypeDTO2);
        unitTypeDTO2.setId(2L);
        assertThat(unitTypeDTO1).isNotEqualTo(unitTypeDTO2);
        unitTypeDTO1.setId(null);
        assertThat(unitTypeDTO1).isNotEqualTo(unitTypeDTO2);
    }
}
