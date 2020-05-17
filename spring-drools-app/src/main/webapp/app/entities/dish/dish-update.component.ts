import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDish, Dish } from 'app/shared/model/dish.model';
import { DishService } from './dish.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDishType } from 'app/shared/model/dish-type.model';
import { DishTypeService } from 'app/entities/dish-type/dish-type.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IDishType | IUser;

@Component({
  selector: 'jhi-dish-update',
  templateUrl: './dish-update.component.html'
})
export class DishUpdateComponent implements OnInit {
  isSaving = false;
  dishtypes: IDishType[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    category: [],
    image: [],
    imageContentType: [],
    description: [],
    types: [],
    users: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected dishService: DishService,
    protected dishTypeService: DishTypeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dish }) => {
      this.updateForm(dish);

      this.dishTypeService.query().subscribe((res: HttpResponse<IDishType[]>) => (this.dishtypes = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(dish: IDish): void {
    this.editForm.patchValue({
      id: dish.id,
      name: dish.name,
      category: dish.category,
      image: dish.image,
      imageContentType: dish.imageContentType,
      description: dish.description,
      types: dish.types,
      users: dish.users
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
    const dish = this.createFromForm();
    if (dish.id !== undefined) {
      this.subscribeToSaveResponse(this.dishService.update(dish));
    } else {
      this.subscribeToSaveResponse(this.dishService.create(dish));
    }
  }

  private createFromForm(): IDish {
    return {
      ...new Dish(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      description: this.editForm.get(['description'])!.value,
      types: this.editForm.get(['types'])!.value,
      users: this.editForm.get(['users'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDish>>): void {
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
