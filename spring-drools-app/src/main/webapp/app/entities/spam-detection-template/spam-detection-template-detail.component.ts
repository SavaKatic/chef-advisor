import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

@Component({
  selector: 'jhi-spam-detection-template-detail',
  templateUrl: './spam-detection-template-detail.component.html'
})
export class SpamDetectionTemplateDetailComponent implements OnInit {
  spamDetectionTemplate: ISpamDetectionTemplate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spamDetectionTemplate }) => (this.spamDetectionTemplate = spamDetectionTemplate));
  }

  previousState(): void {
    window.history.back();
  }
}
