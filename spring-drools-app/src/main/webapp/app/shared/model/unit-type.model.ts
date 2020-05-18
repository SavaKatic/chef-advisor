import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

export interface IUnitType {
  id?: number;
  name?: string;
  value?: number;
  ingredientModels?: IIngredientModel[];
}

export class UnitType implements IUnitType {
  constructor(public id?: number, public name?: string, public value?: number, public ingredientModels?: IIngredientModel[]) {}
}
