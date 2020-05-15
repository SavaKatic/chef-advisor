import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IIngredientModel, IngredientModel } from 'app/shared/model/ingredient-model.model';
import { IngredientModelService } from './ingredient-model.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from 'app/entities/unit-type/unit-type.service';
import { IIngredientType } from 'app/shared/model/ingredient-type.model';
import { IngredientTypeService } from 'app/entities/ingredient-type/ingredient-type.service';

type SelectableEntity = IUnitType | IIngredientType;

@Component({
  selector: 'jhi-ingredient-model-update',
  templateUrl: './ingredient-model-update.component.html'
})
export class IngredientModelUpdateComponent implements OnInit {
  isSaving = false;
  unittypes: IUnitType[] = [];
  ingredienttypes: IIngredientType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    caloriesPerUnit: [],
    image: [],
    imageContentType: [],
    unitTypes: [],
    ingredientTypes: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected ingredientModelService: IngredientModelService,
    protected unitTypeService: UnitTypeService,
    protected ingredientTypeService: IngredientTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ingredientModel }) => {
      this.updateForm(ingredientModel);

      this.unitTypeService.query().subscribe((res: HttpResponse<IUnitType[]>) => (this.unittypes = res.body || []));

      this.ingredientTypeService.query().subscribe((res: HttpResponse<IIngredientType[]>) => (this.ingredienttypes = res.body || []));
    });
  }

  updateForm(ingredientModel: IIngredientModel): void {
    this.editForm.patchValue({
      id: ingredientModel.id,
      name: ingredientModel.name,
      caloriesPerUnit: ingredientModel.caloriesPerUnit,
      image: ingredientModel.image,
      imageContentType: ingredientModel.imageContentType,
      unitTypes: ingredientModel.unitTypes,
      ingredientTypes: ingredientModel.ingredientTypes
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('chefadvisorApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ingredientModel = this.createFromForm();
    if (ingredientModel.id !== undefined) {
      this.subscribeToSaveResponse(this.ingredientModelService.update(ingredientModel));
    } else {
      this.subscribeToSaveResponse(this.ingredientModelService.create(ingredientModel));
    }
  }

  private createFromForm(): IIngredientModel {
    return {
      ...new IngredientModel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      caloriesPerUnit: this.editForm.get(['caloriesPerUnit'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      unitTypes: this.editForm.get(['unitTypes'])!.value,
      ingredientTypes: this.editForm.get(['ingredientTypes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIngredientModel>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
