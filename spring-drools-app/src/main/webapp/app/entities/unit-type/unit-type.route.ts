import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUnitType, UnitType } from 'app/shared/model/unit-type.model';
import { UnitTypeService } from './unit-type.service';
import { UnitTypeComponent } from './unit-type.component';
import { UnitTypeDetailComponent } from './unit-type-detail.component';
import { UnitTypeUpdateComponent } from './unit-type-update.component';

@Injectable({ providedIn: 'root' })
export class UnitTypeResolve implements Resolve<IUnitType> {
  constructor(private service: UnitTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUnitType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((unitType: HttpResponse<UnitType>) => {
          if (unitType.body) {
            return of(unitType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UnitType());
  }
}

export const unitTypeRoute: Routes = [
  {
    path: '',
    component: UnitTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UnitTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnitTypeDetailComponent,
    resolve: {
      unitType: UnitTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UnitTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitTypeUpdateComponent,
    resolve: {
      unitType: UnitTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UnitTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitTypeUpdateComponent,
    resolve: {
      unitType: UnitTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UnitTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
