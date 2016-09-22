import { Component, Output, Input, EventEmitter, OnInit, ViewChild } from '@angular/core';
import {CORE_DIRECTIVES} from '@angular/common';
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, ModalDirective} from 'ng2-bootstrap/ng2-bootstrap';
import {TranslatePipe} from "ng2-translate";

import {CapitalizeFirstLetterPipe} from "../../../../../shared/pipes/capitalize-first-letter";
import {IOsbb, Osbb} from "../../../../../shared/models/osbb";

@Component({
    selector: 'osbb-del-form',
    templateUrl: './src/app/admin/components/osbb/osbb_form/osbb-del-form.html',
    styleUrls: ['./src/shared/css/general.css'],
    directives:[MODAL_DIRECTIVES, CORE_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe]
})
export class OsbbDelFormComponent {

    @Output() delete: EventEmitter<IOsbb>;
    @ViewChild('delModal')
    delModal:ModalDirective;

    private osbb: IOsbb;
    name:string;
    submitAttempt:boolean = false;

    constructor() {
        this.delete = new EventEmitter<IOsbb>();
    }

    openDelModal(osbbId:IOsbb): void {
        this.osbb = osbbId;
        this.delModal.show();   
    }

    isNameEquals():boolean {
        return this.osbb.name === this.name;
    }

    hideDelModal(): void {
        this.delModal.hide();   
    }

    clearDelModal() {
        this.name = '';
        this.submitAttempt = false;
    }

    confirmDeleting(): void {
        this.submitAttempt = true;
        if (this.isNameEquals()) {
            this.delete.emit(this.osbb);
            this.hideDelModal();
            this.clearDelModal();
        }
    }
}