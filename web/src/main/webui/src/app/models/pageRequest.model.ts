export class PageRequest {
  public pageNumber: number;
  public rowNum: number;
  public sortedBy: string;
  public order: boolean;
  constructor(pageNumber: number, rowNum: number, sortedBy: string, order: boolean) {
    this.pageNumber = pageNumber;
    this.rowNum = rowNum;
    this.sortedBy = sortedBy;
    this.order = order;
  }
}
