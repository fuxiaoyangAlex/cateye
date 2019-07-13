/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Project2TestModule } from '../../../test.module';
import { RoundDeleteDialogComponent } from 'app/entities/round/round-delete-dialog.component';
import { RoundService } from 'app/entities/round/round.service';

describe('Component Tests', () => {
  describe('Round Management Delete Component', () => {
    let comp: RoundDeleteDialogComponent;
    let fixture: ComponentFixture<RoundDeleteDialogComponent>;
    let service: RoundService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [RoundDeleteDialogComponent]
      })
        .overrideTemplate(RoundDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RoundDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RoundService);
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
