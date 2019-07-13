import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  OrdersComponent,
  OrdersDetailComponent,
  OrdersUpdateComponent,
  OrdersDeletePopupComponent,
  OrdersDeleteDialogComponent,
  ordersRoute,
  ordersPopupRoute
} from './';

const ENTITY_STATES = [...ordersRoute, ...ordersPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OrdersComponent, OrdersDetailComponent, OrdersUpdateComponent, OrdersDeleteDialogComponent, OrdersDeletePopupComponent],
  entryComponents: [OrdersComponent, OrdersUpdateComponent, OrdersDeleteDialogComponent, OrdersDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2OrdersModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
