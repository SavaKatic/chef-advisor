import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ChefadvisorSharedModule } from 'app/shared/shared.module';
import { SpamDetectionTemplateComponent } from './spam-detection-template.component';
import { SpamDetectionTemplateDetailComponent } from './spam-detection-template-detail.component';
import { SpamDetectionTemplateUpdateComponent } from './spam-detection-template-update.component';
import { SpamDetectionTemplateDeleteDialogComponent } from './spam-detection-template-delete-dialog.component';
import { spamDetectionTemplateRoute } from './spam-detection-template.route';

@NgModule({
  imports: [ChefadvisorSharedModule, RouterModule.forChild(spamDetectionTemplateRoute)],
  declarations: [
    SpamDetectionTemplateComponent,
    SpamDetectionTemplateDetailComponent,
    SpamDetectionTemplateUpdateComponent,
    SpamDetectionTemplateDeleteDialogComponent
  ],
  entryComponents: [SpamDetectionTemplateDeleteDialogComponent]
})
export class ChefadvisorSpamDetectionTemplateModule {}
