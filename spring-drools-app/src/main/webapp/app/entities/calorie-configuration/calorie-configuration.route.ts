import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICalorieConfiguration, CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';
import { CalorieConfigurationService } from './calorie-configuration.service';
import { CalorieConfigurationComponent } from './calorie-configuration.component';
import { CalorieConfigurationDetailComponent } from './calorie-configuration-detail.component';
import { CalorieConfigurationUpdateComponent } from './calorie-configuration-update.component';

@Injectable({ providedIn: 'root' })
export class CalorieConfigurationResolve implements Resolve<ICalorieConfiguration> {
  constructor(private service: CalorieConfigurationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICalorieConfiguration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((calorieConfiguration: HttpResponse<CalorieConfiguration>) => {
          if (calorieConfiguration.body) {
            return of(calorieConfiguration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CalorieConfiguration());
  }
}

export const calorieConfigurationRoute: Routes = [
  {
    path: '',
    component: CalorieConfigurationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CalorieConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CalorieConfigurationDetailComponent,
    resolve: {
      calorieConfiguration: CalorieConfigurationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CalorieConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CalorieConfigurationUpdateComponent,
    resolve: {
      calorieConfiguration: CalorieConfigurationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CalorieConfigurations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CalorieConfigurationUpdateComponent,
    resolve: {
      calorieConfiguration: CalorieConfigurationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CalorieConfigurations'
    },
    canActivate: [UserRouteAccessService]
  }
];
