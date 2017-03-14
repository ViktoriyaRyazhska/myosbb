import {Osbb} from './osbb.model'
import {House} from './house.model'
export class Utility {
    

    public utilityId:number;
    public name: string;
    public description:string;
    public price:number;
    public priceCurrency:string;
    public osbb:Osbb;
    public parent:Utility;
    public houses:Array<House>;
    
}
