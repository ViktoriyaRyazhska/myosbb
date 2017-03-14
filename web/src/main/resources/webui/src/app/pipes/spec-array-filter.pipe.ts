import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'specArrayFilter'
})
export class SpecArrayFilterPipe implements PipeTransform {
    transform(items: any[], id: any): any {

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
