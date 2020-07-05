package sbnz.integracija.chefadvisor.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.domain.enumeration.IngredientBelonging;
import sbnz.integracija.chefadvisor.facts.BackwardsIngredientFact;
import sbnz.integracija.chefadvisor.facts.SearchInputFact;
import sbnz.integracija.chefadvisor.facts.SearchResultFact;
import sbnz.integracija.chefadvisor.repository.DishRepository;
import sbnz.integracija.chefadvisor.repository.IngredientRepository;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;
import sbnz.integracija.chefadvisor.service.dto.IngredientDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishMapper;
import sbnz.integracija.chefadvisor.service.mapper.IngredientMapper;

@Service
public class RecommenderService {

	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
    private DishMapper dishMapper;
	
	@Autowired
	private IngredientMapper ingredientMapper;
    
	@Autowired
    private IngredientRepository ingredientRepository;
	
	@Autowired
	private DishRepository dishRepository;

	
	public List<DishDTO> getPossibleDishes(SearchInputFact s) {
        List<Ingredient> fridgeList = ingredientRepository.findByUserIsCurrentUser();
        s.setFridge(fridgeList);

        List<Dish> allDishesList = dishRepository.findAllWithEagerRelationships();
        s.setDishes(allDishesList);
        
		KieSession kieSession = kieContainer.newKieSession();
	    kieSession.insert(s);
	    
	    SearchResultFact srf = new SearchResultFact();
	    kieSession.insert(srf);
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
		kieSession.dispose();
		
		kieSession = kieContainer.newKieSession();
		kieSession.insert(s);
		kieSession.insert(srf);
	    kieSession.getAgenda().getAgendaGroup("sort").setFocus();
	    kieSession.fireAllRules();
		kieSession.dispose();
		
		return dishMapper.toDto(srf.getSortedDishes());
	}
	
	public List<IngredientDTO> reverseSearchMissingIngredients(Long dishId) {
        List<Ingredient> fridgeList = ingredientRepository.findByUserIsCurrentUser();
        SearchInputFact s = new SearchInputFact();
        s.setFridge(fridgeList);
        
        Dish desiredDish = dishRepository.getOne(dishId);
        List<Dish> dishList = new ArrayList<Dish>();
        dishList.add(desiredDish);
        s.setDishes(dishList);
        
        SearchResultFact srf = new SearchResultFact();
        
		KieSession kieSession = kieContainer.newKieSession();

	    kieSession.insert(s);
	    kieSession.insert(srf);

	    kieSession.getAgenda().getAgendaGroup("reverse-search").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    

		return ingredientMapper.toDto(new ArrayList<Ingredient>(srf.getMissingIngredients()));
	}

	public boolean getIfIngredientBelongsToDish(String ingredient, String dish) {
		System.out.println(ingredient);
		System.out.println(dish);
		BackwardsIngredientFact bif = new BackwardsIngredientFact(ingredient, dish);
		KieSession kieSession = kieContainer.newKieSession();
		
		kieSession.insert(bif);
		List<Dish> allDishesList = dishRepository.findAllWithEagerRelationships();
		List<Ingredient> allIngredientsList = ingredientRepository.findAll();
		for(Dish d: allDishesList) {
			kieSession.insert(d);
		}
		for(Ingredient i: allIngredientsList) {
			kieSession.insert(i);
		}
		
		kieSession.getAgenda().getAgendaGroup("backwards").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    
	    System.out.println(bif.getBelonging());
		
	    return bif.getBelonging() == IngredientBelonging.BELONGS;
	}

}
