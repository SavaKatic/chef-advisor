import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlarmTriggerTemplate, AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';
import { AlarmTriggerTemplateService } from './alarm-trigger-template.service';

@Component({
  selector: 'jhi-alarm-trigger-template-update',
  templateUrl: './alarm-trigger-template-update.component.html'
})
export class AlarmTriggerTemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberOfSuspiciousActions: [],
    warningMessage: []
  });

  constructor(
    protected alarmTriggerTemplateService: AlarmTriggerTemplateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarmTriggerTemplate }) => {
      this.updateForm(alarmTriggerTemplate);
    });
  }

  updateForm(alarmTriggerTemplate: IAlarmTriggerTemplate): void {
    this.editForm.patchValue({
      id: alarmTriggerTemplate.id,
      numberOfSuspiciousActions: alarmTriggerTemplate.numberOfSuspiciousActions,
      warningMessage: alarmTriggerTemplate.warningMessage
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alarmTriggerTemplate = this.createFromForm();
    if (alarmTriggerTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmTriggerTemplateService.update(alarmTriggerTemplate));
    } else {
      this.subscribeToSaveResponse(this.alarmTriggerTemplateService.create(alarmTriggerTemplate));
    }
  }

  private createFromForm(): IAlarmTriggerTemplate {
    return {
      ...new AlarmTriggerTemplate(),
      id: this.editForm.get(['id'])!.value,
      numberOfSuspiciousActions: this.editForm.get(['numberOfSuspiciousActions'])!.value,
      warningMessage: this.editForm.get(['warningMessage'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarmTriggerTemplate>>): void {
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
