export interface IRating {
  id?: number;
  comment?: string;
  rating?: number;
  userLogin?: string;
  userId?: number;
  dishName?: string;
  dishId?: number;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public comment?: string,
    public rating?: number,
    public userLogin?: string,
    public userId?: number,
    public dishName?: string,
    public dishId?: number
  ) {}
}
