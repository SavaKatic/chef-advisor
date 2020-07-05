import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

type EntityResponseType = HttpResponse<ISpamDetectionTemplate>;
type EntityArrayResponseType = HttpResponse<ISpamDetectionTemplate[]>;

@Injectable({ providedIn: 'root' })
export class SpamDetectionTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/spam-detection-templates';

  constructor(protected http: HttpClient) {}

  create(spamDetectionTemplate: ISpamDetectionTemplate): Observable<EntityResponseType> {
    return this.http.post<ISpamDetectionTemplate>(this.resourceUrl, spamDetectionTemplate, { observe: 'response' });
  }

  update(spamDetectionTemplate: ISpamDetectionTemplate): Observable<EntityResponseType> {
    return this.http.put<ISpamDetectionTemplate>(this.resourceUrl, spamDetectionTemplate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpamDetectionTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpamDetectionTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
