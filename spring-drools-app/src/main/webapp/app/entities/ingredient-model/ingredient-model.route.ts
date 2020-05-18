import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIngredientModel, IngredientModel } from 'app/shared/model/ingredient-model.model';
import { IngredientModelService } from './ingredient-model.service';
import { IngredientModelComponent } from './ingredient-model.component';
import { IngredientModelDetailComponent } from './ingredient-model-detail.component';
import { IngredientModelUpdateComponent } from './ingredient-model-update.component';

@Injectable({ providedIn: 'root' })
export class IngredientModelResolve implements Resolve<IIngredientModel> {
  constructor(private service: IngredientModelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredientModel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ingredientModel: HttpResponse<IngredientModel>) => {
          if (ingredientModel.body) {
            return of(ingredientModel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IngredientModel());
  }
}

export const ingredientModelRoute: Routes = [
  {
    path: '',
    component: IngredientModelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'IngredientModels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IngredientModelDetailComponent,
    resolve: {
      ingredientModel: IngredientModelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientModels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IngredientModelUpdateComponent,
    resolve: {
      ingredientModel: IngredientModelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientModels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IngredientModelUpdateComponent,
    resolve: {
      ingredientModel: IngredientModelResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IngredientModels'
    },
    canActivate: [UserRouteAccessService]
  }
];
