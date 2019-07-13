import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Round } from 'app/shared/model/round.model';
import { RoundService } from './round.service';
import { RoundComponent } from './round.component';
import { RoundDetailComponent } from './round-detail.component';
import { RoundUpdateComponent } from './round-update.component';
import { RoundDeletePopupComponent } from './round-delete-dialog.component';
import { IRound } from 'app/shared/model/round.model';

@Injectable({ providedIn: 'root' })
export class RoundResolve implements Resolve<IRound> {
  constructor(private service: RoundService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRound> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Round>) => response.ok),
        map((round: HttpResponse<Round>) => round.body)
      );
    }
    return of(new Round());
  }
}

export const roundRoute: Routes = [
  {
    path: '',
    component: RoundComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.round.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RoundDetailComponent,
    resolve: {
      round: RoundResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.round.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RoundUpdateComponent,
    resolve: {
      round: RoundResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.round.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RoundUpdateComponent,
    resolve: {
      round: RoundResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.round.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const roundPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RoundDeletePopupComponent,
    resolve: {
      round: RoundResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.round.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
