import { IMovie } from 'app/shared/model/movie.model';
import { IActor } from 'app/shared/model/actor.model';

export interface IPlay {
  id?: number;
  movieId?: IMovie;
  actorId?: IActor;
}

export class Play implements IPlay {
  constructor(public id?: number, public movieId?: IMovie, public actorId?: IActor) {}
}
