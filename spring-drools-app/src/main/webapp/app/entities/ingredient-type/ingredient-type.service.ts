import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIngredientType } from 'app/shared/model/ingredient-type.model';

type EntityResponseType = HttpResponse<IIngredientType>;
type EntityArrayResponseType = HttpResponse<IIngredientType[]>;

@Injectable({ providedIn: 'root' })
export class IngredientTypeService {
  public resourceUrl = SERVER_API_URL + 'api/ingredient-types';

  constructor(protected http: HttpClient) {}

  create(ingredientType: IIngredientType): Observable<EntityResponseType> {
    return this.http.post<IIngredientType>(this.resourceUrl, ingredientType, { observe: 'response' });
  }

  update(ingredientType: IIngredientType): Observable<EntityResponseType> {
    return this.http.put<IIngredientType>(this.resourceUrl, ingredientType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIngredientType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIngredientType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
