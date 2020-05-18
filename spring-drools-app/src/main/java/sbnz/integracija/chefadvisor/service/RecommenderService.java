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
import sbnz.integracija.chefadvisor.facts.ReverseSearchFact;
import sbnz.integracija.chefadvisor.facts.SearchFact;
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

	
	public List<DishDTO> getPossibleDishes(SearchFact s) {
        List<Ingredient> fridgeList = ingredientRepository.findByUserIsCurrentUser();
        s.setFridge(fridgeList);
        System.out.println(fridgeList);

        List<Dish> allDishesList = dishRepository.findAllWithEagerRelationships();
        
        
		KieSession kieSession = kieContainer.newKieSession();
	    kieSession.insert(s);
	    for(Dish d: allDishesList) {
	    	kieSession.insert(d);
	    }
	    
	    Set<Dish> result = new HashSet<Dish>();
	    kieSession.setGlobal("possibleDishes", result);
	    kieSession.setGlobal("missingIngredients", new HashSet<Ingredient>());
	    
	    kieSession.getAgenda().getAgendaGroup("search").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    

		return dishMapper.toDto(new ArrayList<Dish>(result));
	}
	
	public List<IngredientDTO> reverseSearchMissingIngredients(Long dishId) {
        List<Ingredient> fridgeList = ingredientRepository.findByUserIsCurrentUser();
        ReverseSearchFact s = new ReverseSearchFact();
        s.setFridge(fridgeList);
        
        Dish desiredDish = dishRepository.getOne(dishId);
        
		KieSession kieSession = kieContainer.newKieSession();

	    kieSession.insert(s);
	    kieSession.insert(desiredDish);
	    

	    Set<Ingredient> result = new HashSet<Ingredient>();
	    kieSession.setGlobal("possibleDishes", new HashSet<Dish>());
	    kieSession.setGlobal("missingIngredients", result);
	    
	    kieSession.getAgenda().getAgendaGroup("reverse-search").setFocus();
		kieSession.fireAllRules();
	    kieSession.dispose();
	    

		return ingredientMapper.toDto(new ArrayList<Ingredient>(result));
	}


}
