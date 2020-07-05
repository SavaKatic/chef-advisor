package sbnz.integracija.chefadvisor.facts;

public class AlarmInputFact {
	private Long userId;
	private String message;
	
	public AlarmInputFact() {
		super();
	}

	public AlarmInputFact(Long userId, String message) {
		super();
		this.userId = userId;
		this.message = message;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
