/**
 * Created by Anastasiia Fedorak  8/2/16.
 */
import {Component, ViewChild} from "@angular/core";
import {CapitalizeFirstLetterPipe} from "../../../shared/pipes/capitalize-first-letter";
import {TranslatePipe} from "ng2-translate/ng2-translate";
import {DROPDOWN_DIRECTIVES} from "ng2-bs-dropdown/dropdown";
import {ProviderService} from "./service/provider-service";
import {PageCreator} from "../../../shared/services/page.creator.interface";
import {MODAL_DIRECTIVES, BS_VIEW_PROVIDERS, BUTTON_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';
import {ModalDirective} from "ng2-bootstrap/ng2-bootstrap";
import {CORE_DIRECTIVES, NgClass} from "@angular/common";
import {SELECT_DIRECTIVES} from "ng2-select/ng2-select";
import {ROUTER_DIRECTIVES} from "@angular/router";
import {ProviderTypeComponent} from "./provider_type/provider-type.component";
import {Provider} from "../../../shared/models/provider.interface";
import {Mail} from "../../../shared/models/mail.interface";
import {MailService} from "../../../shared/services/mail.sender.service";
import {SelectItem} from "../../../shared/models/ng2-select-item.interface";
import {HeaderComponent} from "../../header/header.component";
import {PeriodicityItems} from "../../../shared/models/periodicity.const";
import {FORM_DIRECTIVES} from "@angular/forms";
import MaskedInput from 'angular2-text-mask';
import {FileUploader, FileSelectDirective, FileDropDirective} from "ng2-file-upload";
import ApiService = require("../../../shared/services/api.service");

import FileLocationPath = require("../../../shared/services/file.location.path");
import {AttachmentComponent} from "../../attachment/attachment.component";
const attachmentUploadUrl = ApiService.serverUrl + '/restful/attachment/';

declare let saveAs: any;


@Component({
    selector: 'myosbb-provider',
    templateUrl: 'src/app/user/provider/provider-table.html',
    pipes: [TranslatePipe, CapitalizeFirstLetterPipe],
    providers: [ProviderService, MailService],
    directives: [MODAL_DIRECTIVES, CORE_DIRECTIVES, ROUTER_DIRECTIVES, ProviderTypeComponent, AttachmentComponent,
        SELECT_DIRECTIVES, NgClass, FORM_DIRECTIVES, BUTTON_DIRECTIVES, MaskedInput, FileSelectDirective, FileDropDirective, DROPDOWN_DIRECTIVES],
    viewProviders: [BS_VIEW_PROVIDERS],
    styleUrls: ['src/app/user/bills/bill.css', 'src/shared/css/loader.css', 'src/shared/css/general.css']
})
export class ProviderComponent {
    public phoneMask = ['(', /[0]/, /\d/, /\d/, ')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];
    public textMask = [/[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/];
    private providers: Provider[];
    private selected: Provider = {
        providerId: null,
        name: '',
        description: '',
        logoUrl: '',
        periodicity: '',
        type: {providerTypeId: null, providerTypeName: ''},
        email: '',
        phone: '',
        address: '',
        schedule: '',
        active: false,
        attachments: null
    };
    private newProvider: Provider = {
        providerId: null,
        name: '',
        description: '',
        logoUrl: '',
        periodicity: '',
        type: {providerTypeId: null, providerTypeName: ''},
        email: '',
        phone: '',
        address: '',
        schedule: '',
        active: false,
        attachments: null
    };
    private pageCreator: PageCreator<Provider>;
    private pageNumber: number = 1;
    private pageList: Array<number> = [];
    private totalPages: number;

    @ViewChild('delModal') public delModal: ModalDirective;
    @ViewChild('editModal') public editModal: ModalDirective;
    @ViewChild('createModal') public createModal: ModalDirective;
    active: boolean = true;
    order: boolean = true;
    onlyActive: boolean = true;

    private shouldRun: boolean = true;
    private upload: boolean = false;

    private periodicities: SelectItem[] = [];

    private mail: Mail = {to: '', subject: '', text: ''};

    public uploader: FileUploader = new FileUploader({
        url: attachmentUploadUrl,
        authToken: 'Bearer ' + localStorage.getItem('access_token')
    });
    public hasDropZoneOver: boolean = false;

    public fileOverBase(e: any): void {
        this.hasDropZoneOver = e;
    }

    constructor(private _providerService: ProviderService, private _mailService: MailService) {
    }

    ngOnInit(): any {
        for (let i = 0; i < PeriodicityItems.length; i++) {
            this.periodicities.push(PeriodicityItems[i]);
        }
        this.getPeriodicitiesTranslation();
        this.getProvidersByPageNumAndState(this.pageNumber);
    }

    setType(event) {
        this.selected.type = event;
        this.newProvider.type = event;
    }

    public onSelectPeriodicity(value: SelectItem): void {
        this.selected.periodicity = this.backToConst(value);
        this.newProvider.periodicity = this.backToConst(value);
    }

    public onRefresh(value: SelectItem): void {
        if (value.text != null)
            this.selected.periodicity = value.text;
    }

    openEditModal(provider: Provider) {
        this.selected = provider;
        this.upload = false;
        this.editModal.show();
    }

    closeEditModal() {
        this.editModal.hide();
        setTimeout(() => this.active = true, 0);
    }

    onEditProviderSubmit() {
        this.active = false;
        if (this.shouldRun) {
            this._providerService.editProvider(this.selected).subscribe(() => {
                    this.shouldRun = false;
                    this.refresh();
                },
                (err) => {
                    console.error(err)
                }
            );
            this.editModal.hide();
            setTimeout(() => this.active = true, 0);
        }

    }

    openDelModal(provider: Provider) {
        this.selected = provider;
        this.upload = false;

        this.delModal.show();
    }

    closeDelModal() {
        this.active = false;
        if (this.shouldRun) {
            this._providerService.makeProviderInactive(this.selected).subscribe(() => {
                    this.shouldRun = false;
                    this.refresh();
                },
                (err) => {
                    console.error(err)
                }
            );
            this.delModal.hide();
        }
    }

    getPeriodicitiesTranslation() {
        for (let i = 0; i < this.periodicities.length; i++) {
            HeaderComponent.translateService.get(this.periodicities[i].text)
                .subscribe((data: string) => {
                    this.periodicities[i].text = data;
                })
        }
    }

    backToConst(item: SelectItem): string {
        let items: SelectItem[] =
            [{id: 1, text: 'ONE_TIME'},
                {id: 2, text: 'PERMANENT_DAYLY'},
                {id: 3, text: 'PERMANENT_WEEKLY'},
                {id: 4, text: 'PERMANENT_MONTHLY'},
                {id: 5, text: 'PERMANENT_YEARLY'}];
        for (let i = 0; i < items.length; i++) {
            if (item.id === items[i].id) {
                return items[i].text;
            }
        }
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

    sortBy(name: string) {
        this.order = !this.order;
        this.emptyArray();
        this._providerService.getSortedActiveProviders(this.pageNumber, name, this.order, this.onlyActive)
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
    }

    onSearch(search: string) {
        this._providerService.findProviderByNameOrDescription(search)
            .subscribe((providers) => {
                this.providers = providers;
            });
    }

    onSendMessage() {
        this.mail = {to: "aska.fed@gmail.com", subject: "TEST", text: "Success!"};
        this._mailService.sendMail(this.mail);
    }

    onCreateProviderSubmit() {
        this.active = false;
        if (this.shouldRun) {
            this._providerService.addProvider(this.newProvider)
                .subscribe(() => {
                        this.shouldRun = false;
                        this.active = true;
                        this.refresh();
                        this.emptyFields();
                    },
                    (err) => {
                        console.error(err)
                    }
                );
        }
        let mail: Mail = {
            to: this.newProvider.email,
            subject: 'PRIVET',
            text: 'Welcome on the board'
        };
        if (this.newProvider.email !== null) {
            this._mailService.sendMail(mail)
        }
        this.createModal.hide();
        setTimeout(() => this.active = true, 0);
    }

    closeCreateModal() {
        this.createModal.hide();
    }

    openCreateModal() {
        this.upload = false;
        this.createModal.show();
    }

    getProvidersByPageNumAndState(pageNumber: number) {
        this.pageNumber = +pageNumber;
        this.emptyArray();
        this.shouldRun = true;
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

    onOnlyActive() {
        this.onlyActive = !this.onlyActive;
        this.getProvidersByPageNumAndState(this.pageNumber);
    }

    getProvidersByPageNum(num) {
        this.getProvidersByPageNumAndState(num);
    }

    refresh() {
        this.getProvidersByPageNumAndState(this.pageNumber);
    }

    emptyFields() {
        this.newProvider = {
            providerId: null,
            name: '',
            description: '',
            logoUrl: '',
            periodicity: '',
            type: {providerTypeId: null, providerTypeName: ''},
            email: '',
            phone: '',
            address: '',
            schedule: '',
            active: false
        };
    }

    showUploading() {
        this.upload = !this.upload;
    }

    transform(bytes) {
        if (bytes == 0) return '0 Bytes';
        let k = 1000;
        let sizes = ['Bytes', 'KB', 'MB', 'GB'];
        let i = Math.floor(Math.log(bytes) / Math.log(k));
        return (bytes / Math.pow(k, i)).toFixed(1) + ' ' + sizes[i];
    }

    setAttachments(event) {
        this.selected.attachments = event;
    }

    setNewProviderAttachments(event) {
        this.newProvider.attachments = event;
    }

    setLogo(event) {
        this.selected.logoUrl = event;
    }

    setNewProviderLogo(event) {
        this.newProvider.logoUrl = event;
    }
}
