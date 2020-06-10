package sbnz.integracija.chefadvisor.facts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.Ingredient;

public class SearchResultFact {
	private Set<Dish> possibleDishes;
	private List<Dish> sortedDishes;
	private Set<Ingredient> missingIngredients;
	
	public SearchResultFact() {
		super();
		this.possibleDishes = new HashSet<Dish>();
		this.sortedDishes = new ArrayList<Dish>();
		this.missingIngredients = new HashSet<Ingredient>();
	}
	
	public SearchResultFact(Set<Dish> possibleDishes, List<Dish> sortedDishes, Set<Ingredient> missingIngredients) {
		super();
		this.possibleDishes = possibleDishes;
		this.sortedDishes = sortedDishes;
		this.missingIngredients = missingIngredients;
	}
	public Set<Dish> getPossibleDishes() {
		return possibleDishes;
	}
	public void setPossibleDishes(Set<Dish> possibleDishes) {
		this.possibleDishes = possibleDishes;
	}
	public List<Dish> getSortedDishes() {
		return sortedDishes;
	}
	public void setSortedDishes(List<Dish> sortedDishes) {
		this.sortedDishes = sortedDishes;
	}

	public Set<Ingredient> getMissingIngredients() {
		return missingIngredients;
	}

	public void setMissingIngredients(Set<Ingredient> missingIngredients) {
		this.missingIngredients = missingIngredients;
	}
	
}
