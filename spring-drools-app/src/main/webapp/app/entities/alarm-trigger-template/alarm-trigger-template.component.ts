import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';
import { AlarmTriggerTemplateService } from './alarm-trigger-template.service';
import { AlarmTriggerTemplateDeleteDialogComponent } from './alarm-trigger-template-delete-dialog.component';

@Component({
  selector: 'jhi-alarm-trigger-template',
  templateUrl: './alarm-trigger-template.component.html'
})
export class AlarmTriggerTemplateComponent implements OnInit, OnDestroy {
  alarmTriggerTemplates?: IAlarmTriggerTemplate[];
  eventSubscriber?: Subscription;

  constructor(
    protected alarmTriggerTemplateService: AlarmTriggerTemplateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.alarmTriggerTemplateService
      .query()
      .subscribe((res: HttpResponse<IAlarmTriggerTemplate[]>) => (this.alarmTriggerTemplates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlarmTriggerTemplates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlarmTriggerTemplate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlarmTriggerTemplates(): void {
    this.eventSubscriber = this.eventManager.subscribe('alarmTriggerTemplateListModification', () => this.loadAll());
  }

  delete(alarmTriggerTemplate: IAlarmTriggerTemplate): void {
    const modalRef = this.modalService.open(AlarmTriggerTemplateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alarmTriggerTemplate = alarmTriggerTemplate;
  }
}
