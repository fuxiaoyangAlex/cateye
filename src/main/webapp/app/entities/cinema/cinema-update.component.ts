import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICinema, Cinema } from 'app/shared/model/cinema.model';
import { CinemaService } from './cinema.service';
import { ICity } from 'app/shared/model/city.model';
import { CityService } from 'app/entities/city';

@Component({
  selector: 'jhi-cinema-update',
  templateUrl: './cinema-update.component.html'
})
export class CinemaUpdateComponent implements OnInit {
  isSaving: boolean;

  cities: ICity[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    cityId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cinemaService: CinemaService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cinema }) => {
      this.updateForm(cinema);
    });
    this.cityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICity[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICity[]>) => response.body)
      )
      .subscribe((res: ICity[]) => (this.cities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cinema: ICinema) {
    this.editForm.patchValue({
      id: cinema.id,
      name: cinema.name,
      cityId: cinema.cityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cinema = this.createFromForm();
    if (cinema.id !== undefined) {
      this.subscribeToSaveResponse(this.cinemaService.update(cinema));
    } else {
      this.subscribeToSaveResponse(this.cinemaService.create(cinema));
    }
  }

  private createFromForm(): ICinema {
    return {
      ...new Cinema(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      cityId: this.editForm.get(['cityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICinema>>) {
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

  trackCityById(index: number, item: ICity) {
    return item.id;
  }
}
