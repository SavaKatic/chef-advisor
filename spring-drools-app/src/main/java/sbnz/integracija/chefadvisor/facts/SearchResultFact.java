package sbnz.integracija.chefadvisor.facts;

import java.util.HashSet;
import java.util.Set;

import sbnz.integracija.chefadvisor.domain.Dish;
import sbnz.integracija.chefadvisor.domain.Ingredient;

public class SearchResultFact {
	private Set<Dish> possibleDishes;
	private Set<Dish> sortedDishes;
	private Set<Ingredient> missingIngredients;
	
	public SearchResultFact() {
		super();
		this.possibleDishes = new HashSet<Dish>();
		this.sortedDishes = new HashSet<Dish>();
		this.missingIngredients = new HashSet<Ingredient>();
	}
	
	public SearchResultFact(Set<Dish> possibleDishes, Set<Dish> sortedDishes, Set<Ingredient> missingIngredients) {
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
	public Set<Dish> getSortedDishes() {
		return sortedDishes;
	}
	public void setSortedDishes(Set<Dish> sortedDishes) {
		this.sortedDishes = sortedDishes;
	}

	public Set<Ingredient> getMissingIngredients() {
		return missingIngredients;
	}

	public void setMissingIngredients(Set<Ingredient> missingIngredients) {
		this.missingIngredients = missingIngredients;
	}
	
}
