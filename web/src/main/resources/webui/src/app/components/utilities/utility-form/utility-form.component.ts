import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { NgModel, FormControl, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Http, Response, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ModalDirective } from 'ng2-bootstrap';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { LoginService } from '../../../shared/login/login.service';
import { UtilitiesService } from '../utilities.service';

import { Utility } from '../../../models/utility.model';
import { Osbb } from '../../../models/osbb.model';
import { House } from '../../../models/house.model';



@Component({
    selector: 'utility-form',
    templateUrl: 'utility-form.component.html',
    styleUrls: ['../../../../assets/css/manager.page.layout.scss'],
    providers: [UtilitiesService]

})
export class UtilityFormComponent implements OnInit {


    @ViewChild('modalWindow') modalWindow: ModalDirective;

    @Output() created: EventEmitter<Utility>;

    private loginForm: FormGroup;
    private utilities: Utility[];
    private utility: Utility;
    private osbbs: Array<Osbb>;
    private houses: House[];
    private houseSelected: House[] = [];
    private houseItems: string[] = [];
    private nameError: boolean = false;
    private houseError: boolean = false;
    private submit: boolean = true;

    constructor(public fb: FormBuilder, public utilityService: UtilitiesService, private LoginService: LoginService) {
        this.utility = new Utility();
        this.created = new EventEmitter<Utility>();
    }


    ngOnInit(): void {
        this.listAllUtilities();
        this.listAllOsbbs();
        this.buildForm();
        this.listAllHouses();
        console.log('Utility-Form OnInit');
    }

    onSubmit(loginForm: FormGroup) {
        this.utility.name = loginForm.value.name;
        this.utility.price = loginForm.value.price;
        this.utility.description = loginForm.value.description;
        this.utility.priceCurrency = 'UAH';

        this.utilityService.saveUtility(this.utility).subscribe((data) => {
            this.created.emit(data);
        },
            (error) => {
                this.handleError(error);
            });
        console.log(this.utility)
        this.closeModal();
        this.buildForm();
    }

    buildForm(): void {
        this.loginForm = this.fb.group({
            name: ['', [Validators.required]],
            description: ['', [Validators.required]],
            price: [0, [Validators.required]],
            houses: [null, [Validators.required]]

        });
    }

    listAllUtilities() {
        this.utilityService.listAllUtilitiesByOsbb().subscribe((data) => {
            this.utilities = data;
        },
            (error) => {
                this.handleError(error);
            });
    }

    listAllHouses() {
        this.utilityService.listAllHouses().subscribe((data) => {
            this.houses = data;
            this.houseItems = this.fillHousesItems();
        },
            (error) => {
                this.handleError(error);
            });
    }

    listAllOsbbs() {
        this.utilityService.listAllOsbbs().subscribe((data) => {
            this.osbbs = data;
        },
            (error) => {
                this.handleError(error);
            });
    }

    fillHousesItems(): string[] {
        let opts = new Array(this.houses.length);
        for (let house of this.houses) {
            opts.push({
                id: house.houseId,
                text: house.street.name + ' - ' + house.numberHouse.toString()
            })
        }
        return opts.slice(0);
    }

    selectedHouse(value: any) {
        console.log(value);
        let house: House = this.findHousebyId(value.id);
        this.houseSelected.push(house);
        this.utility.houses = this.houseSelected;
    }

    removedHouse(value: any) {
        console.log(value);
        let house: House = this.findHousebyId(value.id);
        this.houseSelected.splice(this.houseSelected.indexOf(house, 0), 1);
        this.utility.houses = this.houseSelected;
    }


    findHousebyId(id: number) {
        for (let house of this.houses) {
            if (house.houseId === id) {
                return house;
            }
        }
    }

    setManagerOsbb() {
        for (let osb of this.osbbs) {
            if (osb.osbbId === this.LoginService.currentUser.osbbId) {
                this.utility.osbb = osb;
                console.log(osb.osbbId + 'added');
            }
        }

    }

    onClear() {
        this.utility = new Utility();
        this.houseSelected = [];
        this.buildForm();
    }

    openModal(parent: Utility) {
        if (parent !== undefined) {
            this.utility.parent = parent;
        }
        this.listAllUtilities();
        this.listAllOsbbs();
        this.listAllHouses();
        this.setManagerOsbb();
        this.modalWindow.show();


    }
    closeModal() {
        this.utility = new Utility();
        this.houseSelected = [];
        this.nameError = false;
        this.submit = false;
        this.buildForm();
        this.modalWindow.hide();
    }

    public checkName(event: any) {
        this.nameError = this.loginForm.value.name.length <= 5 ? true : false;
        this.submit = this.nameError;
    }

    private handleError(error: any): Promise<any> {
        console.log('HandleError', error);
        return Promise.reject(error.message || error);
    }

}