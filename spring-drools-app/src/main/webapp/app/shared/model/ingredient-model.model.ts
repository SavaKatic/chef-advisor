import { IIngredient } from 'app/shared/model/ingredient.model';
import { IIngredientType } from 'app/shared/model/ingredient-type.model';

export interface IIngredientModel {
  id?: number;
  name?: string;
  caloriesPerUnit?: number;
  ingredients?: IIngredient[];
  ingredientTypes?: IIngredientType[];
  unitTypeName?: string;
  unitTypeId?: number;
}

export class IngredientModel implements IIngredientModel {
  constructor(
    public id?: number,
    public name?: string,
    public caloriesPerUnit?: number,
    public ingredients?: IIngredient[],
    public ingredientTypes?: IIngredientType[],
    public unitTypeName?: string,
    public unitTypeId?: number
  ) {}
}
