export interface ICliente {
  id?: string;
  cpf?: string;
  nome?: string;
  cidade?: string;
  estado?: string;
}

export class Cliente implements ICliente {
  constructor(public id?: string, public cpf?: string, public nome?: string, public cidade?: string, public estado?: string) {}
}

export function getClienteIdentifier(cliente: ICliente): string | undefined {
  return cliente.id;
}
