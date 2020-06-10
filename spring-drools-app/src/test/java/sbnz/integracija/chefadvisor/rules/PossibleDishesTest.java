package sbnz.integracija.chefadvisor.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.DishType;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.domain.IngredientModel;
import sbnz.integracija.chefadvisor.domain.UnitType;
import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;
import sbnz.integracija.chefadvisor.facts.SearchInputFact;
import sbnz.integracija.chefadvisor.facts.SearchResultFact;

public class PossibleDishesTest {
	@Test
	public void testFindingPossibleDishesByCategoryAndTypeNonStrict() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    
	    SearchInputFact s = new SearchInputFact(false, "BREAKFAST", "Salad", new CalorieConfiguration());

        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
	    
	    kieSession.insert(s);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(1, s.getDishes().size());
	}
	
	@Test
	public void testFindingPossibleDishesByCategoryAndTypeStrict() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 3.0, new UnitType());
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, new UnitType());

	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    dish1.addIngredient(testIngredient);
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    dish2.addIngredient(testIngredient2);
	    dish2.addIngredient(testIngredient);

	    CalorieConfiguration c = new CalorieConfiguration();
	    c.setBreakfastLow(0.0);
	    c.setBreakfastHigh(1000.0);
	    SearchInputFact s = new SearchInputFact(true, "BREAKFAST", "Salad", c);
	    
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient);
        s.setFridge(fridgeList);
	    
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
	    
	    kieSession.insert(s);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(1, s.getDishes().size());
	}
	
	@Test
	public void testSortingDishes() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    

	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    
	    SearchInputFact s = new SearchInputFact();
	    SearchResultFact srf = new SearchResultFact();
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
        kieSession.insert(s);
        kieSession.insert(srf);
	    
	    kieSession.getAgenda().getAgendaGroup("sort").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
	    assertEquals(2, srf.getSortedDishes().size());
	    assertEquals(2l, srf.getSortedDishes().get(0).getId());
	    assertEquals(1l, srf.getSortedDishes().get(1).getId());
	}
}
