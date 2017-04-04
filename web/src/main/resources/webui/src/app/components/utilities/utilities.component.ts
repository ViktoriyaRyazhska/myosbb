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
    public userUtil: Utility[];
    public dataExpand: boolean[] = [];
    public utilParentId = null;

    constructor(public utilityService: UtilitiesService, private LoginService: LoginService) {
    }

    public ngOnInit() {
        this.authRole = this.LoginService.getRole();
        this.utilityService.getUtilitiesForUser()
            .subscribe((data) => {
                this.userUtil = data;
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

    public expColl(index: number) {
        this.dataExpand[index] = !this.dataExpand[index];
    }

    public checkIfParent(id: number): boolean {
        for (let i = 0; i < this.userUtil.length; i++) {
            if (this.userUtil[i].parent != null && this.userUtil[i].parent.utilityId == id) {
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
