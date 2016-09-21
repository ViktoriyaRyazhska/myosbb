import { Component, Output, Input, EventEmitter, OnInit, ViewChild, ElementRef } from '@angular/core';
import {FORM_DIRECTIVES, CORE_DIRECTIVES, FormBuilder, Control, ControlGroup, Validators} from '@angular/common';
import {IOsbb, Osbb} from "../../../../../shared/models/osbb";
import { OsbbDTO } from '../osbb';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {TranslatePipe} from "ng2-translate";
import {CapitalizeFirstLetterPipe} from "../../../../../shared/pipes/capitalize-first-letter";

@Component({
    selector: 'osbb-modal',
    templateUrl: './src/app/admin/components/osbb/osbb_form/osbb-modal.html',
    styleUrls: ['./src/app/admin/components/osbb/osbb_form/osbb-modal.css', './src/shared/css/general.css'],
    directives:[MODAL_DIRECTIVES, FORM_DIRECTIVES, CORE_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class OsbbModalComponent implements OnInit{

    @Output() created: EventEmitter<OsbbDTO>;
    @Output() update: EventEmitter<Osbb>;
    
    @ViewChild('modalWindow')
    modalWindow:ModalDirective;

    osbb:IOsbb;
    isEditing:boolean;
    logoUrl:string;
    name: string;
    description: string;
    address: string;
    district: string;
    available: boolean;
    
    submitAttempt:boolean = false;
    creatingForm: ControlGroup;
    nameControl: Control;
    descriptionControl: Control;
    addressControl: Control;
    districtControl: Control;

     constructor(private builder: FormBuilder, private element: ElementRef) {
        this.created = new EventEmitter<OsbbDTO>();
        this.update = new EventEmitter<Osbb>();
        this.nameControl = new Control('', Validators.required);
        this.descriptionControl = new Control('', Validators.required);
        this.addressControl = new Control('', Validators.required);
        this.districtControl = new Control('', Validators.required);
        this.creatingForm = builder.group({
            nameControl: this.nameControl,
            descriptionControl: this.descriptionControl,
            addressControl: this.addressControl,
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
        this.isEditing = false;
        this.modalWindow.show();  
    }

     openEditModal(osbb:IOsbb) {
        this.isEditing = true;
        this.osbb = osbb;
        this.name = osbb.name;
        this.description = osbb.description;
        this.address = osbb.address;
        this.district = osbb.district;
        this.available = osbb.available;
        this.modalWindow.show();
    }
    
    saveButtonAction(fileInput:any):void {
         this.submitAttempt = true;
         if(this.nameControl.valid && this.descriptionControl.valid 
                                && this.addressControl.valid && this.districtControl.valid) {
            if(this.isEditing) {
                this.editOsbb();
                this.update.emit(this.osbb);
            } else {
               let osbbDTO = new OsbbDTO();
               osbbDTO.osbb = this.createOsbb();
               let fileList: FileList = fileInput.files;
               osbbDTO.file =  fileList.item(0);
               this.created.emit(osbbDTO);
            }
            this.modalWindow.hide();
            this.clearForm();
        }
    }

    createOsbb():IOsbb {
        let osbb = new Osbb();
        osbb.name = this.name;
        osbb.description = this.description;
        osbb.address = this.address;
        osbb.district = this.district;
        osbb.creationDate = new Date();   
        return osbb;
    }

    editOsbb(): void {
           this.osbb.name = this.name;
           this.osbb.description = this.description;
           this.osbb.address = this.address;
           this.osbb.district = this.district;
           this.osbb.available = this.available;
    }

     clearForm(): void {
        this.name='';
        this.description='';
        this.address='';
        this.district='';
        this.submitAttempt = false;
    }
}

