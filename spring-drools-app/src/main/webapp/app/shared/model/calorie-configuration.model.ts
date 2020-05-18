export interface ICalorieConfiguration {
  id?: number;
  breakfastLow?: number;
  breakfastHigh?: number;
  lunchLow?: number;
  lunchHigh?: number;
  dinnerLow?: number;
  dinnerHigh?: number;
  snackLow?: number;
  snackHigh?: number;
  userLogin?: string;
  userId?: number;
}

export class CalorieConfiguration implements ICalorieConfiguration {
  constructor(
    public id?: number,
    public breakfastLow?: number,
    public breakfastHigh?: number,
    public lunchLow?: number,
    public lunchHigh?: number,
    public dinnerLow?: number,
    public dinnerHigh?: number,
    public snackLow?: number,
    public snackHigh?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
