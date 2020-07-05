import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { AlarmTriggerTemplateComponent } from './alarm-trigger-template.component';
import { AlarmTriggerTemplateDetailComponent } from './alarm-trigger-template-detail.component';
import { AlarmTriggerTemplateUpdateComponent } from './alarm-trigger-template-update.component';
import { AlarmTriggerTemplateDeleteDialogComponent } from './alarm-trigger-template-delete-dialog.component';
import { alarmTriggerTemplateRoute } from './alarm-trigger-template.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(alarmTriggerTemplateRoute)],
  declarations: [
    AlarmTriggerTemplateComponent,
    AlarmTriggerTemplateDetailComponent,
    AlarmTriggerTemplateUpdateComponent,
    AlarmTriggerTemplateDeleteDialogComponent
  ],
  entryComponents: [AlarmTriggerTemplateDeleteDialogComponent]
})
export class ChefadvisorAlarmTriggerTemplateModule {}
