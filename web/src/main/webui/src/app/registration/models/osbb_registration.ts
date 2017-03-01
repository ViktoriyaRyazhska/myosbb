import { Street } from './addressDTO.model';

export class OsbbRegistration {
  public osbbId: number;
  public name: string;
  public description: string;
  public street: Street;
  public address: string;
  public district: string;
  public logo: any;
  public creationDate: Date;
  public available: boolean;
}
