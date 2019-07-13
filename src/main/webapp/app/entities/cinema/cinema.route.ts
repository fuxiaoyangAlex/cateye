import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cinema } from 'app/shared/model/cinema.model';
import { CinemaService } from './cinema.service';
import { CinemaComponent } from './cinema.component';
import { CinemaDetailComponent } from './cinema-detail.component';
import { CinemaUpdateComponent } from './cinema-update.component';
import { CinemaDeletePopupComponent } from './cinema-delete-dialog.component';
import { ICinema } from 'app/shared/model/cinema.model';

@Injectable({ providedIn: 'root' })
export class CinemaResolve implements Resolve<ICinema> {
  constructor(private service: CinemaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICinema> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cinema>) => response.ok),
        map((cinema: HttpResponse<Cinema>) => cinema.body)
      );
    }
    return of(new Cinema());
  }
}

export const cinemaRoute: Routes = [
  {
    path: '',
    component: CinemaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.cinema.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CinemaDetailComponent,
    resolve: {
      cinema: CinemaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.cinema.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CinemaUpdateComponent,
    resolve: {
      cinema: CinemaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.cinema.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CinemaUpdateComponent,
    resolve: {
      cinema: CinemaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.cinema.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cinemaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CinemaDeletePopupComponent,
    resolve: {
      cinema: CinemaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.cinema.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
