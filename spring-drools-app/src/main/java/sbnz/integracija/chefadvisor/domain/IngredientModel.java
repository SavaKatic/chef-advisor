package sbnz.integracija.chefadvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A IngredientModel.
 */
@Entity
@Table(name = "ingredient_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IngredientModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "calories_per_unit")
    private Double caloriesPerUnit;

    @OneToMany(mappedBy = "ingredientModel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ingredient_model_ingredient_types",
               joinColumns = @JoinColumn(name = "ingredient_model_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ingredient_types_id", referencedColumnName = "id"))
    private Set<IngredientType> ingredientTypes = new HashSet<>();

    public IngredientModel() {
    	super();
    }
    
    public IngredientModel(Long id, String name, Double caloriesPerUnit, UnitType unitType) {
		super();
		this.id = id;
		this.name = name;
		this.caloriesPerUnit = caloriesPerUnit;
		this.unitType = unitType;
	}

	@ManyToOne
    @JsonIgnoreProperties("ingredientModels")
    private UnitType unitType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IngredientModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public IngredientModel caloriesPerUnit(Double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
        return this;
    }

    public void setCaloriesPerUnit(Double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public IngredientModel ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public IngredientModel addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setIngredientModel(this);
        return this;
    }

    public IngredientModel removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setIngredientModel(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<IngredientType> getIngredientTypes() {
        return ingredientTypes;
    }

    public IngredientModel ingredientTypes(Set<IngredientType> ingredientTypes) {
        this.ingredientTypes = ingredientTypes;
        return this;
    }

    public IngredientModel addIngredientTypes(IngredientType ingredientType) {
        this.ingredientTypes.add(ingredientType);
        ingredientType.getIngredientModels().add(this);
        return this;
    }

    public IngredientModel removeIngredientTypes(IngredientType ingredientType) {
        this.ingredientTypes.remove(ingredientType);
        ingredientType.getIngredientModels().remove(this);
        return this;
    }

    public void setIngredientTypes(Set<IngredientType> ingredientTypes) {
        this.ingredientTypes = ingredientTypes;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public IngredientModel unitType(UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IngredientModel)) {
            return false;
        }
        return id != null && id.equals(((IngredientModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IngredientModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", caloriesPerUnit=" + getCaloriesPerUnit() +
            "}";
    }
}
