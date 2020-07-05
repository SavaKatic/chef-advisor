import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';
import { AlarmTriggerTemplateService } from './alarm-trigger-template.service';

@Component({
  templateUrl: './alarm-trigger-template-delete-dialog.component.html'
})
export class AlarmTriggerTemplateDeleteDialogComponent {
  alarmTriggerTemplate?: IAlarmTriggerTemplate;

  constructor(
    protected alarmTriggerTemplateService: AlarmTriggerTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alarmTriggerTemplateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alarmTriggerTemplateListModification');
      this.activeModal.close();
    });
  }
}
