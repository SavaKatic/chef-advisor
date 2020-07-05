export interface ISpamDetectionTemplate {
  id?: number;
  numberOfComments?: number;
  rating?: number;
  reason?: string;
}

export class SpamDetectionTemplate implements ISpamDetectionTemplate {
  constructor(public id?: number, public numberOfComments?: number, public rating?: number, public reason?: string) {}
}
