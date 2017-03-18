import { Component, OnInit, } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { UtilityService } from './utility.service';
import { UtilityFormComponent } from './utility-form/utility-form.component';
import { LoginService } from '../../shared/login/login.service';
import { Utility } from '../../models/utility.model';
@Component({
    selector: 'utility',
    templateUrl: 'utility.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './utility.scss'],
    providers: [UtilityService]

})
export class UtilityComponent implements OnInit {
    public title: string = `Utilities`;
    public utilities: Utility[];

    constructor(public utilityService: UtilityService, private LoginService: LoginService) {
		console.log(this.LoginService.currentUser);
    }

    public ngOnInit() {
        console.log('UtilityComponent : init');
        this.listUtilities();
    }

    public listUtilities() {
        this.utilityService.listAllUtilities().subscribe((data) => {
                this.utilities = data;
            },
            (error) => {
                this.handleError(error);
            });
            
    }

    private handleError(error: any): Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

    onEdit(item: any){
        console.log(item);
    }

    onDelete(utility: Utility) {
        this.utilityService.deleteUtility(utility).subscribe(()=>this.utilities.splice(this.utilities.indexOf(utility, 0), 1));
    }

    createUtility(utility: Utility) {
        this.utilities.push(utility);
    }
}
