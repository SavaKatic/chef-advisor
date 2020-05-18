import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientType } from 'app/shared/model/ingredient-type.model';
import { IngredientTypeService } from './ingredient-type.service';

@Component({
  templateUrl: './ingredient-type-delete-dialog.component.html'
})
export class IngredientTypeDeleteDialogComponent {
  ingredientType?: IIngredientType;

  constructor(
    protected ingredientTypeService: IngredientTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ingredientTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ingredientTypeListModification');
      this.activeModal.close();
    });
  }
}
