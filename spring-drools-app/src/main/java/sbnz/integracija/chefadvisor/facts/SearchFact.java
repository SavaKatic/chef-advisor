package sbnz.integracija.chefadvisor.facts;

import java.util.List;

import sbnz.integracija.chefadvisor.domain.DishType;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;

public class SearchFact {
	private boolean strict;
	private List<Ingredient> fridge;
	private DishCategory category;
	private String type;
	
	public SearchFact() {
		super();
	}
	
	public SearchFact(boolean strict, String category, String type) {
		this.strict = strict;
		this.type = type;
		this.category = DishCategory.valueOf(category);
	}
	
	public SearchFact(boolean strict, List<Ingredient> fridge, DishCategory category, String type) {
		super();
		this.strict = strict;
		this.fridge = fridge;
		this.category = category;
		this.type = type;
	}

	

	public boolean isStrict() {
		return strict;
	}

	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	public List<Ingredient> getFridge() {
		return fridge;
	}

	public void setFridge(List<Ingredient> fridge) {
		this.fridge = fridge;
	}

	public DishCategory getCategory() {
		return category;
	}

	public void setCategory(DishCategory category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SearchFact [ fridge=" + fridge + ", category=" + category + ", type=" + type
				+ "]";
	}


	
	
}
