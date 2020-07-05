import { NgModule } from '@angular/core';
import { SearchDishesComponent } from './search-dishes.component';
import { RouterModule } from '@angular/router';
import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { searchDishesRoute } from './search-dishes.route';


@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(searchDishesRoute)],
  declarations: [SearchDishesComponent]
})
export class ChefadvisorSearchDishesModule { }
