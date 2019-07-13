import { Moment } from 'moment';
import { IMovie } from 'app/shared/model/movie.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface ICollect {
  id?: number;
  date?: Moment;
  moveId?: IMovie;
  customerId?: ICustomer;
}

export class Collect implements ICollect {
  constructor(public id?: number, public date?: Moment, public moveId?: IMovie, public customerId?: ICustomer) {}
}
