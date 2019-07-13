import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  CinemaComponent,
  CinemaDetailComponent,
  CinemaUpdateComponent,
  CinemaDeletePopupComponent,
  CinemaDeleteDialogComponent,
  cinemaRoute,
  cinemaPopupRoute
} from './';

const ENTITY_STATES = [...cinemaRoute, ...cinemaPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CinemaComponent, CinemaDetailComponent, CinemaUpdateComponent, CinemaDeleteDialogComponent, CinemaDeletePopupComponent],
  entryComponents: [CinemaComponent, CinemaUpdateComponent, CinemaDeleteDialogComponent, CinemaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2CinemaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
