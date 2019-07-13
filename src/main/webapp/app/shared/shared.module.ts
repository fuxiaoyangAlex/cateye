import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Project2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Project2SharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [Project2SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Project2SharedModule {
  static forRoot() {
    return {
      ngModule: Project2SharedModule
    };
  }
}
