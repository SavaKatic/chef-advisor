import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlarmTriggerTemplate, AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';
import { AlarmTriggerTemplateService } from './alarm-trigger-template.service';
import { AlarmTriggerTemplateComponent } from './alarm-trigger-template.component';
import { AlarmTriggerTemplateDetailComponent } from './alarm-trigger-template-detail.component';
import { AlarmTriggerTemplateUpdateComponent } from './alarm-trigger-template-update.component';

@Injectable({ providedIn: 'root' })
export class AlarmTriggerTemplateResolve implements Resolve<IAlarmTriggerTemplate> {
  constructor(private service: AlarmTriggerTemplateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlarmTriggerTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alarmTriggerTemplate: HttpResponse<AlarmTriggerTemplate>) => {
          if (alarmTriggerTemplate.body) {
            return of(alarmTriggerTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AlarmTriggerTemplate());
  }
}

export const alarmTriggerTemplateRoute: Routes = [
  {
    path: '',
    component: AlarmTriggerTemplateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AlarmTriggerTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlarmTriggerTemplateDetailComponent,
    resolve: {
      alarmTriggerTemplate: AlarmTriggerTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AlarmTriggerTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlarmTriggerTemplateUpdateComponent,
    resolve: {
      alarmTriggerTemplate: AlarmTriggerTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AlarmTriggerTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlarmTriggerTemplateUpdateComponent,
    resolve: {
      alarmTriggerTemplate: AlarmTriggerTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AlarmTriggerTemplates'
    },
    canActivate: [UserRouteAccessService]
  }
];
