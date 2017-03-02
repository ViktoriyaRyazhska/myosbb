import {
    Component,
    OnInit,
      ViewChild
} from '@angular/core';
import {
    Http,
    Response
} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { ApartmentService } from './apartment.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from '../../shared/login/login.service';
import { ModalDirective } from 'ng2-bootstrap/modal';

@Component({
    selector: 'apartments',
    templateUrl: 'apartment.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './apartment.scss'],
    providers: [ApartmentService, LoginService]
})

export class ApartmentComponent implements OnInit {
    
  @ViewChild('createModal') public createModal: ModalDirective;

    public title: string = `Apartments`;
    public resData: any;
    private admin: boolean;
    private authRole: string;

    constructor(
        public http: Http,
        public apartment: ApartmentService,
        public LoginService: LoginService,
        private router: Router
    ) {}
    public onNavigate(id: number) {
        if (this.authRole === 'ROLE_ADMIN') {
            this.router.navigate(['admin/apartment', id]);
            return;
        }
        this.router.navigate(['manager/apartment', id]);
    }
    public ngOnInit() {
        this.apartment.getApartmentData().subscribe((data) => {
            this.resData = data;
        });
        this.LoginService.setRole();
        this.authRole = this.LoginService.getRole();
    }

     public openCreateModal() {
    this.createModal.show();
  };
}
