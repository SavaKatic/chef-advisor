package sbnz.integracija.chefadvisor.facts;

import sbnz.integracija.chefadvisor.domain.enumeration.IngredientBelonging;

public class BackwardsIngredientFact {
	private String ingredient;
	private String dish;
	private IngredientBelonging belonging;
	
	
	public BackwardsIngredientFact() {
		super();
	}
	
	public BackwardsIngredientFact(String ingredient, String dish) {
		super();
		this.ingredient = ingredient;
		this.dish = dish;
		this.belonging = IngredientBelonging.NA;
	}



	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public String getDish() {
		return dish;
	}
	public void setDish(String dish) {
		this.dish = dish;
	}

	public IngredientBelonging getBelonging() {
		return belonging;
	}

	public void setBelonging(IngredientBelonging belonging) {
		this.belonging = belonging;
	}
	
	
	
	
}
