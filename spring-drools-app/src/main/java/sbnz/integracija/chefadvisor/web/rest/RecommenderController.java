package sbnz.integracija.chefadvisor.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.facts.SearchInputFact;
import sbnz.integracija.chefadvisor.service.CalorieConfigurationService;
import sbnz.integracija.chefadvisor.service.RecommenderService;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;
import sbnz.integracija.chefadvisor.service.dto.IngredientDTO;

/**
 * REST controller for searching Dishes {@link sbnz.integracija.chefadvisor.domain.Dish}.
 */
@RestController
@RequestMapping("/api")
public class RecommenderController {
	
	private final RecommenderService recommenderService;
	
	private final CalorieConfigurationService calorieConfigurationService;

	@Autowired
	public RecommenderController(RecommenderService recommenderService, CalorieConfigurationService calorieConfigurationService) {
		this.recommenderService = recommenderService;
		this.calorieConfigurationService = calorieConfigurationService;
	}
	
	@RequestMapping(value = "/search-dishes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<DishDTO>> getPossibleDishes(@RequestParam(required = true) boolean isStrict, @RequestParam(required = true) String dishType,
			@RequestParam(required = true) String dishCategory) {
		CalorieConfiguration config = calorieConfigurationService.findByUserIsCurrentUser();
		
		SearchInputFact s = new SearchInputFact(isStrict, dishCategory, dishType, config);
		List<DishDTO> dishes = this.recommenderService.getPossibleDishes(s);
		return ResponseEntity.ok().body(dishes);
	}
	
	@RequestMapping(value = "/search-ingredients", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<IngredientDTO>> reverseSearchMissingIngredients(@RequestParam(required = true) Long dishId) {
		List<IngredientDTO> ingredients = this.recommenderService.reverseSearchMissingIngredients(dishId);
		return ResponseEntity.ok().body(ingredients);
	}
	
	
}
