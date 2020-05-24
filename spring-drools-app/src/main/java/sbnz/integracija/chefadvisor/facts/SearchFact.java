package sbnz.integracija.chefadvisor.facts;

import java.util.List;

import sbnz.integracija.chefadvisor.domain.CalorieConfiguration;
import sbnz.integracija.chefadvisor.domain.Ingredient;
import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;

public class SearchFact {
	private boolean strict;
	private List<Ingredient> fridge;
	private DishCategory category;
	private String type;
	private Double minimumCalories;
	private Double maximumCalories;
	
	public SearchFact() {
		super();
	}
	
	public SearchFact(boolean strict, String category, String type, CalorieConfiguration config) {
		this.strict = strict;
		this.type = type;
		this.category = DishCategory.valueOf(category);

		switch(this.category) {
			case BREAKFAST:
				this.minimumCalories = config.getBreakfastLow();
				this.maximumCalories = config.getBreakfastHigh();
				break;
			case LUNCH:
				this.minimumCalories = config.getLunchLow();
				this.maximumCalories = config.getLunchHigh();
				break;
			case DINNER:
				this.minimumCalories = config.getDinnerLow();
				this.maximumCalories = config.getDinnerHigh();
				break;
			case SNACK:
				this.minimumCalories = config.getSnackLow();
				this.maximumCalories = config.getSnackHigh();
				break;
			default:
				this.minimumCalories = 0.0;
				this.maximumCalories = 0.0;
		}

	}
	
	public SearchFact(boolean strict, List<Ingredient> fridge, DishCategory category, String type) {
		super();
		this.strict = strict;
		this.fridge = fridge;
		this.category = category;
		this.type = type;
	}

	

	public Double getMinimumCalories() {
		return minimumCalories;
	}

	public void setMinimumCalories(Double minimumCalories) {
		this.minimumCalories = minimumCalories;
	}

	public Double getMaximumCalories() {
		return maximumCalories;
	}

	public void setMaximumCalories(Double maximumCalories) {
		this.maximumCalories = maximumCalories;
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
