import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

type EntityResponseType = HttpResponse<IAlarmTriggerTemplate>;
type EntityArrayResponseType = HttpResponse<IAlarmTriggerTemplate[]>;

@Injectable({ providedIn: 'root' })
export class AlarmTriggerTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/alarm-trigger-templates';

  constructor(protected http: HttpClient) {}

  create(alarmTriggerTemplate: IAlarmTriggerTemplate): Observable<EntityResponseType> {
    return this.http.post<IAlarmTriggerTemplate>(this.resourceUrl, alarmTriggerTemplate, { observe: 'response' });
  }

  update(alarmTriggerTemplate: IAlarmTriggerTemplate): Observable<EntityResponseType> {
    return this.http.put<IAlarmTriggerTemplate>(this.resourceUrl, alarmTriggerTemplate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlarmTriggerTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlarmTriggerTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
