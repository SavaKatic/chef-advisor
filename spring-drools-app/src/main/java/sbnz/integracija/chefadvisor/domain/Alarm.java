package sbnz.integracija.chefadvisor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Alarm.
 */
@Entity
@Table(name = "alarm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "number_of_suspicious_actions")
    private Integer numberOfSuspiciousActions;

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

    public String getMessage() {
        return message;
    }

    public Alarm message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNumberOfSuspiciousActions() {
        return numberOfSuspiciousActions;
    }

    public Alarm numberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
        return this;
    }

    public void setNumberOfSuspiciousActions(Integer numberOfSuspiciousActions) {
        this.numberOfSuspiciousActions = numberOfSuspiciousActions;
    }

    public User getUser() {
        return user;
    }

    public Alarm user(User user) {
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
        if (!(o instanceof Alarm)) {
            return false;
        }
        return id != null && id.equals(((Alarm) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alarm{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", numberOfSuspiciousActions=" + getNumberOfSuspiciousActions() +
            "}";
    }
}
