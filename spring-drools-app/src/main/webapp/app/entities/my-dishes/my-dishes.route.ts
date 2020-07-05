import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDish, Dish } from 'app/shared/model/dish.model';
import { MyDishesService } from './my-dishes.service';
import { MyDishesComponent } from './my-dishes.component';


@Injectable({ providedIn: 'root' })
export class DishResolve implements Resolve<IDish> {
  constructor(private service: MyDishesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDish> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dish: HttpResponse<Dish>) => {
          if (dish.body) {
            return of(dish.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dish());
  }
}

export const myDishesRoute: Routes = [
  {
    path: '',
    component: MyDishesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Dishes'
    },
    canActivate: [UserRouteAccessService]
  }
];
