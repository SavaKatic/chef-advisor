import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { IngredientModelComponent } from './ingredient-model.component';
import { IngredientModelDetailComponent } from './ingredient-model-detail.component';
import { IngredientModelUpdateComponent } from './ingredient-model-update.component';
import { IngredientModelDeleteDialogComponent } from './ingredient-model-delete-dialog.component';
import { ingredientModelRoute } from './ingredient-model.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(ingredientModelRoute)],
  declarations: [
    IngredientModelComponent,
    IngredientModelDetailComponent,
    IngredientModelUpdateComponent,
    IngredientModelDeleteDialogComponent
  ],
  entryComponents: [IngredientModelDeleteDialogComponent]
})
export class ChefadvisorIngredientModelModule {}
