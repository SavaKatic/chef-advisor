package sbnz.integracija.chefadvisor.facts;

public class SpamDetectionTemplateModel {
	private int numberOfComments;
	private int rating;
	private String reason;
	
	public SpamDetectionTemplateModel() {
		super();
	}
	
	public SpamDetectionTemplateModel(int numberOfComments, int rating, String reason) {
		super();
		this.numberOfComments = numberOfComments;
		this.rating = rating;
		this.reason = reason;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
