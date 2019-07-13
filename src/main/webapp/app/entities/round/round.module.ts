import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  RoundComponent,
  RoundDetailComponent,
  RoundUpdateComponent,
  RoundDeletePopupComponent,
  RoundDeleteDialogComponent,
  roundRoute,
  roundPopupRoute
} from './';

const ENTITY_STATES = [...roundRoute, ...roundPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RoundComponent, RoundDetailComponent, RoundUpdateComponent, RoundDeleteDialogComponent, RoundDeletePopupComponent],
  entryComponents: [RoundComponent, RoundUpdateComponent, RoundDeleteDialogComponent, RoundDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2RoundModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
