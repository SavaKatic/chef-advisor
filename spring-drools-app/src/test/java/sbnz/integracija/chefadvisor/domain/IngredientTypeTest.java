package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class IngredientTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientType.class);
        IngredientType ingredientType1 = new IngredientType();
        ingredientType1.setId(1L);
        IngredientType ingredientType2 = new IngredientType();
        ingredientType2.setId(ingredientType1.getId());
        assertThat(ingredientType1).isEqualTo(ingredientType2);
        ingredientType2.setId(2L);
        assertThat(ingredientType1).isNotEqualTo(ingredientType2);
        ingredientType1.setId(null);
        assertThat(ingredientType1).isNotEqualTo(ingredientType2);
    }
}
