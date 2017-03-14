import { Component, OnInit } from "@angular/core";
import { UtilityService } from './utility.service';

@Component({
    selector: 'utilities',
    templateUrl: 'utility.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
    providers: [ UtilityService ]
})
export class UtilityComponent implements OnInit {
    public title: string = `Utilities`;
    public rowsOnPage = 10;
    public resData: any;
    public isSubUtilityShow = false;
    public utilityId = null;

    constructor( public utilityService: UtilityService ) { }

    public ngOnInit() {
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
}
