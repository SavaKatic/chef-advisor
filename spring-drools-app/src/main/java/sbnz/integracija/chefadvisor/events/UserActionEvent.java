package sbnz.integracija.chefadvisor.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("2h30m")
public class UserActionEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Long userId;
    private Double rating;

    public UserActionEvent() {
        super();
    }

	public UserActionEvent(Long userId) {
		super();
		this.userId = userId;
        this.executionTime = new Date();
	}

	public UserActionEvent(Long userId, Double rating) {
		super();
		this.userId = userId;
		this.rating = rating;
        this.executionTime = new Date();
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}

