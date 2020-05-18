import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDishType, DishType } from 'app/shared/model/dish-type.model';
import { DishTypeService } from './dish-type.service';
import { DishTypeComponent } from './dish-type.component';
import { DishTypeDetailComponent } from './dish-type-detail.component';
import { DishTypeUpdateComponent } from './dish-type-update.component';

@Injectable({ providedIn: 'root' })
export class DishTypeResolve implements Resolve<IDishType> {
  constructor(private service: DishTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDishType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dishType: HttpResponse<DishType>) => {
          if (dishType.body) {
            return of(dishType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DishType());
  }
}

export const dishTypeRoute: Routes = [
  {
    path: '',
    component: DishTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DishTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DishTypeDetailComponent,
    resolve: {
      dishType: DishTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DishTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DishTypeUpdateComponent,
    resolve: {
      dishType: DishTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DishTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DishTypeUpdateComponent,
    resolve: {
      dishType: DishTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DishTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
