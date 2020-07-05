export const enum ActivityLevel {
  VERY_LIGHT = 'VERY_LIGHT',
  LIGHT = 'LIGHT',
  MODERATE = 'MODERATE',
  HEAVY = 'HEAVY',
  VERY_HEAVY = 'VERY_HEAVY'
}

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

export interface ICalorieInformation {
  bodyWeight?: number;
  height?: number;
  age?: number;
  gender?: Gender;
  level?: ActivityLevel;
}

export class CalorieInformation implements ICalorieInformation {
  constructor(
    public bodyWeight?: number,
    public height?: number,
    public age?: number,
    public gender?: Gender,
    public level?: ActivityLevel,
  ) {}
}
