import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICollect } from 'app/shared/model/collect.model';

type EntityResponseType = HttpResponse<ICollect>;
type EntityArrayResponseType = HttpResponse<ICollect[]>;

@Injectable({ providedIn: 'root' })
export class CollectService {
  public resourceUrl = SERVER_API_URL + 'api/collects';

  constructor(protected http: HttpClient) {}

  create(collect: ICollect): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collect);
    return this.http
      .post<ICollect>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(collect: ICollect): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(collect);
    return this.http
      .put<ICollect>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICollect>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICollect[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(collect: ICollect): ICollect {
    const copy: ICollect = Object.assign({}, collect, {
      date: collect.date != null && collect.date.isValid() ? collect.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((collect: ICollect) => {
        collect.date = collect.date != null ? moment(collect.date) : null;
      });
    }
    return res;
  }
}
