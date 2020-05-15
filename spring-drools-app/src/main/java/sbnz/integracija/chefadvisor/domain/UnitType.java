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
 * A UnitType.
 */
@Entity
@Table(name = "unit_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnitType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "unitTypes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<IngredientModel> ingredientModels = new HashSet<>();

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

    public UnitType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientModel> getIngredientModels() {
        return ingredientModels;
    }

    public UnitType ingredientModels(Set<IngredientModel> ingredientModels) {
        this.ingredientModels = ingredientModels;
        return this;
    }

    public UnitType addIngredientModels(IngredientModel ingredientModel) {
        this.ingredientModels.add(ingredientModel);
        ingredientModel.getUnitTypes().add(this);
        return this;
    }

    public UnitType removeIngredientModels(IngredientModel ingredientModel) {
        this.ingredientModels.remove(ingredientModel);
        ingredientModel.getUnitTypes().remove(this);
        return this;
    }

    public void setIngredientModels(Set<IngredientModel> ingredientModels) {
        this.ingredientModels = ingredientModels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitType)) {
            return false;
        }
        return id != null && id.equals(((UnitType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnitType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
