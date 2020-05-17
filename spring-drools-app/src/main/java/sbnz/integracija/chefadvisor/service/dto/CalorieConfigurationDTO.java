package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

import sbnz.integracija.chefadvisor.domain.User;


/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.CalorieConfiguration} entity.
 */
public class CalorieConfigurationDTO implements Serializable {
    
    private Long id;

    private Double breakfastLow;

    private Double breakfastHigh;

    private Double lunchLow;

    private Double lunchHigh;

    private Double dinnerLow;

    private Double dinnerHigh;

    private Double snackLow;

    private Double snackHigh;


    private Long userId;

    private String userLogin;
    
    public CalorieConfigurationDTO() {
    	super();
    }
    
    public CalorieConfigurationDTO(Double dailyCaloricIntake, User user) {
    	Double minimalIntake = dailyCaloricIntake - 150;
    	Double maximalIntake = dailyCaloricIntake + 150;
    	
    	this.breakfastLow = minimalIntake / 100 * 40;
    	this.lunchLow = minimalIntake / 100 * 30;
    	this.dinnerLow = minimalIntake / 100 * 20;
    	this.snackLow = minimalIntake / 100 * 10;
    	
    	this.breakfastHigh = maximalIntake / 100 * 40;
    	this.lunchHigh = maximalIntake / 100 * 30;
    	this.dinnerHigh = maximalIntake / 100 * 20;
    	this.snackLow = maximalIntake / 100 * 10;
    	
    	this.userId = user.getId();
    	this.userLogin = user.getLogin();
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBreakfastLow() {
        return breakfastLow;
    }

    public void setBreakfastLow(Double breakfastLow) {
        this.breakfastLow = breakfastLow;
    }

    public Double getBreakfastHigh() {
        return breakfastHigh;
    }

    public void setBreakfastHigh(Double breakfastHigh) {
        this.breakfastHigh = breakfastHigh;
    }

    public Double getLunchLow() {
        return lunchLow;
    }

    public void setLunchLow(Double lunchLow) {
        this.lunchLow = lunchLow;
    }

    public Double getLunchHigh() {
        return lunchHigh;
    }

    public void setLunchHigh(Double lunchHigh) {
        this.lunchHigh = lunchHigh;
    }

    public Double getDinnerLow() {
        return dinnerLow;
    }

    public void setDinnerLow(Double dinnerLow) {
        this.dinnerLow = dinnerLow;
    }

    public Double getDinnerHigh() {
        return dinnerHigh;
    }

    public void setDinnerHigh(Double dinnerHigh) {
        this.dinnerHigh = dinnerHigh;
    }

    public Double getSnackLow() {
        return snackLow;
    }

    public void setSnackLow(Double snackLow) {
        this.snackLow = snackLow;
    }

    public Double getSnackHigh() {
        return snackHigh;
    }

    public void setSnackHigh(Double snackHigh) {
        this.snackHigh = snackHigh;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalorieConfigurationDTO calorieConfigurationDTO = (CalorieConfigurationDTO) o;
        if (calorieConfigurationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), calorieConfigurationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CalorieConfigurationDTO{" +
            "id=" + getId() +
            ", breakfastLow=" + getBreakfastLow() +
            ", breakfastHigh=" + getBreakfastHigh() +
            ", lunchLow=" + getLunchLow() +
            ", lunchHigh=" + getLunchHigh() +
            ", dinnerLow=" + getDinnerLow() +
            ", dinnerHigh=" + getDinnerHigh() +
            ", snackLow=" + getSnackLow() +
            ", snackHigh=" + getSnackHigh() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
