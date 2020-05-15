package sbnz.integracija.chefadvisor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sbnz.integracija.chefadvisor.web.rest.TestUtil;

public class IngredientModelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientModel.class);
        IngredientModel ingredientModel1 = new IngredientModel();
        ingredientModel1.setId(1L);
        IngredientModel ingredientModel2 = new IngredientModel();
        ingredientModel2.setId(ingredientModel1.getId());
        assertThat(ingredientModel1).isEqualTo(ingredientModel2);
        ingredientModel2.setId(2L);
        assertThat(ingredientModel1).isNotEqualTo(ingredientModel2);
        ingredientModel1.setId(null);
        assertThat(ingredientModel1).isNotEqualTo(ingredientModel2);
    }
}
