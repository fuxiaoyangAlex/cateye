/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { CollectUpdateComponent } from 'app/entities/collect/collect-update.component';
import { CollectService } from 'app/entities/collect/collect.service';
import { Collect } from 'app/shared/model/collect.model';

describe('Component Tests', () => {
  describe('Collect Management Update Component', () => {
    let comp: CollectUpdateComponent;
    let fixture: ComponentFixture<CollectUpdateComponent>;
    let service: CollectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [CollectUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CollectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Collect(123);
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
        const entity = new Collect();
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
