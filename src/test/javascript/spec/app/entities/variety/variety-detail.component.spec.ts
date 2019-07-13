/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { VarietyDetailComponent } from 'app/entities/variety/variety-detail.component';
import { Variety } from 'app/shared/model/variety.model';

describe('Component Tests', () => {
  describe('Variety Management Detail Component', () => {
    let comp: VarietyDetailComponent;
    let fixture: ComponentFixture<VarietyDetailComponent>;
    const route = ({ data: of({ variety: new Variety(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [VarietyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VarietyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VarietyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.variety).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
