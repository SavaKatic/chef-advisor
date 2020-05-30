import { IIngredient } from 'app/shared/model/ingredient.model';
import { IRating } from 'app/shared/model/rating.model';
import { IUser } from 'app/core/user/user.model';
import { DishCategory } from 'app/shared/model/enumerations/dish-category.model';

export interface IDish {
  id?: number;
  name?: string;
  category?: DishCategory;
  imageContentType?: string;
  image?: any;
  description?: string;
  averageRating?: number;
  ingredients?: IIngredient[];
  ratings?: IRating[];
  users?: IUser[];
  dishTypeName?: string;
  dishTypeId?: number;
}

export class Dish implements IDish {
  constructor(
    public id?: number,
    public name?: string,
    public category?: DishCategory,
    public imageContentType?: string,
    public image?: any,
    public description?: string,
    public averageRating?: number,
    public ingredients?: IIngredient[],
    public ratings?: IRating[],
    public users?: IUser[],
    public dishTypeName?: string,
    public dishTypeId?: number
  ) {}
}
