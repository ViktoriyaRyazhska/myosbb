import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { BS_VIEW_PROVIDERS, MODAL_DIRECTIVES, BUTTON_DIRECTIVES, ModalDirective } from "ng2-bootstrap";
import { CORE_DIRECTIVES } from "@angular/common";
import { TranslatePipe } from "ng2-translate";
import { subbillService } from "./subbill.service";
import { ToasterContainerComponent, ToasterService} from "angular2-toaster/angular2-toaster";
import { SELECT_DIRECTIVES } from "ng2-select";
import {ROUTER_DIRECTIVES} from "@angular/router";
import { BillDTO } from "../customservice.dto.interface";
import { FORM_DIRECTIVES } from "@angular/forms";
import {Router, ActivatedRoute} from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'subbillcomponent',
    templateUrl: 'src/app/home/components/apartment/submenu/customservice/subbill/subbill.component.html',
    providers: [subbillService, ToasterService],
    inputs: ['isUserDownload'],
    styleUrls: ['src/app/home/components/apartment/styles.css'],
    directives: [ToasterContainerComponent, ROUTER_DIRECTIVES,
        MODAL_DIRECTIVES, SELECT_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES, BUTTON_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe]
})
export class SubbillComponent implements OnInit, OnDestroy {

    id: number;
    parentId: number;
    subscription: Subscription;
    private parentBillId: Array<String> = [];
    private bills: BillDTO[] = [];

    constructor(private _billService: subbillService,
        private _toasterService: ToasterService,
        private activatedRoute: ActivatedRoute,
        private router: Router) {
        this.subscription = activatedRoute.params.subscribe(
            (params) => {
                this.parentId = params['id'];
                console.log('Folder changed to ' + this.parentId);
                this.getCurrentBill();
                console.log('Folder changed to ' + this.parentId);
            }
        );
    }

    getCurrentBill() {
        this._billService.getCurrentBill(this.parentId)
            .subscribe(data => {
                this.bills = data;
            });
    }

    getBillid() {
        let tempArr: number[] = [];
        for (let reg of this.bills) {
            tempArr.push(reg.billId);
        }
        return tempArr;
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        console.log('Unsubscribed from ActivatedRoure.params');
    }

    public handleErrors(error) {
        if (error.status === 404 || error.status === 400) {
            console.log('server error 400', error);
            return;
        }
        if (error.status === 500) {
            console.log('server error 500', error);
            return;
        }

        console.log(error);
    }
    
    BiilId(): string[] {
        let tempArr: string[] = [];
        for (let reg of this.bills) {
            tempArr.push(reg.name);
        }
        return tempArr;
    }

}
