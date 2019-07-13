import { IMovie } from 'app/shared/model/movie.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrders {
  id?: number;
  count?: number;
  movieId?: IMovie;
  customerId?: ICustomer;
}

export class Orders implements IOrders {
  constructor(public id?: number, public count?: number, public movieId?: IMovie, public customerId?: ICustomer) {}
}
