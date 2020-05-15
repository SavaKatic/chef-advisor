package sbnz.integracija.chefadvisor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CalorieConfiguration.
 */
@Entity
@Table(name = "calorie_configuration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CalorieConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "breakfast_low")
    private Double breakfastLow;

    @Column(name = "breakfast_high")
    private Double breakfastHigh;

    @Column(name = "lunch_low")
    private Double lunchLow;

    @Column(name = "lunch_high")
    private Double lunchHigh;

    @Column(name = "dinner_low")
    private Double dinnerLow;

    @Column(name = "dinner_high")
    private Double dinnerHigh;

    @Column(name = "snack_low")
    private Double snackLow;

    @Column(name = "snack_high")
    private Double snackHigh;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBreakfastLow() {
        return breakfastLow;
    }

    public CalorieConfiguration breakfastLow(Double breakfastLow) {
        this.breakfastLow = breakfastLow;
        return this;
    }

    public void setBreakfastLow(Double breakfastLow) {
        this.breakfastLow = breakfastLow;
    }

    public Double getBreakfastHigh() {
        return breakfastHigh;
    }

    public CalorieConfiguration breakfastHigh(Double breakfastHigh) {
        this.breakfastHigh = breakfastHigh;
        return this;
    }

    public void setBreakfastHigh(Double breakfastHigh) {
        this.breakfastHigh = breakfastHigh;
    }

    public Double getLunchLow() {
        return lunchLow;
    }

    public CalorieConfiguration lunchLow(Double lunchLow) {
        this.lunchLow = lunchLow;
        return this;
    }

    public void setLunchLow(Double lunchLow) {
        this.lunchLow = lunchLow;
    }

    public Double getLunchHigh() {
        return lunchHigh;
    }

    public CalorieConfiguration lunchHigh(Double lunchHigh) {
        this.lunchHigh = lunchHigh;
        return this;
    }

    public void setLunchHigh(Double lunchHigh) {
        this.lunchHigh = lunchHigh;
    }

    public Double getDinnerLow() {
        return dinnerLow;
    }

    public CalorieConfiguration dinnerLow(Double dinnerLow) {
        this.dinnerLow = dinnerLow;
        return this;
    }

    public void setDinnerLow(Double dinnerLow) {
        this.dinnerLow = dinnerLow;
    }

    public Double getDinnerHigh() {
        return dinnerHigh;
    }

    public CalorieConfiguration dinnerHigh(Double dinnerHigh) {
        this.dinnerHigh = dinnerHigh;
        return this;
    }

    public void setDinnerHigh(Double dinnerHigh) {
        this.dinnerHigh = dinnerHigh;
    }

    public Double getSnackLow() {
        return snackLow;
    }

    public CalorieConfiguration snackLow(Double snackLow) {
        this.snackLow = snackLow;
        return this;
    }

    public void setSnackLow(Double snackLow) {
        this.snackLow = snackLow;
    }

    public Double getSnackHigh() {
        return snackHigh;
    }

    public CalorieConfiguration snackHigh(Double snackHigh) {
        this.snackHigh = snackHigh;
        return this;
    }

    public void setSnackHigh(Double snackHigh) {
        this.snackHigh = snackHigh;
    }

    public User getUser() {
        return user;
    }

    public CalorieConfiguration user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalorieConfiguration)) {
            return false;
        }
        return id != null && id.equals(((CalorieConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CalorieConfiguration{" +
            "id=" + getId() +
            ", breakfastLow=" + getBreakfastLow() +
            ", breakfastHigh=" + getBreakfastHigh() +
            ", lunchLow=" + getLunchLow() +
            ", lunchHigh=" + getLunchHigh() +
            ", dinnerLow=" + getDinnerLow() +
            ", dinnerHigh=" + getDinnerHigh() +
            ", snackLow=" + getSnackLow() +
            ", snackHigh=" + getSnackHigh() +
            "}";
    }
}
