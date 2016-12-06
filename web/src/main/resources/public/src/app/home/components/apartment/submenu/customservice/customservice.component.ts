import { Component, OnInit, ViewChild } from "@angular/core";
import { BS_VIEW_PROVIDERS, MODAL_DIRECTIVES, BUTTON_DIRECTIVES, ModalDirective } from "ng2-bootstrap";
import { CORE_DIRECTIVES } from "@angular/common";
import { TranslatePipe } from "ng2-translate";
import { CustomserviceService } from "./service/customservice.service";
import { ToasterContainerComponent, ToasterService} from "angular2-toaster/angular2-toaster";
import { SELECT_DIRECTIVES } from "ng2-select";
import { BillDTO } from "./customservice.dto.interface";
import { FORM_DIRECTIVES } from "@angular/forms";

@Component({
    selector: 'customservice',
    templateUrl: 'src/app/home/components/apartment/submenu/customservice/customservice.component.html',
    providers: [CustomserviceService, ToasterService],
    inputs: [ 'isUserDownload'],
    styleUrls: ['src/app/user/bills/bill.css', 'src/shared/css/loader.css', 'src/shared/css/general.css'],
    directives: [ ToasterContainerComponent,
        MODAL_DIRECTIVES, SELECT_DIRECTIVES, CORE_DIRECTIVES, FORM_DIRECTIVES, BUTTON_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe]
})
export class  CustomserviceComponent implements OnInit {
    private parentBillId: Array<String>=[];
    private bills: BillDTO[] = [];
    constructor(private _billService: CustomserviceService, private _toasterService: ToasterService,) {}
    ngOnInit(): any {
    

        
    
        this.getParentBillIds();
    
    }

        getParentBillIds() {
        
        this._billService.getAllParentId()
            .subscribe((data) => {
                    this.bills=data;
                    this.parentBillId =  this.BiilId();
                },
                (error) => {
                    this.handleErrors(error);
                });
    };

    
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
