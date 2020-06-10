package sbnz.integracija.chefadvisor.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import sbnz.integracija.chefadvisor.facts.SearchInputFact;
import sbnz.integracija.chefadvisor.facts.SearchResultFact;

public class MissingIngredientsTest {
	
	@Test
	public void testReverseSearchForMissingIngredient() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    // make an ingredient and add it to the fridge
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 3.0, new UnitType());
	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient);

        SearchInputFact s = new SearchInputFact();
        s.setFridge(fridgeList);
        
        // add two ingredients that are required for a dish
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, new UnitType());
	    Ingredient testIngredient2 = new Ingredient(2l, 400.0, testIngredientModel2, null);
        Set<Ingredient> dishIngredients = new HashSet<Ingredient>();
        dishIngredients.add(testIngredient);
        dishIngredients.add(testIngredient2);
        Dish desiredDish = new Dish(1l, "dish", DishCategory.BREAKFAST, "opis", 3.0, dishIngredients, new DishType());
        List<Dish> dishList = new ArrayList<Dish>();
        dishList.add(desiredDish);
        s.setDishes(dishList);
        
        SearchResultFact srf = new SearchResultFact();
        
	    kieSession.insert(s);
	    kieSession.insert(srf);

	    kieSession.getAgenda().getAgendaGroup("reverse-search").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    
	    // rules would return the missing ingredient
	    assertEquals(srf.getMissingIngredients().size(), 1);
	    List<Ingredient> missingIngredientList = new ArrayList<Ingredient>();
	    assertEquals(srf.getMissingIngredients().size(), 1);
	    missingIngredientList.addAll(srf.getMissingIngredients());
	    Ingredient i = missingIngredientList.get(0);
	    assertEquals(i.getAmount(), 400);
	}
	
	@Test
	public void testReverseSearchForQuantityNotEnoughIngredient() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    // make two ingredients, one that will have enough quantity and other that is missing some
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 3.0, new UnitType());
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, new UnitType());

	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient);
	    fridgeList.add(testIngredient2);

        SearchInputFact s = new SearchInputFact();
        s.setFridge(fridgeList);
        
        // add two ingredients that are required for a dish
	    Ingredient testIngredient3 = new Ingredient(2l, 400.0, testIngredientModel2, null);
        Set<Ingredient> dishIngredients = new HashSet<Ingredient>();
        dishIngredients.add(testIngredient);
        dishIngredients.add(testIngredient3);
        Dish desiredDish = new Dish(1l, "dish", DishCategory.BREAKFAST, "opis", 3.0, dishIngredients, new DishType());
        List<Dish> dishList = new ArrayList<Dish>();
        dishList.add(desiredDish);
        s.setDishes(dishList);
        
        SearchResultFact srf = new SearchResultFact();
        
	    kieSession.insert(s);
	    kieSession.insert(srf);

	    kieSession.getAgenda().getAgendaGroup("reverse-search").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    
	    // rules would return the quantity that is needed for the ingredient that should be refilled
	    List<Ingredient> missingIngredientList = new ArrayList<Ingredient>();
	    assertEquals(srf.getMissingIngredients().size(), 1);
	    missingIngredientList.addAll(srf.getMissingIngredients());
	    Ingredient i = missingIngredientList.get(0);
	    assertEquals(i.getAmount(), 200);
	    
	}

}
