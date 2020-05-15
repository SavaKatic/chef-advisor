import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDishType } from 'app/shared/model/dish-type.model';
import { DishTypeService } from './dish-type.service';

@Component({
  templateUrl: './dish-type-delete-dialog.component.html'
})
export class DishTypeDeleteDialogComponent {
  dishType?: IDishType;

  constructor(protected dishTypeService: DishTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dishTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dishTypeListModification');
      this.activeModal.close();
    });
  }
}
