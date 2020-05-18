import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { DishTypeComponent } from './dish-type.component';
import { DishTypeDetailComponent } from './dish-type-detail.component';
import { DishTypeUpdateComponent } from './dish-type-update.component';
import { DishTypeDeleteDialogComponent } from './dish-type-delete-dialog.component';
import { dishTypeRoute } from './dish-type.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(dishTypeRoute)],
  declarations: [DishTypeComponent, DishTypeDetailComponent, DishTypeUpdateComponent, DishTypeDeleteDialogComponent],
  entryComponents: [DishTypeDeleteDialogComponent]
})
export class ChefadvisorDishTypeModule {}
