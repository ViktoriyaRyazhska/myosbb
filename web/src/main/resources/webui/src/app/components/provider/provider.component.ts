import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http,Response} from '@angular/http';
import {ProviderService} from './provider.service';
import { LoginService } from '../../shared/login/login.service';
import { ModalDirective } from 'ng2-bootstrap/modal';
import { Router, ActivatedRoute } from '@angular/router';
import { ToasterService } from 'angular2-toaster';
import { TranslateService } from 'ng2-translate';
import * as _ from 'lodash';


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
  @ViewChild('editModal') public editModal:ModalDirective;
  public providerTypes:ProviderType[];
  public periodicities =["ONE_TIME","PERMANENT_DAYLY","PERMANENT_WEEKLY","PERMANENT_MONTHLY","PERMANENT_YEARLY"];
  public providerForAdding;
  private newProvider: Provider = {
        providerId:null,
        name: '',
        description: '',
        logoUrl: '',
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

  private updatedProvider: Provider = {
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
  
  private authRole:string;
  constructor
  (
  private http:Http,
  public providerService:ProviderService,
  public loginService:LoginService,
  private router: Router,
  public toasterService: ToasterService,
  )
  {
    this.user=this.loginService.currentUser;
    this.authRole=this.loginService.getRole();
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

public openAddModal(){
	this.addModal.show();
}

public openEditModal(provider: Provider) {
        this.updatedProvider = provider;
        this.editModal.show();
    }


public  onEditProviderSubmit() {
      this.providerService.editProvider(this.updatedProvider,this.updatedProvider.providerId)
  .subscribe((data) => {
  this.toasterService.pop('success', '', 'Провайдерa було оновлено!');
  this.emptyFields();
  this.editModal.hide();
  this.reLoad();
  },

  (error) => {this.handleErrors(error)} ); 

    }

public onCreateProviderSubmit() {
  this.providerService.addProvider(this.newProvider)
  .subscribe((data) => {
  this.toasterService.pop('success', '', 'Провайдерa було створено!');
  this.emptyFields();
  this.createModal.hide();
  this.reLoad();
  },

  (error) => {this.handleErrors(error)} );  
}

public onAddProviderSubmit() {
  this.providerService.addProviderToOsbb(this.providerForAdding,this.user.osbbId)
  .subscribe((data) => {
    this.toasterService.pop('success', '', 'Провадера було успішно додано до ОСББ!');
   this.addModal.hide();    
   this.reLoad();
   },
  (error) => {this.handleErrors(error)} );  
  
}

 private handleErrors(error) {
   console.log(error);
   this.toasterService.pop('error', '', 'Нажаль, сталась помилка');
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

 public onNavigate(id: number) {
 this.router.navigate(['manager/provider-info', id]);
 }

public openDelModal(provider: Provider) {
        this.delModal.show();
        this.selectedProvider=provider;
}

private reLoad() {
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

public closeDelModal() {
  this.providerService.deleteProvider(this.selectedProvider.providerId)
  .subscribe((data) => {
   this.toasterService.pop('success', '', 'Провайдера успішно видалено!');
  this.reLoad();
  },
  (error) => {this.handleErrors(error)} );  
  this.delModal.hide();
}

}
