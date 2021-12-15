import * as dayjs from 'dayjs';

export interface IApolice {
  id?: string;
  numero?: number | null;
  inicio?: dayjs.Dayjs;
  fim?: dayjs.Dayjs;
  placaVeiculo?: string;
  valor?: number | null;
}

export class Apolice implements IApolice {
  constructor(
    public id?: string,
    public numero?: number | null,
    public inicio?: dayjs.Dayjs,
    public fim?: dayjs.Dayjs,
    public placaVeiculo?: string,
    public valor?: number | null
  ) {}
}

export function getApoliceIdentifier(apolice: IApolice): string | undefined {
  return apolice.id;
}
