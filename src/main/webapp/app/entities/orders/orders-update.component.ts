import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
  selector: 'jhi-orders-update',
  templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  movies: IMovie[];

  customers: ICustomer[];

  editForm = this.fb.group({
    id: [],
    count: [],
    movieId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ordersService: OrdersService,
    protected movieService: MovieService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orders }) => {
      this.updateForm(orders);
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

  updateForm(orders: IOrders) {
    this.editForm.patchValue({
      id: orders.id,
      count: orders.count,
      movieId: orders.movieId,
      customerId: orders.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orders = this.createFromForm();
    if (orders.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersService.update(orders));
    } else {
      this.subscribeToSaveResponse(this.ordersService.create(orders));
    }
  }

  private createFromForm(): IOrders {
    return {
      ...new Orders(),
      id: this.editForm.get(['id']).value,
      count: this.editForm.get(['count']).value,
      movieId: this.editForm.get(['movieId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
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
