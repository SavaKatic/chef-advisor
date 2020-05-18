import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIngredientType, IngredientType } from 'app/shared/model/ingredient-type.model';
import { IngredientTypeService } from './ingredient-type.service';

@Component({
  selector: 'jhi-ingredient-type-update',
  templateUrl: './ingredient-type-update.component.html'
})
export class IngredientTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected ingredientTypeService: IngredientTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientType }) => {
      this.updateForm(ingredientType);
    });
  }

  updateForm(ingredientType: IIngredientType): void {
    this.editForm.patchValue({
      id: ingredientType.id,
      name: ingredientType.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredientType = this.createFromForm();
    if (ingredientType.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientTypeService.update(ingredientType));
    } else {
      this.subscribeToSaveResponse(this.ingredientTypeService.create(ingredientType));
    }
  }

  private createFromForm(): IIngredientType {
    return {
      ...new IngredientType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientType>>): void {
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
}
