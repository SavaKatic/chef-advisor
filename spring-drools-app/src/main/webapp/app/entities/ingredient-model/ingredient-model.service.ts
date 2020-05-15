import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIngredientModel } from 'app/shared/model/ingredient-model.model';

type EntityResponseType = HttpResponse<IIngredientModel>;
type EntityArrayResponseType = HttpResponse<IIngredientModel[]>;

@Injectable({ providedIn: 'root' })
export class IngredientModelService {
  public resourceUrl = SERVER_API_URL + 'api/ingredient-models';

  constructor(protected http: HttpClient) {}

  create(ingredientModel: IIngredientModel): Observable<EntityResponseType> {
    return this.http.post<IIngredientModel>(this.resourceUrl, ingredientModel, { observe: 'response' });
  }

  update(ingredientModel: IIngredientModel): Observable<EntityResponseType> {
    return this.http.put<IIngredientModel>(this.resourceUrl, ingredientModel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIngredientModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIngredientModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
