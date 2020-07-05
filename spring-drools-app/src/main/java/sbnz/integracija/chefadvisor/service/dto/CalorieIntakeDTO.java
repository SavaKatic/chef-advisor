package sbnz.integracija.chefadvisor.service.dto;

import sbnz.integracija.chefadvisor.domain.enumeration.ActivityLevel;
import sbnz.integracija.chefadvisor.domain.enumeration.Gender;

public class CalorieIntakeDTO {
	private Double bodyWeight;
	private Double height;
	private Integer age;
	private Gender gender;
	private String level;
	
	public CalorieIntakeDTO() {
		super();
	}
	
	public CalorieIntakeDTO(Double bodyWeight, Double height, Integer age, Gender gender, String level) {
		super();
		this.bodyWeight = bodyWeight;
		this.height = height;
		this.age = age;
		this.gender = gender;
		this.level = level;
	}

	public Double getBodyWeight() {
		return bodyWeight;
	}
	public void setBodyWeight(Double bodyWeight) {
		this.bodyWeight = bodyWeight;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Double calculateDailyIntake() {
		Integer remainder = this.gender.equals(Gender.MALE) ? 5 : (161 * -1);
		Double bmr = 10 * this.bodyWeight + 6.25 * this.height - 5 * this.age + remainder;
		return bmr * ActivityLevel.valueOf(this.level).getActivityIndex();
	}

	@Override
	public String toString() {
		return "CalorieIntakeDTO [bodyWeight=" + bodyWeight + ", height=" + height + ", age=" + age + ", gender="
				+ gender + ", level=" + level + "]";
	}
	
	
}