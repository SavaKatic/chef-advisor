import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from './unit-type.service';

@Component({
  templateUrl: './unit-type-delete-dialog.component.html'
})
export class UnitTypeDeleteDialogComponent {
  unitType?: IUnitType;

  constructor(protected unitTypeService: UnitTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.unitTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('unitTypeListModification');
      this.activeModal.close();
    });
  }
}
