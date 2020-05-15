package sbnz.integracija.chefadvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A DishType.
 */
@Entity
@Table(name = "dish_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DishType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "types")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Dish> dishes = new HashSet<>();

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

    public DishType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public DishType dishes(Set<Dish> dishes) {
        this.dishes = dishes;
        return this;
    }

    public DishType addDishes(Dish dish) {
        this.dishes.add(dish);
        dish.getTypes().add(this);
        return this;
    }

    public DishType removeDishes(Dish dish) {
        this.dishes.remove(dish);
        dish.getTypes().remove(this);
        return this;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DishType)) {
            return false;
        }
        return id != null && id.equals(((DishType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DishType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
