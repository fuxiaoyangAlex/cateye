export interface ICustomer {
  id?: number;
  name?: string;
  phone?: number;
  gender?: string;
}

export class Customer implements ICustomer {
  constructor(public id?: number, public name?: string, public phone?: number, public gender?: string) {}
}
