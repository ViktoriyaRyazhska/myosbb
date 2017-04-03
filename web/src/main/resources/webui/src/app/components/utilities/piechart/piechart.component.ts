import { Input, Component } from '@angular/core';

@Component({
    selector: 'piechart',
    template: '<p-chart type="pie" [data]="chartData"></p-chart>'
})
export class UtilPieChartComponent {

    @Input() chartData: any;
}
