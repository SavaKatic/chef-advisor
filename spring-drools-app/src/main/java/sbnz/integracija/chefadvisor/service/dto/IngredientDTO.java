package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.Ingredient} entity.
 */
public class IngredientDTO implements Serializable {
    
    private Long id;

    private Double amount;


    private Long userId;

    private String userLogin;

    private Long ingredientModelId;

    private String ingredientModelName;

    private Long dishId;

    private String dishName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getIngredientModelId() {
        return ingredientModelId;
    }

    public void setIngredientModelId(Long ingredientModelId) {
        this.ingredientModelId = ingredientModelId;
    }

    public String getIngredientModelName() {
        return ingredientModelName;
    }

    public void setIngredientModelName(String ingredientModelName) {
        this.ingredientModelName = ingredientModelName;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientDTO ingredientDTO = (IngredientDTO) o;
        if (ingredientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ingredientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IngredientDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", ingredientModelId=" + getIngredientModelId() +
            ", ingredientModelName='" + getIngredientModelName() + "'" +
            ", dishId=" + getDishId() +
            ", dishName='" + getDishName() + "'" +
            "}";
    }
}
