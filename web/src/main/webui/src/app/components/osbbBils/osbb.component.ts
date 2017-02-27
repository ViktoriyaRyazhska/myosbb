import {
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { LoginService } from '../../shared/login/login.service';
import { OsbbService } from './osbb.service';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { PageParams } from '../../../assets/models/pageParam.interface';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { BillDTO } from '../../../assets/models/bill.dto.interface';
import { SubBill } from '../../../assets/models/sub.bill.interface';

@Component({
  selector: 'osbb',
  styleUrls: [
    '../../../assets/style/page.layout.scss',
    './osbbBils.style.scss'
  ],
  templateUrl: './osbb.component.html',
  providers: [
    LoginService,
    OsbbService
  ]
})

export class OsbbBillsComponent implements OnInit {
  public resData: any;

  @ViewChild('delModal') private delModal: ModalDirective;
  @ViewChild('createModal') private createModal: ModalDirective;
  @ViewChild('createSubBillModal') private createSubBillModal: ModalDirective;

  private status: string = 'ALL';
  private title: string = `Osbb`;
  private oneBill: any;
  public subBill: SubBill = new SubBill();
  private billId: number;
  public pageParams: PageParams = {
    pageNumber: 1,
    sortedBy: 'date',
    orderType: false,
    rowNum: 10
  };
  public newBill: BillDTO = {
    billId: null, name: '', date: null, tariff: 0, toPay: 0, paid: 0,
    apartmentNumber: null, apartmentId: null, providerId: null, status: null,
    parentBillId: null
  };
  constructor(
    public osbbService: OsbbService
  ) { }

  public ngOnInit() {
    this.osbbService.getBilsData(this.pageParams, this.status)
      .subscribe((responze) => {
        this.resData = responze.rows;
      });
  };

  public processOption(status: string) {
    this.status = status;
    this.osbbService.getBilsData(this.pageParams, status)
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public pagePaginationList(list: number) {
    this.pageParams.rowNum = list;
    this.osbbService.getBilsData(this.pageParams, this.status)
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public itemStatusColor(status: string) {
    return status === 'PAID' ? 'rgba(38, 255, 5, .1)' : 'rgba(255, 0, 0, .1)';
  };

  public someExpression(status: string) {
    return  status === 'PAID' ? 'fa fa-check' : 'fa fa-times';
  };

  public refresh(status: string, list: number) {
    this.status = status;
    this.pageParams.rowNum = list;
    this.osbbService.getBilsData(this.pageParams, status)
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public openCreateBillModal() {
    this.createModal.show();
  };

  public cancelCreateModal() {
    this.createModal.hide();
    this.createSubBillModal.hide();
  };

  public onCreateSubBillModalOpen(billId: number) {
    this.osbbService.findBillById(Number(billId))
      .subscribe((response) => {
        this.oneBill = response;
      });
    this.createSubBillModal.show();
  };

  public onDelModalOpen(billId: number) {
    this.billId = +billId;
    this.delModal.show();
  };
}
