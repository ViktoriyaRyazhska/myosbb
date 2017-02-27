import {
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { ContractsService } from './contracts.service';
import { LoginService } from '../../shared/login/login.service';
import { ModalDirective } from 'ng2-bootstrap/modal';

@Component({
  selector: 'contract',
  styleUrls: [
    '../../../assets/style/page.layout.scss',
    './contracts.style.scss'
  ],
  templateUrl: './contracts.component.html',
  providers: [
    ContractsService,
    LoginService
  ]
})
export class ContractsComponent implements OnInit {
  @ViewChild('delModal') public delModal: ModalDirective;
  @ViewChild('editModal') public editModal: ModalDirective;
  @ViewChild('createModal') public createModal: ModalDirective;
  public resData: any;
  public title: string = 'Contracts';

  constructor(
    public login: LoginService,
    public contract: ContractsService
  ) { }

  public ngOnInit() {
    this.contract.getContractsData()
      .subscribe((response) => {
        this.resData = response.rows;
      });
  };

  public openEditModal() {
    this.editModal.show();
  };

  public openCreateModal() {
    this.createModal.show();
  };

  public openDelModal(id: number) {
    this.delModal.show();
  };

  public closeEditModal() {
    this.editModal.hide();
  };

  public onSearch(search: string) {
    this.contract.findByProviderName(search)
      .subscribe((response) => {
        this.resData = response;
      });
  };
}
