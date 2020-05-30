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
 * A Ingredient.
 */
@Entity
@Table(name = "ingredient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @OneToMany(mappedBy = "ingredient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private IngredientModel ingredientModel;

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private Dish dish;

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private Ingredient ingredient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Ingredient amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Ingredient addIngredients(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setIngredient(this);
        return this;
    }

    public Ingredient removeIngredients(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setIngredient(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public User getUser() {
        return user;
    }

    public Ingredient user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IngredientModel getIngredientModel() {
        return ingredientModel;
    }

    public Ingredient ingredientModel(IngredientModel ingredientModel) {
        this.ingredientModel = ingredientModel;
        return this;
    }

    public void setIngredientModel(IngredientModel ingredientModel) {
        this.ingredientModel = ingredientModel;
    }

    public Dish getDish() {
        return dish;
    }

    public Ingredient dish(Dish dish) {
        this.dish = dish;
        return this;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Ingredient ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
        }
        if (this.ingredientModel != null && ((Ingredient) o).ingredientModel != null) {
    		for(Ingredient i: this.ingredients) {
    			if (i.getIngredientModel().getId() == ((Ingredient) o).ingredientModel.getId()) {
    				return true;
    			}
    		}
        	return this.ingredientModel.getId() == ((Ingredient) o).ingredientModel.getId();
        }
        return id != null && id.equals(((Ingredient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            "}";
    }

    public void increaseAmount() {
      this.amount += this.ingredientModel.getUnitType().getValue();
    }
}
