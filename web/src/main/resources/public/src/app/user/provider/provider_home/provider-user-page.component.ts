/**
 * Created by Anastasiia Fedorak  8/17/16.
 */
import {Component} from "@angular/core";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../../shared/pipes/capitalize-first-letter";
import {Provider} from "../../../../shared/models/provider.interface";
import {PageCreator} from "../../../../shared/services/page.creator.interface";
import {ProviderService} from "../service/provider-service";
import {ROUTER_DIRECTIVES} from "@angular/router";

@Component({
    selector: 'provider-home',
    templateUrl: 'src/app/user/provider/provider_home/provider-user-page.html',
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    providers: [ProviderService],
    directives: [ROUTER_DIRECTIVES],
    styleUrls: ['src/app/house/house.css', 'src/shared/css/loader.css', 'src/shared/css/general.css']
})
export class ProviderUserPageComponent {
    private providers: Provider[];
    private pageCreator: PageCreator<Provider>;
    private pageNumber: number = 1;
    private pageList: Array<number> = [];
    private totalPages: number;
    onlyActive: boolean = true;

    constructor(private _providerService: ProviderService) {
    }

    ngOnInit(): any {
        this.getProvidersByPageNumAndState(this.pageNumber);
    }

    prevPage() {
        this.pageNumber = this.pageNumber - 1;
        this.getProvidersByPageNumAndState(this.pageNumber)
    }

    nextPage() {
        this.pageNumber = this.pageNumber + 1;
        this.getProvidersByPageNumAndState(this.pageNumber)
    }

    emptyArray() {
        while (this.pageList.length) {
            this.pageList.pop();
        }
    }

    preparePageList(start: number, end: number) {
        for (let i = start; i <= end; i++) {
            this.pageList.push(i);
        }
    }

    getProvidersByPageNumAndState(pageNumber: number) {
        this.pageNumber = +pageNumber;
        this.emptyArray();
        return this._providerService.getProvidersByState(this.pageNumber, this.onlyActive)
            .subscribe((data) => {
                    this.pageCreator = data;
                    this.providers = data.rows;
                    this.preparePageList(+this.pageCreator.beginPage,
                        +this.pageCreator.endPage);
                    this.totalPages = +data.totalPages;
                },
                (err) => {
                    console.error(err)
                });
    };
}
