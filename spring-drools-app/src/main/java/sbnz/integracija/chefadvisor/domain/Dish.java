package sbnz.integracija.chefadvisor.domain;

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

    @OneToMany(mappedBy = "dish")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dish_types",
               joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "types_id", referencedColumnName = "id"))
    private Set<DishType> types = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dish_users",
               joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

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

    public Set<DishType> getTypes() {
        return types;
    }

    public Dish types(Set<DishType> dishTypes) {
        this.types = dishTypes;
        return this;
    }

    public Dish addTypes(DishType dishType) {
        this.types.add(dishType);
        dishType.getDishes().add(this);
        return this;
    }

    public Dish removeTypes(DishType dishType) {
        this.types.remove(dishType);
        dishType.getDishes().remove(this);
        return this;
    }

    public void setTypes(Set<DishType> dishTypes) {
        this.types = dishTypes;
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
            "}";
    }
}
