export interface IIngredient {
  id?: number;
  amount?: number;
  ingredients?: IIngredient[];
  userLogin?: string;
  userId?: number;
  ingredientModelName?: string;
  ingredientModelId?: number;
  dishName?: string;
  dishId?: number;
  ingredientId?: number;
}

export class Ingredient implements IIngredient {
  constructor(
    public id?: number,
    public amount?: number,
    public ingredients?: IIngredient[],
    public userLogin?: string,
    public userId?: number,
    public ingredientModelName?: string,
    public ingredientModelId?: number,
    public dishName?: string,
    public dishId?: number,
    public ingredientId?: number
  ) {}
}
