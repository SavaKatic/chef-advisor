import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISpamDetectionTemplate, SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';
import { SpamDetectionTemplateService } from './spam-detection-template.service';
import { SpamDetectionTemplateComponent } from './spam-detection-template.component';
import { SpamDetectionTemplateDetailComponent } from './spam-detection-template-detail.component';
import { SpamDetectionTemplateUpdateComponent } from './spam-detection-template-update.component';

@Injectable({ providedIn: 'root' })
export class SpamDetectionTemplateResolve implements Resolve<ISpamDetectionTemplate> {
  constructor(private service: SpamDetectionTemplateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISpamDetectionTemplate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((spamDetectionTemplate: HttpResponse<SpamDetectionTemplate>) => {
          if (spamDetectionTemplate.body) {
            return of(spamDetectionTemplate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SpamDetectionTemplate());
  }
}

export const spamDetectionTemplateRoute: Routes = [
  {
    path: '',
    component: SpamDetectionTemplateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SpamDetectionTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpamDetectionTemplateDetailComponent,
    resolve: {
      spamDetectionTemplate: SpamDetectionTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SpamDetectionTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpamDetectionTemplateUpdateComponent,
    resolve: {
      spamDetectionTemplate: SpamDetectionTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SpamDetectionTemplates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpamDetectionTemplateUpdateComponent,
    resolve: {
      spamDetectionTemplate: SpamDetectionTemplateResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SpamDetectionTemplates'
    },
    canActivate: [UserRouteAccessService]
  }
];
