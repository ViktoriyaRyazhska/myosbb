import { Street, District } from './addressDTO.model';

export class OsbbDTO {
  osbbId: number;
  name: string;
  description: string;
  street: Street;
  address: string;
  district: District;
  houseNumber: string;
  logo: any;
  creationDate: Date;
  available: boolean;
  countOfHouses: number;
  countOfUsers: number;
}
