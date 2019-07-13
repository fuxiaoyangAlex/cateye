import { ICity } from 'app/shared/model/city.model';

export interface ICinema {
  id?: number;
  name?: string;
  cityId?: ICity;
}

export class Cinema implements ICinema {
  constructor(public id?: number, public name?: string, public cityId?: ICity) {}
}
