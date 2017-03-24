import {Component, OnInit} from "@angular/core";
import {UtilitiesService} from './utilities.service';
import {LoginService} from '../../shared/login/login.service';
import {Utility} from '../../models/utility.model';

@Component({
    selector: 'utilities',
    templateUrl: 'utilities.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './utility.scss'],
    providers: [UtilitiesService]
})
export class UtilitiesComponent implements OnInit {
    
    public title: string = `Utilities`;
    public utilities: Utility[];
    public deleteUtility: Utility;
    public authRole: string;
    public rowsOnPage = 10;
    public resData: Utility[];
    public isSubUtilityShow = false;
    public utilityId = null;

    constructor(public utilityService: UtilitiesService, private LoginService: LoginService) {
    }

    public ngOnInit() {
        this.authRole = this.LoginService.getRole();
        this.utilityService.getUtilitiesForUser()
            .subscribe((data) => {
                this.resData = data;
            });

        this.listUtilities();
    }

    public listUtilities() {
        this.utilityService.listAllUtilitiesByOsbb().subscribe((data) => {
                this.utilities = data;
            },
            (error) => {
                this.handleError(error);
            });
     }

    public switchMode(id: number) {
        this.utilityId = id;
        this.isSubUtilityShow = !this.isSubUtilityShow;
    }

    public checkIfParent(id: number): boolean {
        for (let i = 0; i < this.resData.length; i++) {
            if (this.resData[i].parent != null && this.resData[i].parent.utilityId == id) {
                console.log(true);
                return true;
            }
        }
        return false;
    }

    private handleError(error: any): Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

    onEdit(item: any) {
        console.log(item);
    }

    createUtility(utility: Utility) {
        this.utilities.push(utility);
    }
    onDelete(utility: Utility) {
        this.deleteUtility = utility;
    }

    onConfirmDelete(){
        this.utilityService.deleteUtility(this.deleteUtility)
        .subscribe(()=>this.utilities.splice(this.utilities.indexOf(this.deleteUtility, 0), 1));
    }

}
