import { NgModule } from '@angular/core';
import { CalorieInformationFormComponent } from './calorie-information-form.component';
import { RouterModule } from '@angular/router';
import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { calorieInformationRoute } from './calorie-information.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(calorieInformationRoute)],
  declarations: [CalorieInformationFormComponent],
  entryComponents: []
})
export class ChefadvisorCalorieInformationModule { }
