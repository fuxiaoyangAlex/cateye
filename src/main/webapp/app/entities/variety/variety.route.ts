import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Variety } from 'app/shared/model/variety.model';
import { VarietyService } from './variety.service';
import { VarietyComponent } from './variety.component';
import { VarietyDetailComponent } from './variety-detail.component';
import { VarietyUpdateComponent } from './variety-update.component';
import { VarietyDeletePopupComponent } from './variety-delete-dialog.component';
import { IVariety } from 'app/shared/model/variety.model';

@Injectable({ providedIn: 'root' })
export class VarietyResolve implements Resolve<IVariety> {
  constructor(private service: VarietyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVariety> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Variety>) => response.ok),
        map((variety: HttpResponse<Variety>) => variety.body)
      );
    }
    return of(new Variety());
  }
}

export const varietyRoute: Routes = [
  {
    path: '',
    component: VarietyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'project2App.variety.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VarietyDetailComponent,
    resolve: {
      variety: VarietyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.variety.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VarietyUpdateComponent,
    resolve: {
      variety: VarietyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.variety.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VarietyUpdateComponent,
    resolve: {
      variety: VarietyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.variety.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const varietyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VarietyDeletePopupComponent,
    resolve: {
      variety: VarietyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'project2App.variety.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
