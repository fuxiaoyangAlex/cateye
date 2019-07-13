import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  VarietyComponent,
  VarietyDetailComponent,
  VarietyUpdateComponent,
  VarietyDeletePopupComponent,
  VarietyDeleteDialogComponent,
  varietyRoute,
  varietyPopupRoute
} from './';

const ENTITY_STATES = [...varietyRoute, ...varietyPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VarietyComponent,
    VarietyDetailComponent,
    VarietyUpdateComponent,
    VarietyDeleteDialogComponent,
    VarietyDeletePopupComponent
  ],
  entryComponents: [VarietyComponent, VarietyUpdateComponent, VarietyDeleteDialogComponent, VarietyDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2VarietyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
