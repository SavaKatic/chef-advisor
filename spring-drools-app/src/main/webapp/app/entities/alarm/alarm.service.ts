import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlarm } from 'app/shared/model/alarm.model';

type EntityResponseType = HttpResponse<IAlarm>;
type EntityArrayResponseType = HttpResponse<IAlarm[]>;

@Injectable({ providedIn: 'root' })
export class AlarmService {
  public resourceUrl = SERVER_API_URL + 'api/alarms';

  constructor(protected http: HttpClient) {}

  create(alarm: IAlarm): Observable<EntityResponseType> {
    return this.http.post<IAlarm>(this.resourceUrl, alarm, { observe: 'response' });
  }

  update(alarm: IAlarm): Observable<EntityResponseType> {
    return this.http.put<IAlarm>(this.resourceUrl, alarm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlarm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlarm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
