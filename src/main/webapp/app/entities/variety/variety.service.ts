import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVariety } from 'app/shared/model/variety.model';

type EntityResponseType = HttpResponse<IVariety>;
type EntityArrayResponseType = HttpResponse<IVariety[]>;

@Injectable({ providedIn: 'root' })
export class VarietyService {
  public resourceUrl = SERVER_API_URL + 'api/varieties';

  constructor(protected http: HttpClient) {}

  create(variety: IVariety): Observable<EntityResponseType> {
    return this.http.post<IVariety>(this.resourceUrl, variety, { observe: 'response' });
  }

  update(variety: IVariety): Observable<EntityResponseType> {
    return this.http.put<IVariety>(this.resourceUrl, variety, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVariety>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariety[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
