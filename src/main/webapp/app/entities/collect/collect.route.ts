import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Collect } from 'app/shared/model/collect.model';
import { CollectService } from './collect.service';
import { CollectComponent } from './collect.component';
import { CollectDetailComponent } from './collect-detail.component';
import { CollectUpdateComponent } from './collect-update.component';
import { CollectDeletePopupComponent } from './collect-delete-dialog.component';
import { ICollect } from 'app/shared/model/collect.model';

@Injectable({ providedIn: 'root' })
export class CollectResolve implements Resolve<ICollect> {
  constructor(private service: CollectService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICollect> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Collect>) => response.ok),
        map((collect: HttpResponse<Collect>) => collect.body)
      );
    }
    return of(new Collect());
  }
}

export const collectRoute: Routes = [
  {
    path: '',
    component: CollectComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.collect.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CollectDetailComponent,
    resolve: {
      collect: CollectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.collect.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CollectUpdateComponent,
    resolve: {
      collect: CollectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.collect.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CollectUpdateComponent,
    resolve: {
      collect: CollectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.collect.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const collectPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CollectDeletePopupComponent,
    resolve: {
      collect: CollectResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.collect.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
