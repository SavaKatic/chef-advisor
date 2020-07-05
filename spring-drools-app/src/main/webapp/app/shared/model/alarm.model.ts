export interface IAlarm {
  id?: number;
  message?: string;
  numberOfSuspiciousActions?: number;
  userLogin?: string;
  userId?: number;
}

export class Alarm implements IAlarm {
  constructor(
    public id?: number,
    public message?: string,
    public numberOfSuspiciousActions?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
