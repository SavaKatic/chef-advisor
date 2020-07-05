package sbnz.integracija.chefadvisor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SpamDetectionTemplate.
 */
@Entity
@Table(name = "spam_detection_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpamDetectionTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_comments")
    private Integer numberOfComments;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "reason")
    private String reason;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public SpamDetectionTemplate numberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
        return this;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getRating() {
        return rating;
    }

    public SpamDetectionTemplate rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReason() {
        return reason;
    }

    public SpamDetectionTemplate reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpamDetectionTemplate)) {
            return false;
        }
        return id != null && id.equals(((SpamDetectionTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SpamDetectionTemplate{" +
            "id=" + getId() +
            ", numberOfComments=" + getNumberOfComments() +
            ", rating=" + getRating() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
