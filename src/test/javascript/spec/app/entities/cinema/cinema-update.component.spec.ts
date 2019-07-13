/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { CinemaUpdateComponent } from 'app/entities/cinema/cinema-update.component';
import { CinemaService } from 'app/entities/cinema/cinema.service';
import { Cinema } from 'app/shared/model/cinema.model';

describe('Component Tests', () => {
  describe('Cinema Management Update Component', () => {
    let comp: CinemaUpdateComponent;
    let fixture: ComponentFixture<CinemaUpdateComponent>;
    let service: CinemaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [CinemaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CinemaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CinemaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CinemaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cinema(123);
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
        const entity = new Cinema();
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
