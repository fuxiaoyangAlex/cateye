import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  MovieComponent,
  MovieDetailComponent,
  MovieUpdateComponent,
  MovieDeletePopupComponent,
  MovieDeleteDialogComponent,
  movieRoute,
  moviePopupRoute
} from './';

const ENTITY_STATES = [...movieRoute, ...moviePopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [MovieComponent, MovieDetailComponent, MovieUpdateComponent, MovieDeleteDialogComponent, MovieDeletePopupComponent],
  entryComponents: [MovieComponent, MovieUpdateComponent, MovieDeleteDialogComponent, MovieDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2MovieModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
