package sbnz.integracija.chefadvisor.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.chefadvisor.facts.SearchFact;
import sbnz.integracija.chefadvisor.service.RecommenderService;
import sbnz.integracija.chefadvisor.service.dto.DishDTO;

/**
 * REST controller for searching Dishes {@link sbnz.integracija.chefadvisor.domain.Dish}.
 */
@RestController
@RequestMapping("/api")
public class RecommenderController {
	
	private final RecommenderService recommenderService;

	@Autowired
	public RecommenderController(RecommenderService recommenderService) {
		this.recommenderService = recommenderService;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<DishDTO>> getDishes(@RequestParam(required = true) boolean isStrict, @RequestParam(required = true) String dishType,
			@RequestParam(required = true) String dishCategory) {
		SearchFact s = new SearchFact(isStrict, dishCategory, dishType);
		List<DishDTO> dishes = this.recommenderService.getDishes(s);
		for(DishDTO d: dishes) {
			System.out.println(d);
		}
//		List<Dish> dishes = new ArrayList<Dish>();
		return ResponseEntity.ok().body(dishes);
	}
}
