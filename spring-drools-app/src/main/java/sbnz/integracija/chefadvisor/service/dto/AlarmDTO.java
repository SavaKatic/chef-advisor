package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.Alarm} entity.
 */
public class AlarmDTO implements Serializable {
    
    private Long id;

    private String message;

    private Integer numberOfSuspiciousActions;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNumberOfSuspiciousActions() {
        return numberOfSuspiciousActions;
    }

    public void setNumberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
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

        AlarmDTO alarmDTO = (AlarmDTO) o;
        if (alarmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", numberOfSuspiciousActions=" + getNumberOfSuspiciousActions() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
