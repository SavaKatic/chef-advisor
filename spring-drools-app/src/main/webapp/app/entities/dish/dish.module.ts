import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { DishComponent } from './dish.component';
import { DishDetailComponent } from './dish-detail.component';
import { DishUpdateComponent } from './dish-update.component';
import { DishDeleteDialogComponent } from './dish-delete-dialog.component';
import { dishRoute } from './dish.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(dishRoute)],
  declarations: [DishComponent, DishDetailComponent, DishUpdateComponent, DishDeleteDialogComponent],
  entryComponents: [DishDeleteDialogComponent]
})
export class ChefadvisorDishModule {}
