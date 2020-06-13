import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { ICalorieInformation } from 'app/shared/model/calorie-information.model';

type EntityResponseType = HttpResponse<ICalorieInformation>;
type EntityArrayResponseType = HttpResponse<ICalorieInformation[]>;

@Injectable({ providedIn: 'root' })
export class CalorieInformationService {
  public resourceUrl = SERVER_API_URL + 'api/users';

  constructor(protected http: HttpClient) {}

  setCaloricIntake(caloricInformation: ICalorieInformation): Observable<EntityResponseType> {
    return this.http.put<ICalorieInformation>(this.resourceUrl + '/calories', caloricInformation, { observe: 'response' });
  }
}
