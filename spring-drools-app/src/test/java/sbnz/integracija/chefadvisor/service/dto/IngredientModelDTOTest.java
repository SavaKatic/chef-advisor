package sbnz.integracija.chefadvisor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class IngredientModelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientModelDTO.class);
        IngredientModelDTO ingredientModelDTO1 = new IngredientModelDTO();
        ingredientModelDTO1.setId(1L);
        IngredientModelDTO ingredientModelDTO2 = new IngredientModelDTO();
        assertThat(ingredientModelDTO1).isNotEqualTo(ingredientModelDTO2);
        ingredientModelDTO2.setId(ingredientModelDTO1.getId());
        assertThat(ingredientModelDTO1).isEqualTo(ingredientModelDTO2);
        ingredientModelDTO2.setId(2L);
        assertThat(ingredientModelDTO1).isNotEqualTo(ingredientModelDTO2);
        ingredientModelDTO1.setId(null);
        assertThat(ingredientModelDTO1).isNotEqualTo(ingredientModelDTO2);
    }
}
