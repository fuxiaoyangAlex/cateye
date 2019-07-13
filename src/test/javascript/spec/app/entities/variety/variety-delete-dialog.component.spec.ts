/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Project2TestModule } from '../../../test.module';
import { VarietyDeleteDialogComponent } from 'app/entities/variety/variety-delete-dialog.component';
import { VarietyService } from 'app/entities/variety/variety.service';

describe('Component Tests', () => {
  describe('Variety Management Delete Component', () => {
    let comp: VarietyDeleteDialogComponent;
    let fixture: ComponentFixture<VarietyDeleteDialogComponent>;
    let service: VarietyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [VarietyDeleteDialogComponent]
      })
        .overrideTemplate(VarietyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VarietyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VarietyService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
