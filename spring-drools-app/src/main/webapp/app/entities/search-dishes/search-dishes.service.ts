import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDish } from 'app/shared/model/dish.model';
import { IIngredient, Ingredient } from 'app/shared/model/ingredient.model';

type EntityResponseType = HttpResponse<IDish>;
type EntityArrayResponseType = HttpResponse<IDish[]>;

@Injectable({ providedIn: 'root' })
export class SearchDishesService {
  public resourceUrl = SERVER_API_URL + 'api/dishes';

  constructor(protected http: HttpClient) {}

  create(dish: IDish): Observable<EntityResponseType> {
    return this.http.post<IDish>(this.resourceUrl, dish, { observe: 'response' });
  }

  update(dish: IDish): Observable<EntityResponseType> {
    return this.http.put<IDish>(this.resourceUrl, dish, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDish>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDish[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(query: string): Observable<EntityArrayResponseType> {
    return this.http.get<IDish[]>(`api/search-dishes${query}`, { observe: 'response' });
  }

  choose(dish: IDish): Observable<{}> {
    return this.http.post<{}>(`${this.resourceUrl}/addToHistory`, dish, { observe: 'response'});
  }

  findMissing(dishId: number): Observable<HttpResponse<IIngredient[]>> {
    return this.http.get<IIngredient[]>(`api/search-ingredients?dishId=${dishId}`, { observe: 'response' });
  }
}
