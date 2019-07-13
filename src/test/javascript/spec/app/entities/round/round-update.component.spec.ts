/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { RoundUpdateComponent } from 'app/entities/round/round-update.component';
import { RoundService } from 'app/entities/round/round.service';
import { Round } from 'app/shared/model/round.model';

describe('Component Tests', () => {
  describe('Round Management Update Component', () => {
    let comp: RoundUpdateComponent;
    let fixture: ComponentFixture<RoundUpdateComponent>;
    let service: RoundService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [RoundUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RoundUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoundUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RoundService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Round(123);
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
        const entity = new Round();
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
