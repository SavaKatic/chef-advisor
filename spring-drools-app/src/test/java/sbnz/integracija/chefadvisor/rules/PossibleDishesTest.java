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
		/*
		 * Cilj: pronalazenje jela po tipu i kategoriji
		 * */
		
		// pravljenje novog kie session-a
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    // dodavanje dva jela, jedan za breakfast i drugi dinner
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    
	    // fact koji sadrzi sve inpute za search
	    SearchInputFact s = new SearchInputFact(false, "BREAKFAST", "Salad", new CalorieConfiguration());

	    // sva jela ubacujem u input fact
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
	    // input fact insertujem u novokreirani session
	    kieSession.insert(s);
	    
		assertEquals(2, s.getDishes().size());
	    
	    // pokrecem search pravila - izvrsice se samo prvo za "non-strict" search
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		// iz dishes liste fact-a treba da se izbaci jedan dish
		// posto je non-strict pretraga, gledace se prva dva pravila koja izbacuju
		// na osnovu tipa i kategorije
		assertEquals(1, s.getDishes().size());
		assertEquals("test dish", s.getDishes().get(0).getName());
	}
	
	@Test
	public void testFindingPossibleDishesByCategoryAndTypeStrict() {
		/*
		 * Cilj: pronalazenje jela po tipu, kategoriji i sastojcima
		 * imajuci u vidu kolicine sastojaka koje su potrebne i koje su u frizideru
		 * */
		// pravljenje novog kie session-a
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    // pravljenje novih modela sastojaka
	    // model sastojka sadrzi sve informacije o sastojku sem kolicine
	    // kolicina se definise zasebno u klasi Ingredient, koja se uvezuje
	    // sa jelima (Dish)
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 3.0, new UnitType());
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, new UnitType());

	    // pravljenje novih sastojaka
	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    
	    // za prvo jelo potreban je samo prvi sastojak
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    dish1.addIngredient(testIngredient);
	    
	    // za drugo jelo potrebna su oba sastojka
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    dish2.addIngredient(testIngredient2);
	    dish2.addIngredient(testIngredient);

	    // dodajem kalorijski opseg koji nije bitan za ovaj konkretan test
	    CalorieConfiguration c = new CalorieConfiguration();
	    c.setBreakfastLow(0.0);
	    c.setBreakfastHigh(1000.0);
	    SearchInputFact s = new SearchInputFact(true, "BREAKFAST", "Salad", c);
	    
	    // pravim frizider korisnika, koji sadrzi samo prvi sastojak (u odgovarajucoj kolicini)
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient);
        s.setFridge(fridgeList);
	    
        // ubacujem oba jela u input fact
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
	    // zatim i sam input fact u sesiju
	    kieSession.insert(s);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		// nakon izvrsenih pravila ocekiva se da samo prvo jelo za koje smo imali
		// sastojak u fridge-u bude odabrano
		assertEquals(1, s.getDishes().size());
	}
	
	@Test
	public void testSortingDishes() {
		/*
		 * Cilj: sortiranje pronadjenih jela po prosecnoj oceni
		 * */
		// pravljenje novog kie session-a
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	    // pravljenje novih jela
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    
	    // vestacki mock-ujem result fact koji bi inace bio rezultat
	    // izvrsavanja prethodnih pravila za search
	    // i potom se sve ubacuje u session
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
		
		// ocekiva se da sortiranje bude obrnuto jer drugi dish ima bolji average score (4.3 > 4.2)
	    assertEquals(2, srf.getSortedDishes().size());
	    assertEquals(2l, srf.getSortedDishes().get(0).getId());
	    assertEquals(1l, srf.getSortedDishes().get(1).getId());
	}
	
	@Test
	public void testReplacementIngredient() {
		// pravljenje novog kie session-a
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
	    
	 // pravljenje novih modela sastojaka
	    // model sastojka sadrzi sve informacije o sastojku sem kolicine
	    // kolicina se definise zasebno u klasi Ingredient, koja se uvezuje
	    // sa jelima (Dish)
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 3.0, new UnitType());
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, new UnitType());

	    // pravljenje novih sastojaka
	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    
	    // prvi sastojak moze biti zamenjen drugim
	    testIngredient.addIngredients(testIngredient2);
	    
	    // za prvo jelo potreban je samo prvi sastojak ( ili drugi, zamenski )
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    dish1.addIngredient(testIngredient);
	    
	    // za drugo jelo potrebna su oba sastojka
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    dish2.addIngredient(testIngredient2);
	    dish2.addIngredient(testIngredient);

	    // dodajem kalorijski opseg koji nije bitan za ovaj konkretan test
	    CalorieConfiguration c = new CalorieConfiguration();
	    c.setBreakfastLow(0.0);
	    c.setBreakfastHigh(1000.0);
	    SearchInputFact s = new SearchInputFact(true, "BREAKFAST", "Salad", c);
	    
	    // pravim frizider korisnika, koji sadrzi samo drugi sastojak (u odgovarajucoj kolicini)
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient2);
        s.setFridge(fridgeList);
	    
        // ubacujem oba jela u input fact
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
	    // zatim i sam input fact u sesiju
	    kieSession.insert(s);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		// nakon izvrsenih pravila ocekiva se da samo prvo jelo za koje smo imali
		// zamenski (drugi) sastojak u fridge-u bude odabrano
		assertEquals(1, s.getDishes().size());
		assertEquals("test dish", s.getDishes().get(0).getName());
	}
	
	
	@Test
	public void testAdjustingCalories() {
		// pravljenje novog kie session-a
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
	    KieSession kieSession = kContainer.newKieSession();
		
	    // pravljenje kalorijske konfiguracije
	    CalorieConfiguration cfg = new CalorieConfiguration();
	    cfg.setBreakfastLow(950.0);
	    cfg.setBreakfastHigh(1050.0);
	    
	    // value od unit-a nekog sastojka se koristi za uvecavanje kolicine
	    UnitType unitType = new UnitType();
	    unitType.setName("unittype");
	    unitType.setValue(10.0);
	    
	    // pravim sastojke za jelo gde ce ukupne kalorije biti  (1 x 300 + 3 x 200 = 900)
	    // obzirom na konfiguraciju, morace da se povecaju rekurzivno dok ne budu vece od 950
	    IngredientModel testIngredientModel = new IngredientModel(1l, "ingr", 1.0, unitType);
	    IngredientModel testIngredientModel2 = new IngredientModel(2l, "ingr2", 3.0, unitType);
	    Ingredient testIngredient = new Ingredient(1l, 300.0, testIngredientModel, null);
	    Ingredient testIngredient2 = new Ingredient(2l, 200.0, testIngredientModel2, null);
	    
	    SearchInputFact s = new SearchInputFact(true, "BREAKFAST", "Salad", cfg);
	    
	    // za prvo jelo potreban je samo prvi sastojak
	    Dish dish1 = new Dish(1l, "test dish", DishCategory.BREAKFAST, "cool description", 4.3, new HashSet<Ingredient>(), new DishType(1l, "Salad"));
	    dish1.addIngredient(testIngredient);
	    dish1.addIngredient(testIngredient2);
	    
	    // za drugo jelo potrebna su oba sastojka
	    Dish dish2 = new Dish(2l, "test dish 2", DishCategory.DINNER, "cool description", 4.2, new HashSet<Ingredient>(), new DishType(2l, "Meat"));
	    dish2.addIngredient(testIngredient2);
	    dish2.addIngredient(testIngredient);

	    // pravim frizider korisnika
	    List<Ingredient> fridgeList = new ArrayList<Ingredient>();
	    fridgeList.add(testIngredient);
	    fridgeList.add(testIngredient2);
        s.setFridge(fridgeList);
        
        // ubacujem oba jela u input fact
        List<Dish> allDishesList = new ArrayList<Dish>();
        allDishesList.add(dish1);
        allDishesList.add(dish2);
        s.setDishes(allDishesList);
        
        assertEquals(900, s.getDishes().get(0).getCalories());
        
	    // zatim i sam input fact u sesiju
	    kieSession.insert(s);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		// nakon izvrsenih pravila ocekiva se da samo prvo jelo za koje smo imali
		// sastojak u fridge-u bude odabrano
		assertEquals(1, s.getDishes().size());
		assertEquals(980, s.getDishes().get(0).getCalories());  
	}
}
