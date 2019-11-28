import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Guest } from './guests.model';
import { GuestsService } from './guests.service';
import { DatePipe } from '@angular/common';
import { AppComponent } from '../app.component';
import { Backoffice } from '../backoffices/models/backoffice.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-guests',
  templateUrl: './guests.component.html',
  styleUrls: ['./guests.component.css']
})
export class GuestsComponent implements OnInit, OnDestroy {

  insertForm: FormGroup;
  editForm: FormGroup;
  deleteForm: FormGroup;

  public isFetching = false;
  public error = '';
  public success = '';

  public guests: Guest[] = [ ];
  public guest: Guest;
  public employer: Backoffice;

  private subscription: Subscription;

  constructor(private app: AppComponent, private datepipe: DatePipe, private guestsService: GuestsService) {
    this.employer = this.app.employer;
  }

  ngOnInit() {

    this.onRefresh();

    // using Reactive Forms
    this.insertForm = new FormGroup({
      'guestFirstName' : new FormControl(null,[Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
      'guestLastName' : new FormControl(null,[Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
      'guestDateBirth' : new FormControl(null,Validators.required),
      'guestGender' : new FormControl(null,Validators.required),
      'guestIdNumber' : new FormControl(null,[Validators.required, Validators.maxLength(16),
        Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
      'guestAddress' : new FormControl(null,[Validators.required, Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
      'guestContactNumber' : new FormControl(null,[Validators.required, Validators.maxLength(9),Validators.pattern('^[0-9]*[0-9]$')]),
      'guestEmail' : new FormControl(null, [Validators.required, Validators.email])
    });

    this.editForm = new FormGroup({
      'guestId' : new FormControl(null),
      'guestFirstName' : new FormControl(null,[Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
      'guestLastName' : new FormControl(null,[Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
      'guestDateBirth' : new FormControl(null,Validators.required),
      'guestGender' : new FormControl(null,Validators.required),
      'guestIdNumber' : new FormControl(null,[Validators.required,  Validators.maxLength(16),
        Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
      'guestAddress' : new FormControl(null,[Validators.required, Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
      'guestContactNumber' : new FormControl(null,[Validators.required, Validators.maxLength(9),Validators.pattern('^[0-9]*[0-9]$')]),
      'guestEmail' : new FormControl(null,[Validators.required, Validators.email])
    });

    this.deleteForm = new FormGroup({
      'guestId' : new FormControl(null),
    });

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onRefresh() {
    /*this.guest = this.app.guest;
    this.employer = this.app.employer;*/
    this.fetchGuests();
  }



  populateEditForm(index: number){
    // console.log('editing Guest id ' + this.guests[index].id);


        this.editForm.setValue({
          guestId: index,
          guestFirstName: this.guests[index].firstName,
          guestLastName : this.guests[index].lastName,
          guestDateBirth : this.datepipe.transform(this.guests[index].dateOfBirth, 'yyyy-MM-dd'),
          guestGender : this.guests[index].gender,
          guestIdNumber : this.guests[index].idNumber,
          guestAddress : this.guests[index].address,
          guestContactNumber : this.guests[index].contactNumber,
          guestEmail : this.guests[index].email

        });

  }

  populateDeleteForm(index: number){
    this.deleteForm.setValue({
      guestId : index
    });
  }

  onCreateGuest(){
    // console.log('onCreateGuest');
    // send http request
    this.subscription = this.guestsService.createAndStoreGuest(
      this.insertForm.value.guestFirstName,
      this.insertForm.value.guestLastName,
      this.insertForm.value.guestDateBirth,
      this.insertForm.value.guestGender,
      this.insertForm.value.guestIdNumber,
      this.insertForm.value.guestAddress,
      this.insertForm.value.guestContactNumber,
      this.insertForm.value.guestEmail,
      'Inactive'
      ).subscribe(responseData => {
        // console.log(responseData);
        if(responseData == -1){
          this.error = 'Something went wrong inserting the Guest...'
          this.success = '';
        }else{
          this.success = 'Guest inserted!';
          this.error ='';
          this.fetchGuests();
        }

      },
      error =>{
          this.error = error.message;
          this.success = '';
      });
  }


  onUpdateGuest() {
    // console.log('onUpdateGuest');
    // send http request
    this.subscription = this.guestsService.updateGuest(
      this.guests[this.editForm.value.guestId].id,
      this.editForm.value.guestFirstName,
      this.editForm.value.guestLastName,
      this.editForm.value.guestDateBirth,
      this.editForm.value.guestIdNumber,
      this.editForm.value.guestAddress,
      this.editForm.value.guestContactNumber,
      this.editForm.value.guestGender,
      this.editForm.value.guestEmail,
      'Inactive'
      ).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'Guest updated!';
        this.error = '';
        this.fetchGuests();
      },
      error => {
          this.error = error.message;
          this.success = '';
      });
  }

  onDeleteGuest(){
    // console.log('onDeleteGuest');
    // get id from the deleteForm
    let index = this.deleteForm.value.guestId;
    console.log('deleting Guest id: ' + this.guests[index].id);
    // send http request
    this.subscription = this.guestsService.deleteGuest(this.guests[index].id).subscribe(responseData => {
      // console.log(responseData);
      this.success = 'Guest Deleted!';
      this.error = '';
      this.fetchGuests();
    },
    error =>{
        this.error = error.message;
        this.success = '';
    });

  }

  onFetchGuests() {
    this.fetchGuests();
  }

  private fetchGuests() {
    this.isFetching = true;
    this.subscription = this.guestsService.fetchGuests().subscribe(guests => {
      this.isFetching = false;
      this.guests = [];
      this.guests = guests;
      // console.log(this.guests);
      this.error = '';
    },
    error => {
        this.error = error.message;
    });

  }

  onErrorClose() {
    this.error = null;
  }

  onSuccessClose() {
    this.success = null;
  }

}
