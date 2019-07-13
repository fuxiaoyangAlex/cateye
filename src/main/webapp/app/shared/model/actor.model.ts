export interface IActor {
  id?: number;
  name?: string;
  gender?: string;
  age?: string;
  nationality?: string;
}

export class Actor implements IActor {
  constructor(public id?: number, public name?: string, public gender?: string, public age?: string, public nationality?: string) {}
}
