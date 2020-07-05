package sbnz.integracija.chefadvisor.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.DishType;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.domain.IngredientModel;
import sbnz.integracija.chefadvisor.domain.UnitType;
import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;
import sbnz.integracija.chefadvisor.domain.enumeration.IngredientBelonging;
import sbnz.integracija.chefadvisor.facts.BackwardsIngredientFact;


public class BackwardsIngredientTest {
	@Test
	public void testIfIngredientBelongsToDish() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    UnitType unitType = new UnitType();
	    unitType.setName("unittype");
	    unitType.setValue(10.0);
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 1.0, unitType);
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, unitType);
	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    
	    dish1.addIngredient(testIngredient);
	    
	    BackwardsIngredientFact bif = new BackwardsIngredientFact("ingr", "test dish");
	    
	    kieSession.insert(bif);
	    kieSession.insert(dish1);
	    kieSession.insert(testIngredient);
	    kieSession.insert(testIngredient2);

	    kieSession.getAgenda().getAgendaGroup("backwards").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    
	    assertEquals(IngredientBelonging.BELONGS, bif.getBelonging());
	}
}
