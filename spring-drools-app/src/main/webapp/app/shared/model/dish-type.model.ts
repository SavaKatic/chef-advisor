import { IDish } from 'app/shared/model/dish.model';

export interface IDishType {
  id?: number;
  name?: string;
  dishes?: IDish[];
}

export class DishType implements IDishType {
  constructor(public id?: number, public name?: string, public dishes?: IDish[]) {}
}
