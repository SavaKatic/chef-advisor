import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUnitType, UnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from './unit-type.service';

@Component({
  selector: 'jhi-unit-type-update',
  templateUrl: './unit-type-update.component.html'
})
export class UnitTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected unitTypeService: UnitTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ unitType }) => {
      this.updateForm(unitType);
    });
  }

  updateForm(unitType: IUnitType): void {
    this.editForm.patchValue({
      id: unitType.id,
      name: unitType.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const unitType = this.createFromForm();
    if (unitType.id !== undefined) {
      this.subscribeToSaveResponse(this.unitTypeService.update(unitType));
    } else {
      this.subscribeToSaveResponse(this.unitTypeService.create(unitType));
    }
  }

  private createFromForm(): IUnitType {
    return {
      ...new UnitType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUnitType>>): void {
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
