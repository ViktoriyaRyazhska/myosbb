import { User } from './user.model';
import { Street, District } from './addressDTO.model';

export class IOsbb {
  osbbId: number;
  name: string;
  description: string;
  creator: User;
  street: Street;
  district: District;
  houseNumber: string;
  logo: any;
  creationDate: Date;
  available: boolean;
}
export class Osbb implements IOsbb {
  osbbId: number;
  name: string;
  description: string;
  street: Street;
  district: District;
  creator: User;
  houseNumber: string;
  logo: any;
  creationDate: Date;
  available: boolean;
}

