import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDish, Dish } from 'app/shared/model/dish.model';
import { IIngredient, Ingredient } from 'app/shared/model/ingredient.model';
import { SearchDishesComponent } from './search-dishes.component';
import { IngredientComponent } from '../ingredient/ingredient.component';
import { SearchDishesService } from './search-dishes.service';

@Injectable({ providedIn: 'root' })
export class MissingIngredientsResolve implements Resolve<IIngredient[]> {
  constructor(private service: SearchDishesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredient[]> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.findMissing(id).pipe(
        flatMap((ingredients: HttpResponse<Ingredient[]>) => {
          if (ingredients.body) {
            return of(ingredients.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of([]);
  }
}

export const searchDishesRoute: Routes = [
  {
    path: '',
    component: SearchDishesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/missing-ingredients',
    component: IngredientComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
      ingredients: MissingIngredientsResolve
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  }
];
