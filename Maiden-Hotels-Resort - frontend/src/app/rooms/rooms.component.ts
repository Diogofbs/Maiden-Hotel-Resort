import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Room } from './rooms.model';
import { RoomsService } from './rooms.service';
import { AppComponent } from '../app.component';
import { Backoffice } from '../backoffices/models/backoffice.model';
import { Guest } from '../guests/guests.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})
export class RoomsComponent implements OnInit, OnDestroy {

  insertForm: FormGroup;
  editForm: FormGroup;
  deleteForm: FormGroup;

  public isFetching = false;
  public error = '';
  public success = '';
  public currentlySelected = -1;

  public rooms: Room[] = [ ];
  public guest: Guest;
  public employer: Backoffice;

  private subscrition: Subscription;


  constructor(private app: AppComponent, private roomsService: RoomsService) {
    this.employer = this.app.employer;
   }

  ngOnInit() {

    this.fetchRooms();

    // using Reactive Forms
    this.insertForm = new FormGroup({
      'roomBeds' : new FormControl(null,Validators.required),
      'roomDivisions' : new FormControl(null, Validators.required),
      'roomType' : new FormControl(null, Validators.required),
      'roomSize' : new FormControl(null, [Validators.required, Validators.maxLength(3),Validators.pattern('^[0-9]*[0-9]$')])
    });

    this.editForm = new FormGroup({
      'roomId' : new FormControl(null),
      'roomBeds': new FormControl(null, Validators.required),
      'roomDivisions' : new FormControl(null, Validators.required),
      'roomType' : new FormControl(null, Validators.required),
      'roomSize' : new FormControl(null, [Validators.required, Validators.maxLength(3),Validators.pattern('^[0-9]*[0-9]$')])
    });

    this.deleteForm = new FormGroup({
      'roomId' : new FormControl(null),
    });
  }

  ngOnDestroy() {
      this.subscrition.unsubscribe();
  }


  populateEditForm(index: number){
    // console.log('editing room id ' + this.rooms[index].id);

    this.editForm.setValue({
          roomId: index,
          roomBeds: this.rooms[index].beds,
          roomDivisions: this.rooms[index].divisions,
          roomType: this.rooms[index].type,
          roomSize: this.rooms[index].size
    });
  }

  populateDeleteForm(index: number){
    this.deleteForm.setValue({
      roomId : index
    });
  }

  onCreateRoom(){
    // console.log('onCreateRoom');
    // send http request
    this.subscrition = this.roomsService.createAndStoreRoom(
      this.insertForm.value.roomBeds,
      this.insertForm.value.roomDivisions,
      this.insertForm.value.roomType,
      this.insertForm.value.roomSize).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'New Room Inserted.';
        this.error = '';
        this.fetchRooms();
    },
    error => {
        this.error = error.message;
        this.success = '';
    });

  }

  onUpdateRoom(){
    // console.log('onUpdateRoom');
    // send http request
    this.subscrition = this.roomsService.updateRoom(
        this.rooms[this.editForm.value.roomId].id,
        this.editForm.value.roomBeds,
        this.editForm.value.roomDivisions,
        this.editForm.value.roomType,
        this.editForm.value.roomSize).subscribe(responseData => {
          // console.log(responseData);
          this.success = 'Room Updated.';
          this.error = '';
          this.fetchRooms();
      },
      error => {
          this.error = error.message;
          this.success = '';
      });
  }

  onDeleteRoom(){
    // console.log('onDeleteRoom');
    // get id from the deleteForm
    let index = this.deleteForm.value.roomId;
    console.log('deleting room id: ' + this.rooms[index].id);
    // send http request
    this.subscrition = this.roomsService.deleteRoom(this.rooms[index].id).subscribe(responseData => {
      // console.log(responseData);
      this.success = 'Room Deleted.';
      this.error = '';
      this.fetchRooms();
  },
  error =>{
      this.error = error.message;
      this.success = '';
  });
  }


  private fetchRooms() {
    /*this.guest = this.app.guest;
    this.employer = this.app.employer;*/
    this.isFetching = true;
    // console.log('Fetching rooms...');
    this.subscrition = this.roomsService.fetchRooms().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.rooms = [];
        for (let i = 0, len = data.length; i < len; i++) {
          this.rooms.push(new Room(data[i].id, data[i].beds, data[i].divisions, data[i].type, data[i].size));
        }
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
