import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlarm, Alarm } from 'app/shared/model/alarm.model';
import { AlarmService } from './alarm.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-alarm-update',
  templateUrl: './alarm-update.component.html'
})
export class AlarmUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    message: [],
    numberOfSuspiciousActions: [],
    userId: []
  });

  constructor(
    protected alarmService: AlarmService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarm }) => {
      this.updateForm(alarm);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(alarm: IAlarm): void {
    this.editForm.patchValue({
      id: alarm.id,
      message: alarm.message,
      numberOfSuspiciousActions: alarm.numberOfSuspiciousActions,
      userId: alarm.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alarm = this.createFromForm();
    if (alarm.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmService.update(alarm));
    } else {
      this.subscribeToSaveResponse(this.alarmService.create(alarm));
    }
  }

  private createFromForm(): IAlarm {
    return {
      ...new Alarm(),
      id: this.editForm.get(['id'])!.value,
      message: this.editForm.get(['message'])!.value,
      numberOfSuspiciousActions: this.editForm.get(['numberOfSuspiciousActions'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarm>>): void {
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
