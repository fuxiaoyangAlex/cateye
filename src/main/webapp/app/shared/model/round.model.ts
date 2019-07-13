import { Moment } from 'moment';
import { ICinema } from 'app/shared/model/cinema.model';
import { IMovie } from 'app/shared/model/movie.model';

export interface IRound {
  id?: number;
  time?: Moment;
  duration?: number;
  price?: number;
  cinemaId?: ICinema;
  movieId?: IMovie;
}

export class Round implements IRound {
  constructor(
    public id?: number,
    public time?: Moment,
    public duration?: number,
    public price?: number,
    public cinemaId?: ICinema,
    public movieId?: IMovie
  ) {}
}
