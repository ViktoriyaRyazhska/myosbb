import {CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {LoginService} from "../../app/login/login.service";
import {CurrentUserService} from "../services/current.user.service";

@Injectable()
export class ManagerLoginGuard implements CanActivate {

    constructor(private _loginService: LoginService,
                private _currentUserService: CurrentUserService,
                private _router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean>|boolean {
        if (this._loginService.checkLogin() && this._currentUserService.getRole() == "ROLE_MANAGER") {
            return true;
        }

        this._router.navigate(['/login']);
        return false;

    }

}