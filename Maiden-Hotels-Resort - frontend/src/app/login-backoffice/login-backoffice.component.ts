import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { BackofficeAuthService } from './backoffice-auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-login-backoffice',
  templateUrl: './login-backoffice.component.html',
  styleUrls: ['./login-backoffice.component.css']
})
export class LoginBackofficeComponent implements OnInit, OnDestroy {

    loginForm: FormGroup;

    public error = '';
    public success = '';
    public loggedIn = false;
    public isloggin = false;

    private subscription: Subscription;

    constructor(private route: ActivatedRoute, private router: Router, private app: AppComponent,
                private backofficeAuth: BackofficeAuthService) { }

    ngOnInit() {

        this.loginForm = new FormGroup({

            username: new FormControl(null, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
            password: new FormControl(null, Validators.required)

          });
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();

        }
    }

    onLogin() {
        this.isloggin = true;
        // send http request
        // console.log(this.loginForm.value.username);
        // console.log(this.loginForm.value.password);
        this.subscription = this.backofficeAuth.authenticate(
            this.loginForm.value.username,
            this.loginForm.value.password
            ).subscribe(responseData => {
                this.isloggin = true;
                this.success = 'Welcome';
                this.error = '';
                const tokenStr = responseData.body;
                localStorage.setItem('token', tokenStr);
                localStorage.setItem('username', this.loginForm.value.username);
              // this.getClientByUsername();
                this.app.refreshSideBar();
                this.goToAllHotels();

          },
          error => {
            this.error = 'Wrong Credentials or the authentication server is not working. ' + error.message;
            this.success = '';
            this.loggedIn = false;
          });
      }

    goToAllHotels() {
        // this.username.emit(localStorage.getItem('username'));
        this.router.navigate(['../', 'hotels', 'all'], {relativeTo: this.route});
    }

}
