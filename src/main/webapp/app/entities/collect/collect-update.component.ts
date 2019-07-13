import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICollect, Collect } from 'app/shared/model/collect.model';
import { CollectService } from './collect.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-collect-update',
  templateUrl: './collect-update.component.html'
})
export class CollectUpdateComponent implements OnInit {
  isSaving: boolean;

  movies: IMovie[];

  customers: ICustomer[];
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    date: [],
    moveId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected collectService: CollectService,
    protected movieService: MovieService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ collect }) => {
      this.updateForm(collect);
    });
    this.movieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMovie[]>) => response.body)
      )
      .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomer[]>) => response.body)
      )
      .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(collect: ICollect) {
    this.editForm.patchValue({
      id: collect.id,
      date: collect.date,
      moveId: collect.moveId,
      customerId: collect.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const collect = this.createFromForm();
    if (collect.id !== undefined) {
      this.subscribeToSaveResponse(this.collectService.update(collect));
    } else {
      this.subscribeToSaveResponse(this.collectService.create(collect));
    }
  }

  private createFromForm(): ICollect {
    return {
      ...new Collect(),
      id: this.editForm.get(['id']).value,
      date: this.editForm.get(['date']).value,
      moveId: this.editForm.get(['moveId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollect>>) {
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

  trackCustomerById(index: number, item: ICustomer) {
    return item.id;
  }
}
