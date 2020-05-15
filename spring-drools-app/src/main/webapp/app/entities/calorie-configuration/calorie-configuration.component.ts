import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICalorieConfiguration } from 'app/shared/model/calorie-configuration.model';
import { CalorieConfigurationService } from './calorie-configuration.service';
import { CalorieConfigurationDeleteDialogComponent } from './calorie-configuration-delete-dialog.component';

@Component({
  selector: 'jhi-calorie-configuration',
  templateUrl: './calorie-configuration.component.html'
})
export class CalorieConfigurationComponent implements OnInit, OnDestroy {
  calorieConfigurations?: ICalorieConfiguration[];
  eventSubscriber?: Subscription;

  constructor(
    protected calorieConfigurationService: CalorieConfigurationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.calorieConfigurationService
      .query()
      .subscribe((res: HttpResponse<ICalorieConfiguration[]>) => (this.calorieConfigurations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCalorieConfigurations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICalorieConfiguration): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCalorieConfigurations(): void {
    this.eventSubscriber = this.eventManager.subscribe('calorieConfigurationListModification', () => this.loadAll());
  }

  delete(calorieConfiguration: ICalorieConfiguration): void {
    const modalRef = this.modalService.open(CalorieConfigurationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.calorieConfiguration = calorieConfiguration;
  }
}
