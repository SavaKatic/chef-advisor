package sbnz.integracija.chefadvisor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;

/**
 * A Dish.
 */
@Entity
@Table(name = "dish")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private DishCategory category;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "dish")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToMany(mappedBy = "dish")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rating> ratings = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dish_users",
               joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private DishType dishType;
    
    @Column(name="chosen")
    private boolean chosen = false;
    

    public boolean isChosen() {
		return chosen;
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

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

    public Dish name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DishCategory getCategory() {
        return category;
    }

    public Dish category(DishCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public Dish image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Dish imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescription() {
        return description;
    }

    public Dish description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Dish ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Dish addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setDish(this);
        return this;
    }

    public Dish removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setDish(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Dish ratings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Dish addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setDish(this);
        return this;
    }

    public Dish removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setDish(null);
        return this;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Dish users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Dish addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Dish removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public DishType getDishType() {
        return dishType;
    }

    public Dish dishType(DishType dishType) {
        this.dishType = dishType;
        return this;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dish)) {
            return false;
        }
        return id != null && id.equals(((Dish) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dish{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
    
    public Double getCalories() {
      double calories = 0;
      for(Ingredient i: this.ingredients) {
        calories += i.getIngredientModel().getCaloriesPerUnit() * i.getAmount();
      }
      return calories;
    }
    
    public void increaseIngredients() {
      for(Ingredient i: this.ingredients) {
        i.increaseAmount();
      }
    }
}
