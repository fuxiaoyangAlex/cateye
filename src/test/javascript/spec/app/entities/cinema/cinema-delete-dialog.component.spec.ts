/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Project2TestModule } from '../../../test.module';
import { CinemaDeleteDialogComponent } from 'app/entities/cinema/cinema-delete-dialog.component';
import { CinemaService } from 'app/entities/cinema/cinema.service';

describe('Component Tests', () => {
  describe('Cinema Management Delete Component', () => {
    let comp: CinemaDeleteDialogComponent;
    let fixture: ComponentFixture<CinemaDeleteDialogComponent>;
    let service: CinemaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [CinemaDeleteDialogComponent]
      })
        .overrideTemplate(CinemaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CinemaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CinemaService);
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
