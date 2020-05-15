import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

export interface IUnitType {
  id?: number;
  name?: string;
  ingredientModels?: IIngredientModel[];
}

export class UnitType implements IUnitType {
  constructor(public id?: number, public name?: string, public ingredientModels?: IIngredientModel[]) {}
}
