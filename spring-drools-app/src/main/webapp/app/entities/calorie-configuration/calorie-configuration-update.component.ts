import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICalorieConfiguration, CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';
import { CalorieConfigurationService } from './calorie-configuration.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-calorie-configuration-update',
  templateUrl: './calorie-configuration-update.component.html'
})
export class CalorieConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    breakfastLow: [],
    breakfastHigh: [],
    lunchLow: [],
    lunchHigh: [],
    dinnerLow: [],
    dinnerHigh: [],
    snackLow: [],
    snackHigh: [],
    userId: []
  });

  constructor(
    protected calorieConfigurationService: CalorieConfigurationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ calorieConfiguration }) => {
      this.updateForm(calorieConfiguration);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(calorieConfiguration: ICalorieConfiguration): void {
    this.editForm.patchValue({
      id: calorieConfiguration.id,
      breakfastLow: calorieConfiguration.breakfastLow,
      breakfastHigh: calorieConfiguration.breakfastHigh,
      lunchLow: calorieConfiguration.lunchLow,
      lunchHigh: calorieConfiguration.lunchHigh,
      dinnerLow: calorieConfiguration.dinnerLow,
      dinnerHigh: calorieConfiguration.dinnerHigh,
      snackLow: calorieConfiguration.snackLow,
      snackHigh: calorieConfiguration.snackHigh,
      userId: calorieConfiguration.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const calorieConfiguration = this.createFromForm();
    if (calorieConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.calorieConfigurationService.update(calorieConfiguration));
    } else {
      this.subscribeToSaveResponse(this.calorieConfigurationService.create(calorieConfiguration));
    }
  }

  private createFromForm(): ICalorieConfiguration {
    return {
      ...new CalorieConfiguration(),
      id: this.editForm.get(['id'])!.value,
      breakfastLow: this.editForm.get(['breakfastLow'])!.value,
      breakfastHigh: this.editForm.get(['breakfastHigh'])!.value,
      lunchLow: this.editForm.get(['lunchLow'])!.value,
      lunchHigh: this.editForm.get(['lunchHigh'])!.value,
      dinnerLow: this.editForm.get(['dinnerLow'])!.value,
      dinnerHigh: this.editForm.get(['dinnerHigh'])!.value,
      snackLow: this.editForm.get(['snackLow'])!.value,
      snackHigh: this.editForm.get(['snackHigh'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICalorieConfiguration>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
