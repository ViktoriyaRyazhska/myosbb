import { Component, Output, Input, EventEmitter, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FORM_DIRECTIVES, CORE_DIRECTIVES, FormBuilder, Control, ControlGroup, Validators } from '@angular/common';
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective } from 'ng2-bootstrap/ng2-bootstrap';
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../../../shared/pipes/capitalize-first-letter";
import { IOsbb, Osbb } from "../../../../../shared/models/osbb";
import { Attachment } from "../../../../../shared/models/attachment";
import { OsbbDTO } from '../osbb';
import { SELECT_DIRECTIVES } from "ng2-select";
import { Region, Street, City, District } from "../../../../../shared/models/addressDTO";
import { AddressService } from '../../../../../shared/services/address.service';

@Component({
    selector: 'osbb-modal',
    templateUrl: './src/app/admin/components/osbb/osbb_form/osbb-modal.html',
    styleUrls: ['./src/app/admin/components/osbb/osbb_form/osbb-modal.css', './src/shared/css/general.css'],
    directives:[MODAL_DIRECTIVES, FORM_DIRECTIVES, CORE_DIRECTIVES, SELECT_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    providers: [AddressService],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class OsbbModalComponent implements OnInit{

    @Output() created: EventEmitter<OsbbDTO>;
    @Output() update: EventEmitter<OsbbDTO>;
    
    @ViewChild('modalWindow')
    modalWindow:ModalDirective;

    @ViewChild('inputLogo') 
    inputLogo: any;

    osbbDTO: OsbbDTO;
    osbb:IOsbb;
    isEditing:boolean;
    logoUrl:string;
    name: string;
    description: string;
    street: Street;
    houseNumber: string;
    district: District;
    available: boolean;
    regions: Array<Region>;
    cities: Array<City>;
    streets: Array<Street>;
    districts: Array<District>;
    currentDistrict: District;
    currentStreet: Street;
    currentRegion: Region;
    currentCity: City;
    submitAttempt:boolean = false;
    creatingForm: ControlGroup;
    nameControl: Control;
    descriptionControl: Control;
    regionControl: Control;
    cityControl: Control;
    streetControl: Control;
    houseNumberControl: Control;
    districtControl: Control;

     constructor(private builder: FormBuilder, private element: ElementRef, 
                 private addressService: AddressService) {
        this.osbbDTO = new OsbbDTO();
        this.created = new EventEmitter<OsbbDTO>();
        this.update = new EventEmitter<OsbbDTO>();
        this.nameControl = new Control('', Validators.required);
        this.descriptionControl = new Control('', Validators.required);
        this.regionControl = new Control('', Validators.required);
        this.cityControl = new Control('', Validators.required);
        this.streetControl = new Control('', Validators.required);
        this.houseNumberControl = new Control('', Validators.required);
        this.districtControl = new Control('', Validators.required);
        this.creatingForm = builder.group({
            nameControl: this.nameControl,
            descriptionControl: this.descriptionControl,
            regionControl: this.regionControl,
            cityControl: this.cityControl,
            streetControl: this.streetControl,
            houseNumberControl: this.houseNumberControl,
            districtControl: this.districtControl,
        });

    }

     ngOnInit() {
        if(this.osbb === undefined){
            this.osbb = new Osbb();
        }
    }

    showLogo(event) {
        var reader = new FileReader();
        var image = this.element.nativeElement.querySelector('.image');
        reader.onload = function(e) {
            var src = e.target.result;
            image.src = src;
        };
        reader.readAsDataURL(event.target.files[0]);
    }

    openAddModal() {
        this.logoUrl = null;
        this.isEditing = false;
        this.regions = [];
        this.cities = [];
        this.streets = [];
        this.districts = [];
        this.currentRegion = null;
        this.currentCity = null;
        this.currentStreet = null;
        this.currentDistrict = null;
        this.fillRegions();
        this.modalWindow.show();  
    }

     openEditModal(osbb:IOsbb) {
        this.isEditing = true;
        this.osbb = osbb;
        this.name = osbb.name;
        this.description = osbb.description;
        this.street = osbb.street;
        this.houseNumber = osbb.houseNumber;
        this.district = osbb.district;
        this.available = osbb.available;
        if(osbb.logo !== null ) {
            this.logoUrl = osbb.logo.url;
        } else {
             this.logoUrl = '';
        }
        this.regions = [];
        this.cities = [];
        this.streets = [];
        this.districts = [];
        this.currentRegion = null;
        this.currentCity = null;
        this.currentStreet = null;
        this.currentDistrict = null;
        this.fillRegions();
        if (this.street != null) {
            this.fillCities(this.street.city.region.id);
            this.fillStreets(this.street.city.id);
            this.fillDistricts(this.street.city.id);
        }
        this.modalWindow.show();
    }
    
    saveButtonAction(fileInput:any):void {
         this.submitAttempt = true;
         if(this.nameControl.valid && this.descriptionControl.valid 
                                && this.houseNumberControl.valid) {
            let fileList: FileList = fileInput.files;
            if(this.osbbDTO.isChanged) {
                 this.osbbDTO.file =  fileList.item(0);
            }
            if(this.isEditing) {
                this.editOsbb();
                this.osbbDTO.osbb = this.osbb;
                this.update.emit(this.osbbDTO);
                
            } else {
               this.osbbDTO.osbb = this.createOsbb();
               this.created.emit(this.osbbDTO);
            }
            this.modalWindow.hide();
            this.clearForm();
        }
    }

    createOsbb():IOsbb {
        let osbb = new Osbb();
        osbb.name = this.name;
        osbb.description = this.description;
        osbb.street = this.street;
        osbb.houseNumber = this.houseNumber;
        osbb.district = this.district;
        osbb.creationDate = new Date();   
        return osbb;
    }

    toggleChangeLogo() {
        this.osbbDTO.isChanged = true;
    }

    editOsbb(): void {
           this.osbb.name = this.name;
           this.osbb.description = this.description;
           this.osbb.street = this.street;         
           this.osbb.houseNumber = this.houseNumber;
           this.osbb.district = this.district;
           this.osbb.available = this.available;
    }

    fillRegions(): void {
        this.addressService.getAllRegions().subscribe((data)=> {
             this.regions = data;
             if (this.street != null) {
                for (let reg of this.regions) {
                    if (this.street.city.region.id == reg.id) {
                        this.currentRegion = reg;
                    }
                }
             }
             this.currentCity = null;
             this.currentStreet = null;
             this.currentDistrict = null;
             }, (error)=> {
                 this.handleErrors(error)
             });
    }
   
    fillCities(regionID: number): void {
        this.addressService.getAllCitiesOfRegion(regionID).subscribe((data)=> {
             this.cities = data;
             if (this.street != null) {
                for (let cit of this.cities) {
                    if (this.street.city.id == cit.id) {
                        this.currentCity = cit;
                    }
                }
             }
             this.currentStreet = null;
             this.currentDistrict = null;
             }, (error)=> {
                 this.handleErrors(error)
             });
    }

    fillStreets(cityID: number): void {
        this.addressService.getAllStreetsOfCity(cityID).subscribe((data)=> {
             this.streets = data;
             if (this.street != null) {
                for (let str of this.streets) {
                    if (this.street.id == str.id) {
                        this.currentStreet = str;
                    }
                }
             }
             }, (error)=> {
                 this.handleErrors(error)
             });
    }

    fillDistricts(cityID: number): void {
        this.addressService.getAllDistrictsOfCity(cityID).subscribe((data)=> {
             this.districts = data;
             if (this.district != null) {
                for (let ds of this.districts) {
                    if (this.district.id == ds.id) {
                        this.currentDistrict = ds;
                    }
                }
             }
             }, (error)=> {
                 this.handleErrors(error)
             });
    }

    selectedRegion(value: any): void {
        this.fillCities(value.id);
        this.currentCity = null;
        this.streets = [];
        this.currentStreet = null;
        this.districts = [];
        this.currentDistrict = null;
    }

    selectedCity(value: any): void {
        this.fillStreets(value.id);
        this.currentStreet = null;
        this.fillDistricts(value.id);
        this.currentDistrict = null;
    }

    selectedStreet(value: any): void {
        this.addressService.getStreetById(value.id).subscribe((data)=> {
             this.street = data;
             }, (error)=> {
                 this.handleErrors(error)
             });
    }

    selectedDistrict(value: any): void {
        this.addressService.getDistrictById(value.id).subscribe((data)=> {
             this.district = data;
             }, (error)=> {
                 this.handleErrors(error)
             });
    }

     clearForm(): void {
        this.name='';
        this.description='';
        this.street = null;
        this.houseNumber='';
        this.district=null;
        this.inputLogo.nativeElement.value = '';
        this.logoUrl='';
        this.submitAttempt = false;
    }
    handleErrors(error) {
        console.log('error msg' + error)
    }
}
