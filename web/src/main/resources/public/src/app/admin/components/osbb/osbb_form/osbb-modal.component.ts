import { Component, Output, Input, EventEmitter, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FORM_DIRECTIVES, CORE_DIRECTIVES, FormBuilder, Control, ControlGroup, Validators } from '@angular/common';
import { MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective } from 'ng2-bootstrap/ng2-bootstrap';
import { TranslatePipe } from "ng2-translate";
import { CapitalizeFirstLetterPipe } from "../../../../../shared/pipes/capitalize-first-letter";
import { IOsbb, Osbb } from "../../../../../shared/models/osbb";
import { Attachment } from "../../../../../shared/models/attachment";
import { OsbbDTO } from '../osbb';

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
        this.osbbDTO = new OsbbDTO();
        this.created = new EventEmitter<OsbbDTO>();
        this.update = new EventEmitter<OsbbDTO>();
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
        this.logoUrl = null;
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
        if(osbb.logo !== null ) {
            this.logoUrl = osbb.logo.url;
        } else {
             this.logoUrl = '';
        }
        this.modalWindow.show();
    }
    
    saveButtonAction(fileInput:any):void {
         this.submitAttempt = true;
         if(this.nameControl.valid && this.descriptionControl.valid 
                                && this.addressControl.valid && this.districtControl.valid) {
            let fileList: FileList = fileInput.files;
            if(this.osbbDTO.isChanged) {
                console.log("file was changed");
                 this.osbbDTO.file =  fileList.item(0);
            }
            if(this.isEditing) {
                console.log("isEditing");
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
        osbb.address = this.address;
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
           this.osbb.address = this.address;
           this.osbb.district = this.district;
           this.osbb.available = this.available;
    }

     clearForm(): void {
        this.name='';
        this.description='';
        this.address='';
        this.district='';
        this.inputLogo.nativeElement.value = '';
        this.logoUrl='';
        this.submitAttempt = false;
    }
}
