/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { CinemaDetailComponent } from 'app/entities/cinema/cinema-detail.component';
import { Cinema } from 'app/shared/model/cinema.model';

describe('Component Tests', () => {
  describe('Cinema Management Detail Component', () => {
    let comp: CinemaDetailComponent;
    let fixture: ComponentFixture<CinemaDetailComponent>;
    const route = ({ data: of({ cinema: new Cinema(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [CinemaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CinemaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CinemaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cinema).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
