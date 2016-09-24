import {Component, OnInit} from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';
import {TranslatePipe} from "ng2-translate";
import 'rxjs/Rx';
import moment from 'moment';

import { OsbbDTO } from './osbb';
import { OsbbService } from './osbb.service';
import {LoginService} from "../../../login/login.service";
import { OsbbModalComponent } from './osbb_form/osbb-modal.component';
import { OsbbDelFormComponent } from './osbb_form/osbb-del-form.component';
import {CurrentUserService} from "../../../../shared/services/current.user.service";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import ApiService = require("../../../../shared/services/api.service");
import {IOsbb, Osbb} from "../../../../shared/models/osbb";

@Component({
    selector: 'osbb',
    templateUrl: './src/app/admin/components/osbb/osbb.component.html',
    styleUrls: ['./src/app/admin/components/osbb/osbb.component.css', './src/shared/css/general.css'],
    providers: [OsbbService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, OsbbModalComponent, OsbbDelFormComponent],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class OsbbComponent implements OnInit { 
    
    osbbArr:IOsbb[];
    updatedOsbb:IOsbb;
    asc: boolean;

    constructor( private osbbService: OsbbService, private currentUserService:CurrentUserService, private router:Router,private loginService:LoginService) { 
        this.osbbArr = [];
    }

    ngOnInit() {
        this.initOsbbArr("All");
    }

    private initUpdatedOsbb(osbb:IOsbb): void {
        this.updatedOsbb = osbb;
    }

    initOsbbArr(available: string) {
        if(available === "All") {
            this.osbbService.getAllOsbb().then(osbbArr => this.osbbArr = osbbArr);
        }
        else if(available === "Available"){
            this.osbbService.getByAvailable(true).then(osbbArr => this.osbbArr = osbbArr);
        } else {
            this.osbbService.getByAvailable(false).then(osbbArr => this.osbbArr = osbbArr);
        }
    }

    createOsbb(osbbDTO:OsbbDTO): void {
        let osbb = osbbDTO.osbb;
        osbb.creator = this.currentUserService.getUser();
        if(osbbDTO.file !== null && osbbDTO.file !== undefined){
            this.osbbService.upload(osbbDTO.file)         
            .then((attachment)=> {
                osbb.logo = attachment;
                this.osbbService.addOsbb(osbb).then((osbb)=> this.osbbArr.unshift(osbb));
            });
        } else {
            this.osbbService.addOsbb(osbb).then((osbb)=> this.osbbArr.unshift(osbb));
        }
    }

    private addOsbb(osbb: IOsbb): void {
        this.osbbArr.unshift(osbb);
    }

    editOsbb(osbbDTO:OsbbDTO): void {
        if(osbbDTO.file !== null && osbbDTO.file !== undefined){
            this.osbbService.upload(osbbDTO.file)         
            .then((attachment)=> {
                let osbb = osbbDTO.osbb;
                osbb.logo = attachment;
                this.osbbService.editOsbb(osbb);
            });
        } else {
            this.osbbService.editOsbb(osbbDTO.osbb);
        }
    }

    deleteOsbb(osbb:IOsbb): void {
        this.osbbService.deleteOsbb(osbb).then(osbb => this.deleteOsbbFromArr(osbb));
    }

     private deleteOsbbFromArr(osbb: IOsbb): void {
         let index = this.osbbArr.indexOf(osbb);
         if(index > -1) {
            this.osbbArr.splice(index, 1);
         }
    }

    searchByNameOsbb(osbbName: string) {
        if(osbbName.trim()!=='') {
            this.osbbService.getAllOsbbByNameContaining(osbbName).then(osbbArr => this.osbbArr = osbbArr);
        } else {
             this.osbbService.getAllOsbb().then(osbbArr => this.osbbArr = osbbArr);
        }
    }

    toggleOrder() {
        if(this.asc === false) {
            this.asc = true;
        } else {
            this.asc = false;
        }
    }

    sortBy(sortedBy:string): void {
        this.toggleOrder();
        this.osbbService.getAllOsbbByOrder(sortedBy, this.asc).then(osbbArr => this.osbbArr = osbbArr);
    }

    clearSearch(searchInput) {
        if(searchInput.value === '') {
            this.initOsbbArr("All");
        }
    }

     getFormatDate(date:Date):string {
       return moment(date).format("DD.MM.YYYY");
    }

    redirectToHouseTable(osbbId: number) {
        if(this.loginService.checkLogin()){
             this.router.navigate(['admin/houses/' + osbbId]); 
        } else {
            this.router.navigate(['login']);
        }
    }
}