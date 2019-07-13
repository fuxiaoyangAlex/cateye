import { IDirector } from 'app/shared/model/director.model';
import { IVariety } from 'app/shared/model/variety.model';

export interface IMovie {
  id?: number;
  name?: string;
  releaseDay?: number;
  image?: string;
  brief?: string;
  directorId?: IDirector;
  varietyId?: IVariety;
}

export class Movie implements IMovie {
  constructor(
    public id?: number,
    public name?: string,
    public releaseDay?: number,
    public image?: string,
    public brief?: string,
    public directorId?: IDirector,
    public varietyId?: IVariety
  ) {}
}
