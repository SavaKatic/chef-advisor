import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngredientModel } from 'app/shared/model/ingredient-model.model';
import { IngredientModelService } from './ingredient-model.service';

@Component({
  templateUrl: './ingredient-model-delete-dialog.component.html'
})
export class IngredientModelDeleteDialogComponent {
  ingredientModel?: IIngredientModel;

  constructor(
    protected ingredientModelService: IngredientModelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ingredientModelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ingredientModelListModification');
      this.activeModal.close();
    });
  }
}
