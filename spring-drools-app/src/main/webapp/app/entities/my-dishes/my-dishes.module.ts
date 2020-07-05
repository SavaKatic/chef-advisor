import { NgModule } from '@angular/core';
import { MyDishesComponent } from './my-dishes.component';
import { RouterModule } from '@angular/router';
import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { myDishesRoute } from './my-dishes.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(myDishesRoute)],
  declarations: [MyDishesComponent]
})
export class ChefadvisorMyDishesModule { }
