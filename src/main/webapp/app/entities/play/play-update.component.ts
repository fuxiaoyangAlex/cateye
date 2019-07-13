import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPlay, Play } from 'app/shared/model/play.model';
import { PlayService } from './play.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { IActor } from 'app/shared/model/actor.model';
import { ActorService } from 'app/entities/actor';

@Component({
  selector: 'jhi-play-update',
  templateUrl: './play-update.component.html'
})
export class PlayUpdateComponent implements OnInit {
  isSaving: boolean;

  movies: IMovie[];

  actors: IActor[];

  editForm = this.fb.group({
    id: [],
    movieId: [],
    actorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected playService: PlayService,
    protected movieService: MovieService,
    protected actorService: ActorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ play }) => {
      this.updateForm(play);
    });
    this.movieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMovie[]>) => response.body)
      )
      .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.actorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IActor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IActor[]>) => response.body)
      )
      .subscribe((res: IActor[]) => (this.actors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(play: IPlay) {
    this.editForm.patchValue({
      id: play.id,
      movieId: play.movieId,
      actorId: play.actorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const play = this.createFromForm();
    if (play.id !== undefined) {
      this.subscribeToSaveResponse(this.playService.update(play));
    } else {
      this.subscribeToSaveResponse(this.playService.create(play));
    }
  }

  private createFromForm(): IPlay {
    return {
      ...new Play(),
      id: this.editForm.get(['id']).value,
      movieId: this.editForm.get(['movieId']).value,
      actorId: this.editForm.get(['actorId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlay>>) {
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

  trackMovieById(index: number, item: IMovie) {
    return item.id;
  }

  trackActorById(index: number, item: IActor) {
    return item.id;
  }
}
