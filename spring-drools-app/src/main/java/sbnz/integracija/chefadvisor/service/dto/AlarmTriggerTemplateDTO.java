package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.AlarmTriggerTemplate} entity.
 */
public class AlarmTriggerTemplateDTO implements Serializable {
    
    private Long id;

    private Integer numberOfSuspiciousActions;

    private String warningMessage;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfSuspiciousActions() {
        return numberOfSuspiciousActions;
    }

    public void setNumberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlarmTriggerTemplateDTO alarmTriggerTemplateDTO = (AlarmTriggerTemplateDTO) o;
        if (alarmTriggerTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmTriggerTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmTriggerTemplateDTO{" +
            "id=" + getId() +
            ", numberOfSuspiciousActions=" + getNumberOfSuspiciousActions() +
            ", warningMessage='" + getWarningMessage() + "'" +
            "}";
    }
}
