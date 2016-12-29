import { Component, OnInit, ViewChild } from "@angular/core";
import { ReportDownloaderComponent } from "../report/download/report.downloader.component";
import { BS_VIEW_PROVIDERS, MODAL_DIRECTIVES, BUTTON_DIRECTIVES, ModalDirective } from "ng2-bootstrap";
import { CORE_DIRECTIVES } from "@angular/common";
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../shared/pipes/capitalize-first-letter";
import { PageCreator } from "../../../shared/services/page.creator.interface";
import { User } from "../../../shared/models/User";
import { CurrentUserService } from "../../../shared/services/current.user.service";
import { BillService } from "./bill.service";
import { ToasterContainerComponent, ToasterService} from "angular2-toaster/angular2-toaster";
import {
    onErrorServerNoResponseToastMsg,
    onErrorResourceNotFoundToastMsg
} from "../../../shared/error/error.handler.component";
import { PageParams } from "../../../shared/models/search.model";
import { HeaderComponent } from "../../header/header.component";
import { SELECT_DIRECTIVES } from "ng2-select";
import { HouseService } from "../../house/house.service";
import { HousePageObject } from "../../house/house.page.object";
import { IApartment } from "../../../shared/models/apartment.interface";
import { ProviderService } from "../provider/service/provider-service";
import { Provider } from "../../../shared/models/provider.interface";
import { BillDTO } from "./show_bill_dto.interface";
import { SubBill } from "./subBill";
import { BillChartComponent } from "./chart/bill.chart.component";
import { FORM_DIRECTIVES } from "@angular/forms";

