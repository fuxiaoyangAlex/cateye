import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRound, Round } from 'app/shared/model/round.model';
import { RoundService } from './round.service';
import { ICinema } from 'app/shared/model/cinema.model';
import { CinemaService } from 'app/entities/cinema';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';

@Component({
  selector: 'jhi-round-update',
  templateUrl: './round-update.component.html'
})
export class RoundUpdateComponent implements OnInit {
  isSaving: boolean;

  cinemas: ICinema[];

  movies: IMovie[];

  editForm = this.fb.group({
    id: [],
    time: [],
    duration: [],
    price: [],
    cinemaId: [],
    movieId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected roundService: RoundService,
    protected cinemaService: CinemaService,
    protected movieService: MovieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ round }) => {
      this.updateForm(round);
    });
    this.cinemaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICinema[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICinema[]>) => response.body)
      )
      .subscribe((res: ICinema[]) => (this.cinemas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.movieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMovie[]>) => response.body)
      )
      .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(round: IRound) {
    this.editForm.patchValue({
      id: round.id,
      time: round.time != null ? round.time.format(DATE_TIME_FORMAT) : null,
      duration: round.duration,
      price: round.price,
      cinemaId: round.cinemaId,
      movieId: round.movieId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const round = this.createFromForm();
    if (round.id !== undefined) {
      this.subscribeToSaveResponse(this.roundService.update(round));
    } else {
      this.subscribeToSaveResponse(this.roundService.create(round));
    }
  }

  private createFromForm(): IRound {
    return {
      ...new Round(),
      id: this.editForm.get(['id']).value,
      time: this.editForm.get(['time']).value != null ? moment(this.editForm.get(['time']).value, DATE_TIME_FORMAT) : undefined,
      duration: this.editForm.get(['duration']).value,
      price: this.editForm.get(['price']).value,
      cinemaId: this.editForm.get(['cinemaId']).value,
      movieId: this.editForm.get(['movieId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRound>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCinemaById(index: number, item: ICinema) {
    return item.id;
  }

  trackMovieById(index: number, item: IMovie) {
    return item.id;
  }
}
