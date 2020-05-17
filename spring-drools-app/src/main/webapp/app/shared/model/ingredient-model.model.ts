import { IIngredient } from 'app/shared/model/ingredient.model';
import { IUnitType } from 'app/shared/model/unit-type.model';
import { IIngredientType } from 'app/shared/model/ingredient-type.model';

export interface IIngredientModel {
  id?: number;
  name?: string;
  caloriesPerUnit?: number;
  ingredients?: IIngredient[];
  unitTypes?: IUnitType[];
  ingredientTypes?: IIngredientType[];
}

export class IngredientModel implements IIngredientModel {
  constructor(
    public id?: number,
    public name?: string,
    public caloriesPerUnit?: number,
    public ingredients?: IIngredient[],
    public unitTypes?: IUnitType[],
    public ingredientTypes?: IIngredientType[]
  ) {}
}
