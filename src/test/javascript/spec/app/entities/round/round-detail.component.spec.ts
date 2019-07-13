/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { RoundDetailComponent } from 'app/entities/round/round-detail.component';
import { Round } from 'app/shared/model/round.model';

describe('Component Tests', () => {
  describe('Round Management Detail Component', () => {
    let comp: RoundDetailComponent;
    let fixture: ComponentFixture<RoundDetailComponent>;
    const route = ({ data: of({ round: new Round(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [RoundDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RoundDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RoundDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.round).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
