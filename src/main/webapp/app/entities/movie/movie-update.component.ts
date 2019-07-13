import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovie, Movie } from 'app/shared/model/movie.model';
import { MovieService } from './movie.service';
import { IDirector } from 'app/shared/model/director.model';
import { DirectorService } from 'app/entities/director';
import { IVariety } from 'app/shared/model/variety.model';
import { VarietyService } from 'app/entities/variety';

@Component({
  selector: 'jhi-movie-update',
  templateUrl: './movie-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
  isSaving: boolean;

  directors: IDirector[];

  varieties: IVariety[];

  editForm = this.fb.group({
    id: [],
    name: [],
    releaseDay: [],
    image: [],
    brief: [],
    directorId: [],
    varietyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected movieService: MovieService,
    protected directorService: DirectorService,
    protected varietyService: VarietyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ movie }) => {
      this.updateForm(movie);
    });
    this.directorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDirector[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDirector[]>) => response.body)
      )
      .subscribe((res: IDirector[]) => (this.directors = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.varietyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVariety[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVariety[]>) => response.body)
      )
      .subscribe((res: IVariety[]) => (this.varieties = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(movie: IMovie) {
    this.editForm.patchValue({
      id: movie.id,
      name: movie.name,
      releaseDay: movie.releaseDay,
      image: movie.image,
      brief: movie.brief,
      directorId: movie.directorId,
      varietyId: movie.varietyId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const movie = this.createFromForm();
    if (movie.id !== undefined) {
      this.subscribeToSaveResponse(this.movieService.update(movie));
    } else {
      this.subscribeToSaveResponse(this.movieService.create(movie));
    }
  }

  private createFromForm(): IMovie {
    return {
      ...new Movie(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      releaseDay: this.editForm.get(['releaseDay']).value,
      image: this.editForm.get(['image']).value,
      brief: this.editForm.get(['brief']).value,
      directorId: this.editForm.get(['directorId']).value,
      varietyId: this.editForm.get(['varietyId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>) {
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

  trackDirectorById(index: number, item: IDirector) {
    return item.id;
  }

  trackVarietyById(index: number, item: IVariety) {
    return item.id;
  }
}
