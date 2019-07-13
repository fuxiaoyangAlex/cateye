import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  CollectComponent,
  CollectDetailComponent,
  CollectUpdateComponent,
  CollectDeletePopupComponent,
  CollectDeleteDialogComponent,
  collectRoute,
  collectPopupRoute
} from './';

const ENTITY_STATES = [...collectRoute, ...collectPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CollectComponent,
    CollectDetailComponent,
    CollectUpdateComponent,
    CollectDeleteDialogComponent,
    CollectDeletePopupComponent
  ],
  entryComponents: [CollectComponent, CollectUpdateComponent, CollectDeleteDialogComponent, CollectDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2CollectModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
