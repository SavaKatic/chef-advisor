package sbnz.integracija.chefadvisor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AlarmTriggerTemplate.
 */
@Entity
@Table(name = "alarm_trigger_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlarmTriggerTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_suspicious_actions")
    private Integer numberOfSuspiciousActions;

    @Column(name = "warning_message")
    private String warningMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfSuspiciousActions() {
        return numberOfSuspiciousActions;
    }

    public AlarmTriggerTemplate numberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
        return this;
    }

    public void setNumberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public AlarmTriggerTemplate warningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
        return this;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmTriggerTemplate)) {
            return false;
        }
        return id != null && id.equals(((AlarmTriggerTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AlarmTriggerTemplate{" +
            "id=" + getId() +
            ", numberOfSuspiciousActions=" + getNumberOfSuspiciousActions() +
            ", warningMessage='" + getWarningMessage() + "'" +
            "}";
    }
}
