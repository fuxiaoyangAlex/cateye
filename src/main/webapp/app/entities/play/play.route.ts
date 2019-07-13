import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Play } from 'app/shared/model/play.model';
import { PlayService } from './play.service';
import { PlayComponent } from './play.component';
import { PlayDetailComponent } from './play-detail.component';
import { PlayUpdateComponent } from './play-update.component';
import { PlayDeletePopupComponent } from './play-delete-dialog.component';
import { IPlay } from 'app/shared/model/play.model';

@Injectable({ providedIn: 'root' })
export class PlayResolve implements Resolve<IPlay> {
  constructor(private service: PlayService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlay> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Play>) => response.ok),
        map((play: HttpResponse<Play>) => play.body)
      );
    }
    return of(new Play());
  }
}

export const playRoute: Routes = [
  {
    path: '',
    component: PlayComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.play.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlayDetailComponent,
    resolve: {
      play: PlayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.play.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlayUpdateComponent,
    resolve: {
      play: PlayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.play.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlayUpdateComponent,
    resolve: {
      play: PlayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.play.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const playPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlayDeletePopupComponent,
    resolve: {
      play: PlayResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.play.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
