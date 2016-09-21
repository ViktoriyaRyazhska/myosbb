export class PageRequest {
    pageNumber:number;
    rowNum:number;
    sortedBy:string;
    order:boolean;


    constructor(pageNumber:number, rowNum:number, sortedBy:string, order:boolean) {
        this.pageNumber = pageNumber;
        this.rowNum = rowNum;
        this.sortedBy = sortedBy;
        this.order = order;
    }
}