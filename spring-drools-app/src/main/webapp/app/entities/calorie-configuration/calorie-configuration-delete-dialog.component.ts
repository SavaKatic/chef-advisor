import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalorieConfiguration } from 'app/shared/model/calorie-configuration.model';
import { CalorieConfigurationService } from './calorie-configuration.service';

@Component({
  templateUrl: './calorie-configuration-delete-dialog.component.html'
})
export class CalorieConfigurationDeleteDialogComponent {
  calorieConfiguration?: ICalorieConfiguration;

  constructor(
    protected calorieConfigurationService: CalorieConfigurationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.calorieConfigurationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('calorieConfigurationListModification');
      this.activeModal.close();
    });
  }
}
