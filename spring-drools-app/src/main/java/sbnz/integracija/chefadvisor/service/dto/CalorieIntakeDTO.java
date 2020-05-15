package sbnz.integracija.chefadvisor.service.dto;

import sbnz.integracija.chefadvisor.domain.enumeration.Gender;

public class CalorieIntakeDTO {
	private Double bodyWeight;
	private Double height;
	private Integer age;
	private Gender gender;
	
	public CalorieIntakeDTO() {
		super();
	}
	
	public CalorieIntakeDTO(Double bodyWeight, Double height, Integer age, Gender gender) {
		super();
		this.bodyWeight = bodyWeight;
		this.height = height;
		this.age = age;
		this.gender = gender;
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
	
	public Double calculateBMR() {
		Integer remainder = this.gender.equals(Gender.MALE) ? 5 : (161 * -1);
		return 10 * this.bodyWeight + 6.25 * this.height - 5 * this.age + remainder;
	}
	
	
}