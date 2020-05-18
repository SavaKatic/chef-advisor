import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

export interface IIngredientType {
  id?: number;
  name?: string;
  ingredientModels?: IIngredientModel[];
}

export class IngredientType implements IIngredientType {
  constructor(public id?: number, public name?: string, public ingredientModels?: IIngredientModel[]) {}
}
