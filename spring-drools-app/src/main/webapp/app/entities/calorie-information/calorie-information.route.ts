import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { CalorieInformationFormComponent } from './calorie-information-form.component';

export const calorieInformationRoute: Routes = [
  {
    path: '',
    component: CalorieInformationFormComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  }
];
