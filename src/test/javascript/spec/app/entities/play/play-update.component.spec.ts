/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { PlayUpdateComponent } from 'app/entities/play/play-update.component';
import { PlayService } from 'app/entities/play/play.service';
import { Play } from 'app/shared/model/play.model';

describe('Component Tests', () => {
  describe('Play Management Update Component', () => {
    let comp: PlayUpdateComponent;
    let fixture: ComponentFixture<PlayUpdateComponent>;
    let service: PlayService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [PlayUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlayUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Play(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Play();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
