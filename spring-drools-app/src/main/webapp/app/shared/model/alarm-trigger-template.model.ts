export interface IAlarmTriggerTemplate {
  id?: number;
  numberOfSuspiciousActions?: number;
  warningMessage?: string;
}

export class AlarmTriggerTemplate implements IAlarmTriggerTemplate {
  constructor(public id?: number, public numberOfSuspiciousActions?: number, public warningMessage?: string) {}
}
