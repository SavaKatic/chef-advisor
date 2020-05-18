import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDishType, DishType } from 'app/shared/model/dish-type.model';
import { DishTypeService } from './dish-type.service';

@Component({
  selector: 'jhi-dish-type-update',
  templateUrl: './dish-type-update.component.html'
})
export class DishTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected dishTypeService: DishTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dishType }) => {
      this.updateForm(dishType);
    });
  }

  updateForm(dishType: IDishType): void {
    this.editForm.patchValue({
      id: dishType.id,
      name: dishType.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dishType = this.createFromForm();
    if (dishType.id !== undefined) {
      this.subscribeToSaveResponse(this.dishTypeService.update(dishType));
    } else {
      this.subscribeToSaveResponse(this.dishTypeService.create(dishType));
    }
  }

  private createFromForm(): IDishType {
    return {
      ...new DishType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDishType>>): void {
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
