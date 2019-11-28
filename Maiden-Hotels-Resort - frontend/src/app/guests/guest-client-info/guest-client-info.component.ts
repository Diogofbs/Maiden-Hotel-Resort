import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { GuestsService } from '../guests.service';
import { Guest } from '../guests.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ClientsService } from 'src/app/clients/clients.service';
import { Client } from 'src/app/clients/clients.model';
import { formatDate } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-guest-client-info',
  templateUrl: './guest-client-info.component.html',
  styleUrls: ['./guest-client-info.component.css']
})
export class GuestClientInfoComponent implements OnInit, OnDestroy {

    public guest: Guest;
    public client: Client;
    public editForm: FormGroup;
    public passwordForm: FormGroup;

    public error = '';
    public success = '';

    private subscription: Subscription;

  constructor(private app: AppComponent, private guestsService: GuestsService, private clientsService: ClientsService) {
    this.guest = this.app.guest;
  }

  ngOnInit() {

    if (this.guest) {

        const dateBirth = new Date(this.guest.dateOfBirth);
        const date = formatDate(dateBirth, 'yyyy-MM-dd', 'en');
        this.editForm = new FormGroup({
            'guestFirstName' : new FormControl(this.guest.firstName, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
            'guestLastName' : new FormControl(this.guest.lastName, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
            'guestDateBirth' : new FormControl(date, Validators.required),
            'guestGender' : new FormControl(this.guest.gender, Validators.required),
            'guestIdNumber' : new FormControl(this.guest.idNumber,
                [Validators.required, Validators.maxLength(16),Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
            'guestAddress' : new FormControl(this.guest.address,
                [Validators.required, Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
            'guestContactNumber' : new FormControl(this.guest.contactNumber,
                [Validators.required, Validators.maxLength(9), Validators.pattern('^[0-9]*[0-9]$')]),
            'guestEmail' : new FormControl(this.guest.email, [Validators.required, Validators.email]),
          });

        this.fetchClient();

        this.passwordForm = new FormGroup({
            new_password: new FormControl('', Validators.required),
            new_password_again: new FormControl('', Validators.required)
        });
    }

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  updateGuest() {

    // console.log('onUpdateGuest');
    // console.log(this.editForm);
    // send http request
    this.subscription = this.guestsService.updateGuest(
        this.guest.id,
      this.editForm.value.guestFirstName,
      this.editForm.value.guestLastName,
      this.editForm.value.guestDateBirth,
      this.editForm.value.guestIdNumber,
      this.editForm.value.guestAddress,
      this.editForm.value.guestContactNumber,
      this.editForm.value.guestGender,
      this.editForm.value.guestEmail,
      ''
      ).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'Count updated updated!';
        this.error = '';
        this.app.fetchClientLog();

        return new Promise(resolve => {
            setTimeout(() => {
                this.guest = this.app.guest;
                // console.log(this.guest);
            }, 1000);
        });

      },
      error => {
          this.error = error.message;
          this.success = '';
      });

  }

  updatePassword() {

    if (this.passwordForm.value.new_password === this.passwordForm.value.new_password_again) {
        // console.log('onUpdateClient');
        // send http request
        this.subscription = this.clientsService.updateClient(
          this.client.id,
          this.guest.id,
          this.passwordForm.value.new_password
          ).subscribe(responseData => {
            // console.log(responseData);
            this.success = 'Client updated!';
            this.app.onLogout();
          },
          error => {
              this.error = error.message;
          });
    } else {
        this.error = 'new password was not type correctly';
    }

  }

  private fetchClient() {

    this.subscription = this.clientsService.getClient(this.guest.id).subscribe(clients => {
      this.client = null;
      this.client = new Client(clients[0].id, clients[0].idGuest, null, null,  clients[0].moneySpent);
      // console.log(this.client);

    },
    error => {
        this.error = error.message;
    });

  }

}
