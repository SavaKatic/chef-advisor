package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.IngredientModel} entity.
 */
public class IngredientModelDTO implements Serializable {
    
    private Long id;

    private String name;

    private Double caloriesPerUnit;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Set<UnitTypeDTO> unitTypes = new HashSet<>();
    private Set<IngredientTypeDTO> ingredientTypes = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCaloriesPerUnit() {
        return caloriesPerUnit;
    }

    public void setCaloriesPerUnit(Double caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<UnitTypeDTO> getUnitTypes() {
        return unitTypes;
    }

    public void setUnitTypes(Set<UnitTypeDTO> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public Set<IngredientTypeDTO> getIngredientTypes() {
        return ingredientTypes;
    }

    public void setIngredientTypes(Set<IngredientTypeDTO> ingredientTypes) {
        this.ingredientTypes = ingredientTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientModelDTO ingredientModelDTO = (IngredientModelDTO) o;
        if (ingredientModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", caloriesPerUnit=" + getCaloriesPerUnit() +
            ", image='" + getImage() + "'" +
            ", unitTypes='" + getUnitTypes() + "'" +
            ", ingredientTypes='" + getIngredientTypes() + "'" +
            "}";
    }
}
