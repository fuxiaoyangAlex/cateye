/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { CollectDetailComponent } from 'app/entities/collect/collect-detail.component';
import { Collect } from 'app/shared/model/collect.model';

describe('Component Tests', () => {
  describe('Collect Management Detail Component', () => {
    let comp: CollectDetailComponent;
    let fixture: ComponentFixture<CollectDetailComponent>;
    const route = ({ data: of({ collect: new Collect(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [CollectDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CollectDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CollectDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.collect).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
