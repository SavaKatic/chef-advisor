import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';
import { SpamDetectionTemplateService } from './spam-detection-template.service';

@Component({
  templateUrl: './spam-detection-template-delete-dialog.component.html'
})
export class SpamDetectionTemplateDeleteDialogComponent {
  spamDetectionTemplate?: ISpamDetectionTemplate;

  constructor(
    protected spamDetectionTemplateService: SpamDetectionTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.spamDetectionTemplateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('spamDetectionTemplateListModification');
      this.activeModal.close();
    });
  }
}
