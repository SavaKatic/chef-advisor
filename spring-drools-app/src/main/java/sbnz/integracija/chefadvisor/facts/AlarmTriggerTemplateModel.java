package sbnz.integracija.chefadvisor.facts;

public class AlarmTriggerTemplateModel {
	private int numberOfSuspiciousActions;
	
	public AlarmTriggerTemplateModel() {
		super();
	}
	
	public AlarmTriggerTemplateModel(int numberOfSuspiciousActions) {
		super();
		this.numberOfSuspiciousActions = numberOfSuspiciousActions;
	}

	public int getNumberOfSuspiciousActions() {
		return numberOfSuspiciousActions;
	}

	public void setNumberOfSuspiciousActions(int numberOfSuspiciousActions) {
		this.numberOfSuspiciousActions = numberOfSuspiciousActions;
	}

	
}
