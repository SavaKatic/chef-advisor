package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class IngredientTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientTypeDTO.class);
        IngredientTypeDTO ingredientTypeDTO1 = new IngredientTypeDTO();
        ingredientTypeDTO1.setId(1L);
        IngredientTypeDTO ingredientTypeDTO2 = new IngredientTypeDTO();
        assertThat(ingredientTypeDTO1).isNotEqualTo(ingredientTypeDTO2);
        ingredientTypeDTO2.setId(ingredientTypeDTO1.getId());
        assertThat(ingredientTypeDTO1).isEqualTo(ingredientTypeDTO2);
        ingredientTypeDTO2.setId(2L);
        assertThat(ingredientTypeDTO1).isNotEqualTo(ingredientTypeDTO2);
        ingredientTypeDTO1.setId(null);
        assertThat(ingredientTypeDTO1).isNotEqualTo(ingredientTypeDTO2);
    }
}
