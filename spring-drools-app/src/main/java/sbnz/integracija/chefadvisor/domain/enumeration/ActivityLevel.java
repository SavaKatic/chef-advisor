package sbnz.integracija.chefadvisor.domain.enumeration;

public enum ActivityLevel {
	VERY_LIGHT(1.3), LIGHT(1.55), MODERATE(1.65), HEAVY(1.80), VERY_HEAVY(2.00);
	
	private final double activityIndex;
	
	ActivityLevel(double activityIndex) {
		this.activityIndex = activityIndex;
	}
	
	public double getActivityIndex() {
		return this.activityIndex;
	}
	
}
