package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import sbnz.integracija.chefadvisor.domain.enumeration.DishCategory;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.Dish} entity.
 */
public class DishDTO implements Serializable {
    
    private Long id;

    private String name;

    private DishCategory category;

    @Lob
    private byte[] image;

    private String imageContentType;
    private Set<DishTypeDTO> types = new HashSet<>();
    private Set<UserDTO> users = new HashSet<>();
    
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

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
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

    public Set<DishTypeDTO> getTypes() {
        return types;
    }

    public void setTypes(Set<DishTypeDTO> dishTypes) {
        this.types = dishTypes;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishDTO dishDTO = (DishDTO) o;
        if (dishDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", image='" + getImage() + "'" +
            ", types='" + getTypes() + "'" +
            ", users='" + getUsers() + "'" +
            "}";
    }
}
