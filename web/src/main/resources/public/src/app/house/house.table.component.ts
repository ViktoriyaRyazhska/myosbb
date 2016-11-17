import { Component, OnInit, ViewChild } from "@angular/core";
import { HousePageObject } from "./house.page.object";
import { HouseService } from "./house.service";
import { Router, ActivatedRoute } from "@angular/router";
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../shared/pipes/capitalize-first-letter";
import { ToasterContainerComponent, ToasterService } from "angular2-toaster/angular2-toaster";
import {
    onErrorResourceNotFoundToastMsg,
    onErrorServerNoResponseToastMsg
} from "../../shared/error/error.handler.component";
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective } from "ng2-bootstrap";
import { FORM_DIRECTIVES } from "@angular/forms";
import { CORE_DIRECTIVES } from "@angular/common";
import { Subscription } from "rxjs";
import { PageParams } from "../../shared/models/search.model";
import Regex = require('../../shared/services/regex.all.text');

@Component({
    selector: 'house-table',
    templateUrl: 'src/app/house/house_table.html',
    providers: [HouseService, ToasterService],
    directives: [ToasterContainerComponent, MODAL_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    styleUrls: ['src/app/house/house.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    inputs: ['admin', 'manager']
})
export class HouseTableComponent implements OnInit {

    private sub: Subscription;
    private osbbId: number;
    private houses: HousePageObject[] = [];
    private houseId: number;
    private pageParams: PageParams = {pageNumber: 1, sortedBy: null, orderType: false, rowNum: 10};
    private totalPages: string;
    private pageList: Array<number> = [];
    private pending: boolean = false;
    private rows: number[] = [10, 20, 50];
    private onSearch: boolean = false;
    private admin: boolean = false;
    private manager: boolean = false;
    private selectedHouse: HousePageObject = {
        houseId: null, city: '', street: '', zipCode: '', description: '',
        osbbName: '', apartmentCount: null, numberOfInhabitants: null
    };
    private active: boolean = false;
    @ViewChild('delModal') private delModal: ModalDirective;
    @ViewChild('addModal') private addModal: ModalDirective;

    constructor(private _houseService: HouseService,
                private _router: Router,
                private _toasterService: ToasterService,
                private _routeParams: ActivatedRoute) {
    }

    ngOnInit(): any {
        this.initHousesArr();
    }

    refresh() {
        this.initHousesArr();
    }

    openDelModal(houseId: number) {
        this.houseId = houseId;
        this.delModal.show();
    }

    initHousesArr() {
        if (this.admin) {
            this.findAllHousesByPage();
        } else {
            this.findAllHousesByOsbb();
        }
    }

    closeDelModal() {
        console.log("delete: " + this.houseId);
        this._houseService.deleteHouseById(this.houseId)
            .subscribe(() => {
                    console.log("refreshing...");
                    this.refresh();
                    this.delModal.hide();
                },
                (error)=> {
                    this.handleErrors(error)
                }
            );
    }

    showAddHouseModal() {
        console.log('opening addModal');
        this.active = true;
        this.addModal.show();
    }

    cancelAddModal() {
        this.active = false;
        this.addModal.hide();
        setTimeout(()=> {
            this.active = true
        }, 0)
    }

    onAddHouseSubmit() {
        this.cancelAddModal();
        console.log('saving ', JSON.stringify(this.selectedHouse));
        this._houseService.saveHouse(this.selectedHouse)
            .subscribe(()=> {
                    console.log("refreshing...");
                    this.refresh();
                },
                (error)=> this.handleErrors(error))

    }

    matches(value: string): boolean {
        /* text matching cyrillic alphabet also */
        if (Regex.TEXT.test(value)) {
            return true;
        }
        return false;
    }

    selectByPageNumber(num) {
        this.pageParams.pageNumber = +num;
        this.findAllHousesByPage();
    }

    private findAllHousesByPage() {
        this.emptyPageList();
        this.pending = true;
        this._houseService.admin_getAllHouses(this.pageParams)
            .subscribe((data)=> {
                    this.pending = false;
                    this.houses = data.rows;
                    this.totalPages = data.totalPages;
                    this.fillPageList(+data.beginPage, +data.endPage)
                },
                (error)=> {
                    this.handleErrors(error);
                });
    }

    findAllHousesByOsbb() {
        console.log("find All houses by osbb: " + this.osbbId);
        this.emptyPageList();
        this.pending = true;
        this._houseService.currentUser_getAllHousesByOsbb(this.pageParams, this.osbbId)
            .subscribe((data)=> {
                    this.pending = false;
                    this.houses = data.rows;
                    this.totalPages = data.totalPages;
                    this.fillPageList(+data.beginPage, +data.endPage)
                },
                (error)=> {
                    this.handleErrors(error);
                });
    }

    fillPageList(beginIndex, endIndex) {
        for (let pageNum = beginIndex; pageNum <= endIndex; pageNum++) {
            this.pageList.push(pageNum);
        }
    }

    emptyPageList() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    prevPage() {
        this.pageParams.pageNumber -= 1;
        console.log("load by page :", this.pageParams.pageNumber);
        this.findAllHousesByPage()
    }

    nextPage() {
        this.pageParams.pageNumber += 1;
        console.log("load by page :", this.pageParams.pageNumber);
        this.findAllHousesByPage()
    }

    selectRowNum(row: number) {
        console.log('load by row number', row);
        this.pageParams.rowNum = +row;
        this.findAllHousesByPage();
    }

    onClickSearchByParam(value: string) {
        if (value.trim().length && Regex.TEXT.test(value)) {
            this.emptyPageList();
            this.pending = true;
            console.log('search by ', value);
            this._houseService.searchByInputParam(value)
                .subscribe((data)=> {
                        this.pending = false;
                        this.onSearch = true;
                        this.houses = data;
                        this.fillPageList(this.pageParams.pageNumber, this.pageParams.pageNumber);
                    },
                    (error)=> {
                        this.handleErrors(error);
                    });
        }
    }

    private handleErrors(error: any) {
        if (error.status === 404 || error.status === 400) {
            console.log('server error 400');
            this._toasterService.pop(onErrorResourceNotFoundToastMsg);
            return;
        }

        if (error.status === 500) {
            console.log('server error 500');
            this._toasterService.pop(onErrorServerNoResponseToastMsg);
            return;
        }
    }

    onNavigate(id: number) {
        console.log('navigating to house with id ', id);
        if (this.admin) {
            this._router.navigate(['admin/house', id]);
            return;
        }
        this._router.navigate(['home/house', id]);
    }

}