@Component({
    selector: 'bill-component',
    templateUrl: 'src/app/user/bills/bill.html',
    providers: [BillService, HouseService, ProviderService, ToasterService, CurrentUserService],
    inputs: ['osbbRole', 'isUserDownload'],
    styleUrls: ['src/app/user/bills/bill.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [BillChartComponent, ToasterContainerComponent, ReportDownloaderComponent,
        MODAL_DIRECTIVES, SELECT_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES, BUTTON_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class UserBillComponent implements OnInit {
    
    private bills: BillDTO[] = [];
    private billCalcVal: number = 0;
    private newBill: BillDTO = {
        billId: null, name: '', date: null, tariff: 0, toPay: 0, paid: 0, 
        apartmentNumber: null, apartmentId: null, providerId: null, status: null,
        parentBillId: null
    };
    private subBill: SubBill = new SubBill();
    private pageCreator: PageCreator<BillDTO>;
    private pageNumber: number = 1;
    private pageList: Array<number> = [];
    private totalPages: number;
    private active: boolean = true;
    private order: boolean = true;
    private pending = false;
    private billId: number;
    private onSearch: boolean = false;
    private rows: number[] = [10, 20, 50];
    private selectedRow: number = 10;
    private pageParams: PageParams = {pageNumber: 1, sortedBy: 'date', orderType: false, rowNum: 10};
    private currentUser: User;
    private isUserDownload: boolean = false;
    private osbbRole: string;
    private options = {ALL: 'ALL', PAID: 'PAID', NOT_PAID: 'NOT_PAID'};
    private status: string = this.options.ALL;
    @ViewChild('delModal') private delModal: ModalDirective;
    @ViewChild('createModal') private createModal: ModalDirective;
    @ViewChild('createSubBillModal') private createSubBillModal: ModalDirective;
    private isSelectedHouse: boolean = false;
    private isSelectedApartment: boolean = false;
    private isSelectedProvider: boolean = false;
    private houseList: HousePageObject[] = [];
    private apartmentList: IApartment[] = [];
    private providerList: Provider[] = [];
    private houses: Array<string> = [];
    private apartment: Array<string> = [];
    private provider: Array<string> = [];
    private temp: string;
    private oneBill: BillDTO;

    constructor(private _billService: BillService, private _toasterService: ToasterService,
                private _houseService: HouseService,
                private _providerService: ProviderService) {
        this.currentUser = HeaderComponent.currentUserService.currentUser;
        console.log(JSON.stringify('loading..' + this.currentUser));
    }

    onCreateSubBill() {
        this.oneBill.tariff = parseFloat(this.subBill.price.toString().replace(',', '.'));
        this.oneBill.toPay = this.oneBill.tariff;
        this.oneBill.billId = null;
        this.oneBill.name = this.subBill.name;
        this.oneBill.parentBillId = this.billId.valueOf();
        this._billService.saveBill(this.oneBill)
            .subscribe(()=> {
                this.refresh();
            }, (error)=> this.handleErrors(error));
    }

     onCreateSubBillModalOpen(billId: number) {
        this.billId = +billId;
        this._billService.findBillById(this.billId)
                .subscribe((data) => {
                    this.oneBill = data;
                }, (error)=>
                this.handleErrors(error));
        this.createSubBillModal.show();
    }

    onCloseCreateSubBillModal() {
        this.createSubBillModal.hide();
        this.subBill.name = '';
        this.subBill.price = 0;
    }

    listAllHouses() {
        this._houseService.listAllHouses()
            .subscribe((data)=> {
                this.houseList = data;
                this.houses = this.fillHouses();
            }, (error)=>
                this.handleErrors(error));
    }

    listAllProviders() {
        this._providerService.getAllProviders()
            .subscribe((data)=> {
                this.providerList = data;
                this.provider = this.fillProviders();
            }, (error)=>this.handleErrors(error))
    }

    ngOnInit(): any {
        if (this.osbbRole != 'HEAD') {
            this.isUserDownload = true;
        }
        this.getBillsByPageNum(this.pageNumber, this.selectedRow, this.status);
        this.listAllHouses();
        this.listAllProviders();
    }

    emptyHouses() {
        while (this.houses.length) {
            this.houses.pop();
        }
    }

    emptyApartments() {
        while (this.apartment.length) {
            this.apartment.pop();
        }
    }

    fillApartment(): string[] {
        let tempArr: string[] = [];
        for (let apartmentObject of this.apartmentList) {
            tempArr.push('№ ' + apartmentObject.number);
        }
        return tempArr;
    }

    fillHouses(): string[] {
        let tempArr: string[] = [];
        for (let housePageObject of this.houseList) {
            tempArr.push(housePageObject.street.name);
        }
        return tempArr;
    }

    fillProviders() {
        let temp: string[] = [];
        for (let providerObject of this.providerList) {
            temp.push(providerObject.name);
        }
        return temp;
    }

    refresh() {
        console.log('refreshing...');
        this.getBillsByPageNum(this.pageNumber, this.pageParams.rowNum, this.status);
    }

    onDelModalOpen(billId: number) {
        this.billId = +billId;
        this.delModal.show();
    }

    closeDelModal() {
        setTimeout(()=> {
            this.delModal.hide();
        }, 0);
        this._billService.deleteById(this.billId)
            .subscribe(()=> {
                    this.refresh();
                },
                (error)=> this.handleErrors(error));
    }

    openCreateBillModal() {
        this.createModal.show();
    }

    cancelCreateModal() {
        this.active = false;
        this.createModal.hide();
        setTimeout(()=> {
            this.active = true;
        }, 0);
    }

    onSaveNewBill() {
        this._billService.saveBill(this.newBill)
            .subscribe(()=> {
                this.refresh();
            }, (error)=> this.handleErrors(error));
        this.clearSelectedFields();
        this.cancelCreateModal();
        console.log(this.newBill);
    }

    clearSelectedFields() {
        this.isSelectedApartment = false;
        this.isSelectedHouse = false;
        this.isSelectedProvider = false;
        this.newBill.name = '';
        this.newBill.tariff = 0;
        this.newBill.toPay = 0;
        this.newBill.paid = 0;
        this.billCalcVal = 0;
        this.newBill.date = null;
    }

    isNotNumber(value: number) {
        return isNaN(value);
    }

    getBillsByPageNum(pageNumber: number, selectedRow: number, status: string) {
        this.pending = true;
        this.pageParams.pageNumber = pageNumber;
        this.pageParams.rowNum = selectedRow;
        this.status = status;
        this._billService.getAllByRole(this.osbbRole, this.currentUser.userId, this.pageParams, this.status)
            .subscribe((data) => {
                    this.processBillDTOData(data);
                },
                (error) => {
                    this.handleErrors(error);
                });
    };

    private processBillDTOData(data) {
        console.log('processing bill data...');
        this.pending = false;
        this.pageCreator = data;
        this.bills = data.rows;
        this.preparePageList(+this.pageCreator.beginPage,
            +this.pageCreator.endPage);
        this.totalPages = +data.totalPages;
    }

    public handleErrors(error) {
        this.pending = false;
        if (error.status === 404 || error.status === 400) {
            console.log('server error 400', error);
            this._toasterService.pop(onErrorResourceNotFoundToastMsg);
            return;
        }

        if (error.status === 500) {
            console.log('server error 500', error);
            this._toasterService.pop(onErrorServerNoResponseToastMsg);
            return;
        }
        console.log(error);
    }

    isPaid(status: string): boolean {
        return status == 'PAID';
    }

    isDateValid(date: string): boolean {
        return /\d{4}-\d{2}-\d{2}/.test(date);
    }

    selectRowNum(row: number) {
        this.getBillsByPageNum(this.pageParams.pageNumber, row, this.status);
    }


    prevPage() {
        this.pageParams.pageNumber = this.pageParams.pageNumber - 1;
        this.getBillsByPageNum(this.pageParams.pageNumber, this.pageParams.rowNum, this.status);
    }

    nextPage() {
        this.pageParams.pageNumber = this.pageParams.pageNumber + 1;
        this.getBillsByPageNum(this.pageParams.pageNumber, this.pageParams.rowNum, this.status);
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    preparePageList(start: number, end: number) {
        this.emptyArray();
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    sortBy(name: string) {
        console.log('sorted by ', name);
        this.pageParams.orderType = !this.pageParams.orderType;
        this.pageParams.sortedBy = name;
        console.log('order by asc', this.pageParams.orderType);
        this._billService.getAllByRole(this.osbbRole, this.currentUser.userId, this.pageParams, this.status)
            .subscribe((data) => {
                    this.processBillDTOData(data);
                },
                (error) => {
                    this.handleErrors(error);
                });
    }

    confirmBill(bill: BillDTO) {
        console.log('confirming bill', bill.billId);
        bill.paid = bill.toPay;
        bill.status = this.options.PAID;
        this._billService.saveBill(bill).subscribe(()=> {
                this.refresh();
            },
            (error) => {
                this.handleErrors(error)
            });
    }

    onClickSearchByParam(value: string) {
        if (value.trim().length) {
            console.log('search by ', value);
        }
    }

    processOption(status: string) {
        console.log('status', status);
        this.status = status;
        this.getBillsByPageNum(this.pageNumber, this.selectedRow, this.status);
    }

    refreshHouse(value: any) {
        console.log('refresh house: ', value);
        this.isSelectedHouse = false;
    }

    selectedHouse(value: any) {
        this.isSelectedHouse = true;
        console.log('select house: ', value);
        let houseId = this.selectHouseIdByHouseValue(value.text);
        console.log('selected house id: ', houseId);
        this._houseService.getAllApartmentsByHouseId(houseId)
            .subscribe((data)=> {
                this.apartmentList = data;
                this.apartment = this.fillApartment();
                console.log('all apartment numbers', this.apartment);
            }, (error)=> {
                this.handleErrors(error)
            });
        this.createModal.show();
    }

    refreshApartment(value: any) {
        this.isSelectedApartment = false;
    }

    selectedApartment(value: any) {
        this.isSelectedApartment = true;
        console.log('selected apartment: ', value.text.substring(2));
        let apartmentNumber = parseInt(value.text.substring(2));
        this.newBill.apartmentNumber = apartmentNumber;
        let apartmentId = this.selectApartmentIdByApartmentNumber(apartmentNumber);
        this.newBill.apartmentId = apartmentId;
        console.log('bill updated with apartment id: ', this.newBill.apartmentId);
        this.createModal.show();
    }

    selectApartmentIdByApartmentNumber(apartmentNumber: number): number {
        let apartmentId = 0;
        for (let apartment of this.apartmentList) {
            if (apartmentNumber === apartment.number) {
                apartmentId = apartment.apartmentId;
            }
        }
        return apartmentId;
    }

    refreshProvider(value: any) {
        console.log('refresh provider: ', value);
        this.isSelectedProvider = false;
    }

    selectedProvider(value: any) {
        this.isSelectedProvider = true;
        console.log('select provider: ', value.text);
        let providerId = this.selectProviderIdByProviderName(value.text);
        this.newBill.providerId = providerId;
        console.log('bill updated with provider id: ', this.newBill.providerId);
        this.createModal.show();
    }

    selectProviderIdByProviderName(value: string): number {
        let providerId = 0;
        for (let provider of this.providerList) {
            if (value.match(provider.name)) {
                providerId = provider.providerId;
                break;
            }
        }
        return providerId;
    }

    selectHouseIdByHouseValue(value: string): number {
        let houseId = 0;
        for (let houseObject of this.houseList) {
            if (value.match(houseObject.street.name)) {
                houseId = houseObject.houseId;
                break;
            }
        }
        return houseId;
    }

    isSelected(): boolean {
        return this.isSelectedHouse && this.isSelectedApartment && this.isSelectedProvider
    }

    calcToPay(value: string) {
        this.temp = this.newBill.tariff.toString().replace(",", ".");
        this.newBill.tariff = parseFloat(this.temp);
        this.temp = this.billCalcVal.toString().replace(",", ".");
        this.billCalcVal = parseFloat(this.temp);
        console.log('calc', value);
        if (!isNaN(parseInt('' + this.billCalcVal))) {
            this.newBill.toPay = this.billCalcVal * this.newBill.tariff;
            this.newBill.toPay = parseFloat(Math.round(this.newBill.toPay * 100) / 100);
            console.log('toPay: ', this.newBill.toPay);
        }
    }
}
