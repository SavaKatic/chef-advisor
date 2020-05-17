package sbnz.integracija.chefadvisor.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.facts.SearchFact;
import sbnz.integracija.chefadvisor.repository.DishRepository;
import sbnz.integracija.chefadvisor.repository.IngredientRepository;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;
import sbnz.integracija.chefadvisor.service.mapper.DishMapper;

@Service
public class RecommenderService {

	@Autowired
	private KieContainer kieContainer;
	
	@Autowired
    private DishMapper dishMapper;
    
	@Autowired
    private IngredientRepository ingredientRepository;
	
	@Autowired
	private DishRepository dishRepository;

	
	public List<DishDTO> getDishes(SearchFact s) {
        List<Ingredient> fridgeList = ingredientRepository.findByUserIsCurrentUser();
        s.setFridge(fridgeList);

        List<Dish> allDishesList = dishRepository.findAll();
        
		KieSession kieSession = kieContainer.newKieSession();
	    kieSession.insert(s);
	    kieSession.insert(allDishesList);

		kieSession.fireAllRules();
	    kieSession.dispose();

		return dishMapper.toDto(allDishesList);
	}


}
