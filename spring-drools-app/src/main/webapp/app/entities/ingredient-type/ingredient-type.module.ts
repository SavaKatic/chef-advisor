import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { IngredientTypeComponent } from './ingredient-type.component';
import { IngredientTypeDetailComponent } from './ingredient-type-detail.component';
import { IngredientTypeUpdateComponent } from './ingredient-type-update.component';
import { IngredientTypeDeleteDialogComponent } from './ingredient-type-delete-dialog.component';
import { ingredientTypeRoute } from './ingredient-type.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(ingredientTypeRoute)],
  declarations: [
    IngredientTypeComponent,
    IngredientTypeDetailComponent,
    IngredientTypeUpdateComponent,
    IngredientTypeDeleteDialogComponent
  ],
  entryComponents: [IngredientTypeDeleteDialogComponent]
})
export class ChefadvisorIngredientTypeModule {}
