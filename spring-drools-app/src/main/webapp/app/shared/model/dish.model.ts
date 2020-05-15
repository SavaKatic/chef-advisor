import { IIngredient } from 'app/shared/model/ingredient.model';
import { IDishType } from 'app/shared/model/dish-type.model';
import { IUser } from 'app/core/user/user.model';
import { DishCategory } from 'app/shared/model/enumerations/dish-category.model';

export interface IDish {
  id?: number;
  name?: string;
  category?: DishCategory;
  imageContentType?: string;
  image?: any;
  ingredients?: IIngredient[];
  types?: IDishType[];
  users?: IUser[];
}

export class Dish implements IDish {
  constructor(
    public id?: number,
    public name?: string,
    public category?: DishCategory,
    public imageContentType?: string,
    public image?: any,
    public ingredients?: IIngredient[],
    public types?: IDishType[],
    public users?: IUser[]
  ) {}
}
