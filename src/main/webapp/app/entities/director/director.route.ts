import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Director } from 'app/shared/model/director.model';
import { DirectorService } from './director.service';
import { DirectorComponent } from './director.component';
import { DirectorDetailComponent } from './director-detail.component';
import { DirectorUpdateComponent } from './director-update.component';
import { DirectorDeletePopupComponent } from './director-delete-dialog.component';
import { IDirector } from 'app/shared/model/director.model';

@Injectable({ providedIn: 'root' })
export class DirectorResolve implements Resolve<IDirector> {
  constructor(private service: DirectorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDirector> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Director>) => response.ok),
        map((director: HttpResponse<Director>) => director.body)
      );
    }
    return of(new Director());
  }
}

export const directorRoute: Routes = [
  {
    path: '',
    component: DirectorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.director.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DirectorDetailComponent,
    resolve: {
      director: DirectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.director.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DirectorUpdateComponent,
    resolve: {
      director: DirectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.director.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DirectorUpdateComponent,
    resolve: {
      director: DirectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.director.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const directorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DirectorDeletePopupComponent,
    resolve: {
      director: DirectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.director.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
