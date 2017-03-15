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
    public authRole: string;
    public title: string = `Utilities`;
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

    onDelete(utility: Utility) {
        this.utilityService.deleteUtility(utility).subscribe(() => this.resData.splice(this.resData.indexOf(utility, 0), 1));
    }

    createUtility(utility: Utility) {
        this.resData.push(utility);
    }
}
