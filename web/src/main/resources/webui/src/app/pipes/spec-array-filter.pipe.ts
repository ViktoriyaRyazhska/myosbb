import { Pipe, PipeTransform } from '@angular/core';
import {Utility} from '../models/utility.model';

@Pipe({
    name: 'specArrayFilter'
})
export class SpecArrayFilterPipe implements PipeTransform {
    transform(items: Utility[], id: any): Utility[] {

        if (items == null) {
            return null;
        }
        if (id == null) {
            return items.filter(item => item.parent == id);
        }

        items = items.filter(item => item.parent != null);
        return items.filter(item => item.parent.utilityId == id);
    }
}
