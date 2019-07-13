import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  PlayComponent,
  PlayDetailComponent,
  PlayUpdateComponent,
  PlayDeletePopupComponent,
  PlayDeleteDialogComponent,
  playRoute,
  playPopupRoute
} from './';

const ENTITY_STATES = [...playRoute, ...playPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PlayComponent, PlayDetailComponent, PlayUpdateComponent, PlayDeleteDialogComponent, PlayDeletePopupComponent],
  entryComponents: [PlayComponent, PlayUpdateComponent, PlayDeleteDialogComponent, PlayDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2PlayModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
