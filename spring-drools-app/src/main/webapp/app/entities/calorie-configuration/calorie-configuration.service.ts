import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

type EntityResponseType = HttpResponse<ICalorieConfiguration>;
type EntityArrayResponseType = HttpResponse<ICalorieConfiguration[]>;

@Injectable({ providedIn: 'root' })
export class CalorieConfigurationService {
  public resourceUrl = SERVER_API_URL + 'api/calorie-configurations';

  constructor(protected http: HttpClient) {}

  create(calorieConfiguration: ICalorieConfiguration): Observable<EntityResponseType> {
    return this.http.post<ICalorieConfiguration>(this.resourceUrl, calorieConfiguration, { observe: 'response' });
  }

  update(calorieConfiguration: ICalorieConfiguration): Observable<EntityResponseType> {
    return this.http.put<ICalorieConfiguration>(this.resourceUrl, calorieConfiguration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICalorieConfiguration>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICalorieConfiguration[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
