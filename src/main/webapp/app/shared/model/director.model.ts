export interface IDirector {
  id?: number;
  name?: string;
  gender?: string;
  age?: string;
}

export class Director implements IDirector {
  constructor(public id?: number, public name?: string, public gender?: string, public age?: string) {}
}
