import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICinema } from 'app/shared/model/cinema.model';

type EntityResponseType = HttpResponse<ICinema>;
type EntityArrayResponseType = HttpResponse<ICinema[]>;

@Injectable({ providedIn: 'root' })
export class CinemaService {
  public resourceUrl = SERVER_API_URL + 'api/cinemas';

  constructor(protected http: HttpClient) {}

  create(cinema: ICinema): Observable<EntityResponseType> {
    return this.http.post<ICinema>(this.resourceUrl, cinema, { observe: 'response' });
  }

  update(cinema: ICinema): Observable<EntityResponseType> {
    return this.http.put<ICinema>(this.resourceUrl, cinema, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICinema>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICinema[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
