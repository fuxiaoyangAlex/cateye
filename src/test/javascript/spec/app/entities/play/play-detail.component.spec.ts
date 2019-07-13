/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Project2TestModule } from '../../../test.module';
import { PlayDetailComponent } from 'app/entities/play/play-detail.component';
import { Play } from 'app/shared/model/play.model';

describe('Component Tests', () => {
  describe('Play Management Detail Component', () => {
    let comp: PlayDetailComponent;
    let fixture: ComponentFixture<PlayDetailComponent>;
    const route = ({ data: of({ play: new Play(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Project2TestModule],
        declarations: [PlayDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlayDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.play).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
