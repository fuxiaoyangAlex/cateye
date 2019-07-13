import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRound } from 'app/shared/model/round.model';

type EntityResponseType = HttpResponse<IRound>;
type EntityArrayResponseType = HttpResponse<IRound[]>;

@Injectable({ providedIn: 'root' })
export class RoundService {
  public resourceUrl = SERVER_API_URL + 'api/rounds';

  constructor(protected http: HttpClient) {}

  create(round: IRound): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(round);
    return this.http
      .post<IRound>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(round: IRound): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(round);
    return this.http
      .put<IRound>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRound>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRound[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(round: IRound): IRound {
    const copy: IRound = Object.assign({}, round, {
      time: round.time != null && round.time.isValid() ? round.time.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time != null ? moment(res.body.time) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((round: IRound) => {
        round.time = round.time != null ? moment(round.time) : null;
      });
    }
    return res;
  }
}
