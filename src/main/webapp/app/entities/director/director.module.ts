import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  DirectorComponent,
  DirectorDetailComponent,
  DirectorUpdateComponent,
  DirectorDeletePopupComponent,
  DirectorDeleteDialogComponent,
  directorRoute,
  directorPopupRoute
} from './';

const ENTITY_STATES = [...directorRoute, ...directorPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DirectorComponent,
    DirectorDetailComponent,
    DirectorUpdateComponent,
    DirectorDeleteDialogComponent,
    DirectorDeletePopupComponent
  ],
  entryComponents: [DirectorComponent, DirectorUpdateComponent, DirectorDeleteDialogComponent, DirectorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2DirectorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
