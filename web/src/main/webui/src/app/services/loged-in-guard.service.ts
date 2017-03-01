import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { LoginService } from '../shared/login/login.service';

@Injectable()
export class LogedInGuard implements CanActivate {

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {}

  public canActivate(route: ActivatedRouteSnapshot): boolean {
    if (this.loginService.checkLogin()) {
      const role = this.loginService.getRoleFromLocalStorage();
      if (role === route.data['role']) {
        return true;
      } else {
        this.loginService.switchRole(role);
        return false;
      }
    } else {
      this.router.navigate(['./login']);
      return false;
    }
  }
}
