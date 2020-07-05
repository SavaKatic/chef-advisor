import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISpamDetectionTemplate, SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';
import { SpamDetectionTemplateService } from './spam-detection-template.service';

@Component({
  selector: 'jhi-spam-detection-template-update',
  templateUrl: './spam-detection-template-update.component.html'
})
export class SpamDetectionTemplateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numberOfComments: [],
    rating: [],
    reason: []
  });

  constructor(
    protected spamDetectionTemplateService: SpamDetectionTemplateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spamDetectionTemplate }) => {
      this.updateForm(spamDetectionTemplate);
    });
  }

  updateForm(spamDetectionTemplate: ISpamDetectionTemplate): void {
    this.editForm.patchValue({
      id: spamDetectionTemplate.id,
      numberOfComments: spamDetectionTemplate.numberOfComments,
      rating: spamDetectionTemplate.rating,
      reason: spamDetectionTemplate.reason
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spamDetectionTemplate = this.createFromForm();
    if (spamDetectionTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.spamDetectionTemplateService.update(spamDetectionTemplate));
    } else {
      this.subscribeToSaveResponse(this.spamDetectionTemplateService.create(spamDetectionTemplate));
    }
  }

  private createFromForm(): ISpamDetectionTemplate {
    return {
      ...new SpamDetectionTemplate(),
      id: this.editForm.get(['id'])!.value,
      numberOfComments: this.editForm.get(['numberOfComments'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      reason: this.editForm.get(['reason'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpamDetectionTemplate>>): void {
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
