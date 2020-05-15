package sbnz.integracija.chefadvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

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

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private IngredientModel ingredientModel;

    @ManyToOne
    @JsonIgnoreProperties("ingredients")
    private Dish dish;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ingredient)) {
            return false;
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
}
