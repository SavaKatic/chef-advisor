import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIngredient, Ingredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from './ingredient.service';
import { IngredientComponent } from './ingredient.component';
import { IngredientDetailComponent } from './ingredient-detail.component';
import { IngredientUpdateComponent } from './ingredient-update.component';
import { SearchDishesService } from '../search-dishes/search-dishes.service';

@Injectable({ providedIn: 'root' })
export class IngredientResolve implements Resolve<IIngredient> {
  constructor(private service: IngredientService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIngredient> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ingredient: HttpResponse<Ingredient>) => {
          if (ingredient.body) {
            return of(ingredient.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ingredient());
  }
}

@Injectable({ providedIn: 'root' })
export class FridgeResolve implements Resolve<IIngredient[]> {
  constructor(private service: IngredientService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): any {
    return this.service.getFridge().pipe(
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
}


export const ingredientRoute: Routes = [
  {
    path: '',
    component: IngredientComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IngredientDetailComponent,
    resolve: {
      ingredient: IngredientResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IngredientUpdateComponent,
    resolve: {
      ingredient: IngredientResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IngredientUpdateComponent,
    resolve: {
      ingredient: IngredientResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'fridge',
    component: IngredientComponent,
    resolve: {
      ingredient: FridgeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ingredients'
    },
    canActivate: [UserRouteAccessService]
  }
];
