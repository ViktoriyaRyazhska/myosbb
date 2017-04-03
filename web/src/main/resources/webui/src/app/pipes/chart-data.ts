import {Pipe, PipeTransform} from '@angular/core';
import {Utility} from '../models/utility.model';

@Pipe({
    name: 'chartDataFilter'
})
export class ChartDataFilterPipe implements PipeTransform {
    public segmentCount;

    public resultData = {

        labels: [],
        datasets: [
            {
                data: [],
                backgroundColor: [],
                hoverBackgroundColor: []
            }]
    };

    transform(data: Utility[]): any {

        if (data == null) {
            return null;
        }

        this.segmentCount = data.length;

        this.resultData.labels = data.map(item => item.name);

        this.resultData.datasets[0].data = data.map(item => item.price);

        let colorsArray = this.getColorArray(this.segmentCount);

        this.resultData.datasets[0].backgroundColor = colorsArray;
        this.resultData.datasets[0].hoverBackgroundColor = colorsArray;

        return this.resultData;
    }

    public getColorArray(num) {
        let result = [];
        for (let i = 0; i < num; i += 1) {
            let letters = '0123456789ABCDEF'.split('');
            let color = '#';
            for (let j = 0; j < 6; j += 1) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            result.push(color);
        }
        return result;
    }
}
