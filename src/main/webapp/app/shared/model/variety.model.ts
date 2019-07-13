export interface IVariety {
  id?: number;
  label?: string;
}

export class Variety implements IVariety {
  constructor(public id?: number, public label?: string) {}
}
