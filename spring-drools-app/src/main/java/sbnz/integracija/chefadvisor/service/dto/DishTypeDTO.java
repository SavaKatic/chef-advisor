package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.DishType} entity.
 */
public class DishTypeDTO implements Serializable {
    
    private Long id;

    private String name;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishTypeDTO dishTypeDTO = (DishTypeDTO) o;
        if (dishTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
