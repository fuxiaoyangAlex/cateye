export interface ICity {
  id?: number;
  parentId?: number;
  name?: string;
  rank?: number;
}

export class City implements ICity {
  constructor(public id?: number, public parentId?: number, public name?: string, public rank?: number) {}
}
