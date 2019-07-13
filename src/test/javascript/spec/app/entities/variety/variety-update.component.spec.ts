/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { VarietyUpdateComponent } from 'app/entities/variety/variety-update.component';
import { VarietyService } from 'app/entities/variety/variety.service';
import { Variety } from 'app/shared/model/variety.model';

describe('Component Tests', () => {
  describe('Variety Management Update Component', () => {
    let comp: VarietyUpdateComponent;
    let fixture: ComponentFixture<VarietyUpdateComponent>;
    let service: VarietyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [VarietyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VarietyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VarietyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VarietyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Variety(123);
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
        const entity = new Variety();
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
