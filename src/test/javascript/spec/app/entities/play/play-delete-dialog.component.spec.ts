/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Project2TestModule } from '../../../test.module';
import { PlayDeleteDialogComponent } from 'app/entities/play/play-delete-dialog.component';
import { PlayService } from 'app/entities/play/play.service';

describe('Component Tests', () => {
  describe('Play Management Delete Component', () => {
    let comp: PlayDeleteDialogComponent;
    let fixture: ComponentFixture<PlayDeleteDialogComponent>;
    let service: PlayService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [PlayDeleteDialogComponent]
      })
        .overrideTemplate(PlayDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayService);
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
