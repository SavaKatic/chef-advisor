import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';
import { SpamDetectionTemplateService } from './spam-detection-template.service';
import { SpamDetectionTemplateDeleteDialogComponent } from './spam-detection-template-delete-dialog.component';

@Component({
  selector: 'jhi-spam-detection-template',
  templateUrl: './spam-detection-template.component.html'
})
export class SpamDetectionTemplateComponent implements OnInit, OnDestroy {
  spamDetectionTemplates?: ISpamDetectionTemplate[];
  eventSubscriber?: Subscription;

  constructor(
    protected spamDetectionTemplateService: SpamDetectionTemplateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.spamDetectionTemplateService
      .query()
      .subscribe((res: HttpResponse<ISpamDetectionTemplate[]>) => (this.spamDetectionTemplates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpamDetectionTemplates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpamDetectionTemplate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSpamDetectionTemplates(): void {
    this.eventSubscriber = this.eventManager.subscribe('spamDetectionTemplateListModification', () => this.loadAll());
  }

  delete(spamDetectionTemplate: ISpamDetectionTemplate): void {
    const modalRef = this.modalService.open(SpamDetectionTemplateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.spamDetectionTemplate = spamDetectionTemplate;
  }
}
