import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { UnitTypeComponent } from './unit-type.component';
import { UnitTypeDetailComponent } from './unit-type-detail.component';
import { UnitTypeUpdateComponent } from './unit-type-update.component';
import { UnitTypeDeleteDialogComponent } from './unit-type-delete-dialog.component';
import { unitTypeRoute } from './unit-type.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(unitTypeRoute)],
  declarations: [UnitTypeComponent, UnitTypeDetailComponent, UnitTypeUpdateComponent, UnitTypeDeleteDialogComponent],
  entryComponents: [UnitTypeDeleteDialogComponent]
})
export class ChefadvisorUnitTypeModule {}
