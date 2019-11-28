import { Component, OnInit, Output, EventEmitter, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Client } from '../clients/clients.model';
import { ClientsService } from '../clients/clients.service';
import { Guest} from '../guests/guests.model';
import { GuestsService} from '../guests/guests.service';
import { DatePipe } from '@angular/common';
import { ClientAuthService } from './client-auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login-client',
  templateUrl: './login-client.component.html',
  styleUrls: ['./login-client.component.css']
})
export class LoginClientComponent implements OnInit, OnDestroy {
  registerForm: FormGroup;
  loginForm: FormGroup;

  public error = '';
  public success = '';
  public loggedIn = false;

  private subscrition: Subscription;



  constructor(private datepipe: DatePipe, private clientsService: ClientsService,
              private guestsService: GuestsService, private clientAuth: ClientAuthService,
              private route: ActivatedRoute, private router: Router, private app: AppComponent) { }

  ngOnInit() {

    this.registerForm = new FormGroup({
      'registerFirstName' : new FormControl(null, [Validators.required, Validators.pattern("^[A-ZÀ-Ù]*[a-zà-ú]*$")]),
      'registerLastName' : new FormControl(null, [Validators.required, Validators.pattern("^[A-ZÀ-Ù]*[a-zà-ú]*$")]),
      'registerDateBirth' : new FormControl(null, Validators.required),
      'registerGender' : new FormControl(null, Validators.required),
      'registerAddress' : new FormControl(null, [Validators.required, Validators.pattern("^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$")]),
      'registerIdNumber' : new FormControl(null, [Validators.required, Validators.maxLength(16), Validators.minLength(8),Validators.pattern("^[0-9]*[0-9]$")]),
      'registerContactNumber' : new FormControl(null, [Validators.required, Validators.maxLength(9), Validators.minLength(9), Validators.pattern("^[0-9]*[0-9]$")]),
      'registerEmail' : new FormControl(null, [Validators.required, Validators.email]),
      'registerPassword' : new FormControl(null, Validators.required),
      'registerPassword2': new FormControl(null, Validators.required)
    });

    this.loginForm = new FormGroup({

        email: new FormControl(null, [Validators.required, Validators.email]),
        password: new FormControl(null, Validators.required)

      });

  }

  ngOnDestroy() {
      if (this.subscrition) {
        this.subscrition.unsubscribe();

      }
  }

  onRegisterClient(){
    // console.log('onRegisterClient');
    // send http request
    this.subscrition = this.guestsService.createAndStoreGuest(
      this.registerForm.value.registerFirstName,
      this.registerForm.value.registerLastName,
      this.registerForm.value.registerDateBirth,
      this.registerForm.value.registerGender,
      this.registerForm.value.registerIdNumber,
      this.registerForm.value.registerAddress,
      this.registerForm.value.registerContactNumber,
      this.registerForm.value.registerEmail,
      'Inactive' ).subscribe(responseData => {
        // console.log(responseData);
        if (responseData === -1) {
            this.error = 'Something went wrong inserting the guest...';
            this.success = null;
        } else {
          this.success = 'Guest registred!';
          this.clientsService.createAndStoreClient(null,
            Number(responseData),
            this.registerForm.value.registerPassword,
            0

          ).subscribe( responseData2 => {
              // console.log(responseData2);
              if (responseData2 === -1) {
                this.error = 'Something went wrong inserting the Client...';
                this.success = null;
              } else {
                this.success = 'Client registred!';
                this.error = null;

              }

            },
            error => {
                this.error = error.message;
            });
        }


      },
      error => {
          this.error = error.message;
      });
  }

  onLogin() {
    // send http request
    // console.log(this.loginForm.value.email);
    // console.log(this.loginForm.value.password);
    this.subscrition = this.clientAuth.authenticate(
      this.loginForm.value.email,
      this.loginForm.value.password
      ).subscribe(responseData => {
          this.success = 'Welcome';
          this.error = '';
          const tokenStr = responseData.body;
          localStorage.setItem('token', tokenStr);
          localStorage.setItem('username', this.loginForm.value.email);
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
