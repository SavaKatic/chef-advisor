import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { CalorieConfigurationComponent } from './calorie-configuration.component';
import { CalorieConfigurationDetailComponent } from './calorie-configuration-detail.component';
import { CalorieConfigurationUpdateComponent } from './calorie-configuration-update.component';
import { CalorieConfigurationDeleteDialogComponent } from './calorie-configuration-delete-dialog.component';
import { calorieConfigurationRoute } from './calorie-configuration.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(calorieConfigurationRoute)],
  declarations: [
    CalorieConfigurationComponent,
    CalorieConfigurationDetailComponent,
    CalorieConfigurationUpdateComponent,
    CalorieConfigurationDeleteDialogComponent
  ],
  entryComponents: [CalorieConfigurationDeleteDialogComponent]
})
export class ChefadvisorCalorieConfigurationModule {}
