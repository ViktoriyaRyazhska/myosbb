import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http,Response} from '@angular/http';
import {ProviderService} from './provider.service';
import { LoginService } from '../../shared/login/login.service';
import { ModalDirective } from 'ng2-bootstrap/modal';

import { User } from '../../models/user.model';
import { ProviderType } from "../../models/provider.type.interface";
import { Provider } from "../../models/provider.interface";


@Component({
    selector: 'provider',
    templateUrl: 'provider.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss'],
    providers:[ProviderService]
})

export class ProviderComponent implements OnInit {

  public title: string = 'Provider';
  public providers:any;
  private user:User;
  private allProviders:Provider[];
  @ViewChild('createModal') public createModal: ModalDirective;
  @ViewChild('delModal') public delModal: ModalDirective;
  @ViewChild('addModal') public addModal:ModalDirective;
  public providerTypes:ProviderType[];
  public periodicities =["ONE_TIME","PERMANENT_DAYLY","PERMANENT_WEEKLY","PERMANENT_MONTHLY","PERMANENT_YEARLY"];
  public providerForAdding;
  private newProvider: Provider = {
        providerId:null,
        name: '',
        description: '',
        logoUrl: 'empty',
        periodicity:'',
        type: {providerTypeId :null, providerTypeName:''},
        email: '',
        phone: '',
        address: '',
        schedule: '',
        active: false,
        attachments: null
  };

  private selectedProvider: Provider = {
        providerId:null,
        name: '',
        description: '',
        logoUrl: 'empty',
        periodicity:'',
        type: {providerTypeId :null, providerTypeName:''},
        email: '',
        phone: '',
        address: '',
        schedule: '',
        active: false,
        attachments: null
  };
  
  constructor(private http:Http,public providerService:ProviderService,public loginService:LoginService){
    this.user=this.loginService.currentUser;
  }

  public ngOnInit() {
  this.providerService.getProvidersByOsbb(this.user.osbbId).subscribe((data) => {
  this.providers = data; 
  });
  this.providerService.getAllProviderTypes().subscribe((data) => {
  this.providerTypes = data; 
});
this.providerService.getAllProviders().subscribe((data) => {
  this.allProviders = data; 
  });
  
}

 public openCreateModal() {
    this.createModal.show();
}

openAddModal(){
	this.addModal.show();
}

  public onCreateProviderSubmit() {
  this.providerService.addProvider(this.newProvider)
  .subscribe((data) => {this.emptyFields();
  this.createModal.hide();},
  (error) => {this.handleErrors(error)} );  
}

public onAddProviderSubmit() {
  this.providerService.addProviderToOsbb(this.providerForAdding,this.user.osbbId)
  .subscribe((data) => {
  this.addModal.hide();    
  this.providerService.getProvidersByOsbb(this.user.osbbId).subscribe((data) => {
  this.providers = data;  });
   },
  (error) => {this.handleErrors(error)} );  
  
}

 private handleErrors(error) {
   console.log(error);
  }
 private emptyFields() {
  this.newProvider = {
  providerId:null,
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
 }

public openDelModal(provider: Provider) {
        this.delModal.show();
        this.selectedProvider=provider;

}

public closeDelModal() {
  this.providerService.deleteProvider(this.selectedProvider.providerId)
  .subscribe((data) => {
  this.providerService.getProvidersByOsbb(this.user.osbbId).subscribe((data) => {
  this.providers = data; 
  });
  },
  (error) => {this.handleErrors(error)} );  
  this.delModal.hide();
}

}
