import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Project2SharedModule } from 'app/shared';
import {
  ActorComponent,
  ActorDetailComponent,
  ActorUpdateComponent,
  ActorDeletePopupComponent,
  ActorDeleteDialogComponent,
  actorRoute,
  actorPopupRoute
} from './';

const ENTITY_STATES = [...actorRoute, ...actorPopupRoute];

@NgModule({
  imports: [Project2SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ActorComponent, ActorDetailComponent, ActorUpdateComponent, ActorDeleteDialogComponent, ActorDeletePopupComponent],
  entryComponents: [ActorComponent, ActorUpdateComponent, ActorDeleteDialogComponent, ActorDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2ActorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
