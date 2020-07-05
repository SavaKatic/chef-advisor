package sbnz.integracija.chefadvisor.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link sbnz.integracija.chefadvisor.domain.SpamDetectionTemplate} entity.
 */
public class SpamDetectionTemplateDTO implements Serializable {
    
    private Long id;

    private Integer numberOfComments;

    private Integer rating;

    private String reason;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpamDetectionTemplateDTO spamDetectionTemplateDTO = (SpamDetectionTemplateDTO) o;
        if (spamDetectionTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spamDetectionTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpamDetectionTemplateDTO{" +
            "id=" + getId() +
            ", numberOfComments=" + getNumberOfComments() +
            ", rating=" + getRating() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
