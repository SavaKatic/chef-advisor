import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIngredientType, IngredientType } from 'app/shared/model/ingredient-type.model';
import { IngredientTypeService } from './ingredient-type.service';
import { IngredientTypeComponent } from './ingredient-type.component';
import { IngredientTypeDetailComponent } from './ingredient-type-detail.component';
import { IngredientTypeUpdateComponent } from './ingredient-type-update.component';

@Injectable({ providedIn: 'root' })
export class IngredientTypeResolve implements Resolve<IIngredientType> {
  constructor(private service: IngredientTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredientType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ingredientType: HttpResponse<IngredientType>) => {
          if (ingredientType.body) {
            return of(ingredientType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IngredientType());
  }
}

export const ingredientTypeRoute: Routes = [
  {
    path: '',
    component: IngredientTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IngredientTypeDetailComponent,
    resolve: {
      ingredientType: IngredientTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IngredientTypeUpdateComponent,
    resolve: {
      ingredientType: IngredientTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IngredientTypeUpdateComponent,
    resolve: {
      ingredientType: IngredientTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];
