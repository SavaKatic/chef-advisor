import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIngredient, Ingredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from './ingredient.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIngredientModel } from 'app/shared/model/ingredient-model.model';
import { IngredientModelService } from 'app/entities/ingredient-model/ingredient-model.service';
import { IDish } from 'app/shared/model/dish.model';
import { DishService } from 'app/entities/dish/dish.service';
import { AccountService } from 'app/core/auth/account.service';

type SelectableEntity = IUser | IIngredientModel | IDish | IIngredient;

@Component({
  selector: 'jhi-ingredient-update',
  templateUrl: './ingredient-update.component.html'
})
export class IngredientUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  ingredientmodels: IIngredientModel[] = [];
  dishes: IDish[] = [];
  ingredients: IIngredient[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    userId: [],
    ingredientModelId: [],
    dishId: [],
    ingredientId: []
  });

  constructor(
    protected ingredientService: IngredientService,
    protected userService: UserService,
    protected ingredientModelService: IngredientModelService,
    protected dishService: DishService,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredient }) => {
      this.updateForm(ingredient);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.ingredientModelService.query().subscribe((res: HttpResponse<IIngredientModel[]>) => (this.ingredientmodels = res.body || []));

      this.dishService.query().subscribe((res: HttpResponse<IDish[]>) => (this.dishes = res.body || []));

      this.ingredientService.query().subscribe((res: HttpResponse<IIngredient[]>) => (this.ingredients = res.body || []));
    });
  }

  updateForm(ingredient: IIngredient): void {
    this.editForm.patchValue({
      id: ingredient.id,
      amount: ingredient.amount,
      userId: ingredient.userId,
      ingredientModelId: ingredient.ingredientModelId,
      dishId: ingredient.dishId,
      ingredientId: ingredient.ingredientId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredient = this.createFromForm();
    if (!ingredient.userLogin) {
      ingredient.userLogin = this.accountService.getUsername();
      const userObj = this.users.find((user) => {
        return user.login === ingredient.userLogin;
      });
      ingredient.userId = userObj?.id;
    }
    if (ingredient.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientService.update(ingredient));
    } else {
      this.subscribeToSaveResponse(this.ingredientService.create(ingredient));
    }
  }

  private createFromForm(): IIngredient {
    return {
      ...new Ingredient(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      ingredientModelId: this.editForm.get(['ingredientModelId'])!.value,
      dishId: this.editForm.get(['dishId'])!.value,
      ingredientId: this.editForm.get(['ingredientId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredient>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
