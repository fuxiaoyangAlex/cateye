import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlay } from 'app/shared/model/play.model';

type EntityResponseType = HttpResponse<IPlay>;
type EntityArrayResponseType = HttpResponse<IPlay[]>;

@Injectable({ providedIn: 'root' })
export class PlayService {
  public resourceUrl = SERVER_API_URL + 'api/plays';

  constructor(protected http: HttpClient) {}

  create(play: IPlay): Observable<EntityResponseType> {
    return this.http.post<IPlay>(this.resourceUrl, play, { observe: 'response' });
  }

  update(play: IPlay): Observable<EntityResponseType> {
    return this.http.put<IPlay>(this.resourceUrl, play, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
