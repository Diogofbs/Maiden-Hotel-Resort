import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Hotel } from './models/hotels.model';
import { HttpClient } from '@angular/common/http';
import { HotelsService } from './services/hotels.service';
import {HotelService} from './models/hotels-services.model';
import {ServicesService} from '../services/services.service';
import {Service} from '../services/services.model';
import { HotelsServicesService } from './services/hotels-services.service';
import {Room} from '../rooms/rooms.model';
import {HotelRoom} from './models/hotels-rooms.model';
import {HotelsRoomsService} from './services/hotels-rooms.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RoomsService } from '../rooms/rooms.service';
import { AppComponent } from '../app.component';
import { Guest } from '../guests/guests.model';
import { Backoffice } from '../backoffices/models/backoffice.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-hotels',
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css']
})
export class HotelsComponent implements OnInit, OnDestroy {

  insertForm: FormGroup;
  editForm: FormGroup;
  deleteForm: FormGroup;
  readServicesForm: FormGroup;
  deleteServicesForm: FormGroup;
  readRoomsForm: FormGroup;
  deleteRoomsForm: FormGroup;
  editHotelRoomForm: FormGroup;

  public isFetching = false;
  public error = '';
  public success = '';
  public currentlySelected = -1;

  public hotels: Hotel[] = [];
  public hotelsServices: HotelService[] = [ ];
  public services: Service[] = [ ];
  public servicesFromHotel: HotelService[] = [];
  public rooms: Room[] = [];
  public hotelsRooms: HotelRoom[] = [];
  public roomsFromHotel: HotelRoom[] = [];
  public fetchedHotels: boolean;
  public fetchedServices: boolean;
  public fetchedHotelServices: boolean;
  public fetchedHotelRooms: boolean;
  public fetchedRooms: boolean;

  public guest: Guest;
  public employer: Backoffice;

  private subscription: Subscription;


  constructor(private app: AppComponent, private hotelsService: HotelsService, private servicesService: ServicesService,
              private hotelsServicesService: HotelsServicesService, private hotelsRoomsService: HotelsRoomsService,
              private roomsService: RoomsService) {
    this.employer = this.app.employer;
              }

  ngOnInit() {
    this.onRefresh();

    // using Reactive Forms
    this.insertForm = new FormGroup({
      hotelName : new FormControl(null, Validators.required),
      hotelLocation : new FormControl(null, Validators.required),
      hotelClassification : new FormControl(null, Validators.required),
      hotelType : new FormControl(null, Validators.required),
      hotelImagePath: new FormControl(null, Validators.required)
    });

    this.editForm = new FormGroup({
      hotelId : new FormControl(null),
      hotelName : new FormControl(null, Validators.required),
      hotelLocation : new FormControl(null, Validators.required),
      hotelClassification : new FormControl(null, Validators.required),
      hotelType : new FormControl(null, Validators.required),
      hotelImagePath: new FormControl(null, Validators.required)
    });

    this.deleteForm = new FormGroup({
      'hotelId' : new FormControl(null),
    });

    this.readServicesForm = new FormGroup({
      'idHotel': new FormControl(null,Validators.required),
      'idService': new FormControl(null,Validators.required)
    });

    this.deleteServicesForm = new FormGroup({
      'idHotelService' : new FormControl(null),
    });

    this.readRoomsForm = new FormGroup({
      'idHotel': new FormControl(null,Validators.required),
      'idRoom': new FormControl(null,Validators.required),
      'roomNumber': new FormControl(null,[Validators.required, Validators.maxLength(4), Validators.pattern('^[0-9]*[0-9]$')] ),
      'cost': new FormControl(null,[Validators.required, Validators.maxLength(4), Validators.pattern('^[0-9]*[0-9]$')]),
    });

    this.deleteRoomsForm = new FormGroup({
      'idHotelRoom' : new FormControl(null),
    });

    this.editHotelRoomForm = new FormGroup({
      'idHotelRoom': new FormControl(null,Validators.required),
      'idRoom': new FormControl(null,Validators.required),
      'roomNumber': new FormControl(null,[Validators.required, Validators.maxLength(4), Validators.pattern('^[0-9]*[0-9]$')] ),
      'cost': new FormControl(null,[Validators.required, Validators.maxLength(4), Validators.pattern('^[0-9]*[0-9]$')]),
    });

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  populateReadServicesForm(hotelIndex: number) {
    // console.log('index hotel: ' + hotelIndex);
    this.readServicesForm.setValue({
      idHotel: hotelIndex,
      idService: 0,
    });

    this.getServicesByHotelId(this.hotels[hotelIndex].id);
  }

  populateReadRoomsForm(hotelIndex: number) {
    // console.log('index hotel: ' + hotelIndex);
    this.readRoomsForm.setValue({
      idHotel: hotelIndex,
      roomNumber: '',
      cost: '',
      idRoom: 0,
    });

    this.getRoomsByHotelId(this.hotels[hotelIndex].id);
  }

  populateEditHotelRoomForm(index: number) {
    this.editHotelRoomForm.setValue({
      idHotelRoom: index,
      roomNumber: this.roomsFromHotel[index].roomNumber,
      cost: this.roomsFromHotel[index].cost,
      idRoom: this.roomsFromHotel[index].idRooms
    });
  }

  populateDeleteServicesForm(index: number){
    this.deleteServicesForm.setValue({
      idHotelService : index
    });
    // console.log('populateDeleteServicesForm index: ' + index);
  }

  populateDeleteRoomsForm(index: number) {
    this.deleteRoomsForm.setValue({
      idHotelRoom : index
    });
    // console.log('populateDeleteRoomsForm index: ' + index);
  }

  onRefresh() {
    this.fetchHotels();
    this.fetchServices();
    this.fetchRooms();
    while(this.fetchedHotels && this.fetchedServices && this.fetchedRooms){}
    this.fetchHotelsRooms();
    this.fetchHotelsServices();
    /*this.guest = this.app.guest;
    this.employer = this.app.employer;*/

  }

  onAddServiceToHotel() {
    this.subscription = this.hotelsServicesService.createAndStoreHotelService(
      this.hotels[this.readServicesForm.value.idHotel].id,
      this.services[this.readServicesForm.value.idService].id

      ).subscribe(responseData => {
        // console.log(responseData);
        if(responseData === -1) {
          this.error = 'Something went wrong inserting the hotel-service...';
          this.success = '';
        } else {
          this.success = 'Hotel-service inserted!';
          this.error = '';
          this.fetchHotelsServices();
          this.getServicesByHotelId(this.hotels[this.readServicesForm.value.idHotel].id);
        }

      },
      error => {
          this.error = error.message;
          this.success = '';
      });
  }

  onAddRoomToHotel() {
    this.subscription = this.hotelsRoomsService.createAndStoreHotelRoom(
      this.hotels[this.readRoomsForm.value.idHotel].id,
      this.rooms[this.readRoomsForm.value.idRoom].id,
      this.readRoomsForm.value.roomNumber,
      this.readRoomsForm.value.cost

      ).subscribe(responseData => {
        // console.log(responseData);
        if(responseData === -1) {
          this.error = 'Something went wrong inserting the hotel-room...';
          this.success = '';
        } else {
          this.success = 'Hotel-room inserted!';
          this.error = '';
          this.fetchHotelsRooms();
          this.getRoomsByHotelId(this.hotels[this.readRoomsForm.value.idHotel].id);
        }

      },
      error =>{
          this.error = error.message;
          this.success = '';
      });
  }

  onDeactivateHotelService(){
    // console.log('onUpdateHotelService');
    // send http request
    this.subscription = this.hotelsServicesService.updateHotelService(
      this.servicesFromHotel[this.deleteServicesForm.value.idHotelService].id,
      null,
      null,
      'inactive'
      ).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'Hotel-service updated!';
        this.error = '';
        this.fetchHotelsServices();
        this.getServicesByHotelId(this.hotels[this.readServicesForm.value.idHotel].id);
      },
      error => {
          this.error = error.message;
          this.success = '';
      });
  }

  onUpdateHotelRoom(){
    // console.log('onUpdateHotelRoom');
    // send http request
    this.hotelsRoomsService.updateHotelRoom(
      this.roomsFromHotel[this.editHotelRoomForm.value.idHotelRoom].id,
      this.roomsFromHotel[this.editHotelRoomForm.value.idHotelRoom].idHotel,
      this.rooms[this.editHotelRoomForm.value.idRoom].id,
      this.editHotelRoomForm.value.roomNumber,
      this.editHotelRoomForm.value.cost

      ).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'Hotel-room updated!';
        this.error = '';
        this.fetchHotelsRooms();
        this.getRoomsByHotelId(this.hotels[this.readRoomsForm.value.idHotel].id);
      },
      error =>{
          this.error = error.message;
          this.success = '';
      });


  }

  onDeleteHotelService(){
    // console.log('onDeleteHotelService');
    // get id from the deleteForm
    let index = this.deleteServicesForm.value.idHotelService;

    // console.log('deleting hotel-service id: ' + this.servicesFromHotel[index].id);
    // send http request
    this.subscription = this.hotelsServicesService.deleteHotelService(this.servicesFromHotel[index].id).subscribe(responseData => {
      // console.log(responseData);
      this.success = 'Hotel-service Deleted!';
      this.error = '';
      this.fetchHotelsServices();
      // console.log(this.servicesFromHotel);
      this.getServicesByHotelId(this.hotels[this.readServicesForm.value.idHotel].id);
    },
    error => {
        this.error = error.message;
        this.success = '';
    });

  }
  onDeleteHotelRoom() {
    // console.log('onDeleteHotelRoom');
    // get id from the deleteForm
    let index = this.deleteRoomsForm.value.idHotelRoom;

    // console.log('deleting hotel-room id: ' + this.roomsFromHotel[index].id);
    // send http request
    this.subscription = this.hotelsRoomsService.deleteHotelRoom(this.roomsFromHotel[index].id).subscribe(responseData => {
      console.log(responseData);
      this.success = 'Hotel-room Deleted!';
      this.error = '';
      this.fetchHotelsRooms();
      // console.log(this.roomsFromHotel);
      this.getRoomsByHotelId(this.hotels[this.readRoomsForm.value.idHotel].id);
    },
    error => {
        this.error = error.message;
        this.success = '';
    });

  }

  onFetchHotelsServices() {
    this.fetchHotelsServices();
  }

  private fetchHotelsServices(){
    this.isFetching = true;
    this.subscription = this.hotelsServicesService.fetchHotelsServices().subscribe(hotelsServices => {
      this.isFetching = false;
      this.hotelsServices = [];
      for (let i = 0, len = hotelsServices.length; i < len; i++) {
        this.hotelsServices.push(new HotelService(hotelsServices[i].id, hotelsServices[i].idHotel,
        hotelsServices[i].idService, hotelsServices[i].status));
        }
    },
    error => {
        this.error = error.message;
    });

  }

  private fetchHotelsRooms(){
    this.isFetching = true;
    this.subscription = this.hotelsRoomsService.fetchHotelsRooms().subscribe(hotelsRooms => {
      this.isFetching = false;
      this.hotelsRooms = [];
      for (let i = 0, len = hotelsRooms.length; i < len; i++) {
            this.hotelsRooms.push(new HotelRoom(hotelsRooms[i].id, hotelsRooms[i].idHotel, hotelsRooms[i].idRooms,
                hotelsRooms[i].roomNumber, hotelsRooms[i].cost));
        }
    },
    error => {
        this.error = error.message;
    });

  }

  fetchServices() {
    this.fetchedServices = false;
    this.subscription = this.servicesService.fetchServices().subscribe(services => {
      this.fetchedServices = true;
      this.services = [];
      for (let i = 0, len = services.length; i < len; i++) {
        this.services.push(new Service(services[i].id, services[i].name));
        }
      // console.log(this.services);
      this.fetchedServices = true;
    },
    error => {
        this.error = error.message;
    });
  }

  fetchRooms() {
    this.fetchedRooms = false;
    this.subscription = this.roomsService.fetchRooms().subscribe(rooms =>{
        this.fetchedRooms = true;
        this.rooms = [];
        for (let i = 0, len = rooms.length; i < len; i++) {
            this.rooms.push(new Room(rooms[i].id, rooms[i].beds, rooms[i].divisions, rooms[i].type, rooms[i].size));
        }
        // console.log(this.rooms);
        this.fetchedRooms = true;
    },
    error => {
        this.error = error.message;
    });
  }

  populateEditForm(index: number){
    // console.log('editing hotel id ' + this.hotels[index].id);

        this.editForm.setValue({
          hotelId: index,
          hotelName: this.hotels[index].name,
          hotelLocation: this.hotels[index].location,
          hotelClassification: this.hotels[index].classification,
          hotelType: this.hotels[index].type,
          hotelImagePath: this.hotels[index].imagePath
    });

  }

  populateDeleteForm(index: number){
    this.deleteForm.setValue({
      hotelId : index
    });
  }

  onCreateHotel() {
    // console.log('onCreateHotel');
    // send http request
    this.subscription = this.hotelsService.createAndStoreHotel(
      this.insertForm.value.hotelName,
      this.insertForm.value.hotelLocation,
      this.insertForm.value.hotelClassification,
      this.insertForm.value.hotelType,
      this.insertForm.value.hotelImagePath).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'New Hotel Inserted.';
        this.error = '';
        this.fetchHotels();
    },
    error =>{
        this.error = error.message;
        this.success = '';
    });

  }

  onUpdateHotel(){
    // console.log('onUpdateAiport');
    // console.log(this.editForm);
    // console.log(this.hotels[this.editForm.value.hotelId].id);
    // send http request
    this.subscription = this.hotelsService.updateHotel(
        this.hotels[this.editForm.value.hotelId].id,
        this.editForm.value.hotelName,
        this.editForm.value.hotelLocation,
        this.editForm.value.hotelClassification,
        this.editForm.value.hotelType,
        this.editForm.value.hotelImagePath).subscribe(responseData => {
          // console.log(responseData);
          this.success = 'Hotel Updated.';
          this.error = '';
          this.fetchHotels();
      },
      error =>{
          this.error = error.message;
          this.success = '';
      });
  }

  onDeleteHotel(){
    // console.log('onDeleteHotel');
    // get id from the deleteForm
    let index = this.deleteForm.value.hotelId;
    // console.log('deleting hotel id: ' + this.hotels[index].id);
    // send http request
    this.subscription = this.hotelsService.deleteHotel(this.hotels[index].id).subscribe(responseData => {
      // console.log(responseData);
      this.success = 'hotel Deleted.';
      this.error = '';
      this.fetchHotels();
  },
  error => {
      this.error = error.message;
      this.success = '';
  });
  }


  private fetchHotels() {
    this.isFetching = true;
    // console.log('Fetching hotels...');
    this.subscription = this.hotelsService.fetchHotels().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.hotels = [];
        for (let i = 0, len = data.length; i < len; i++) {
          this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location, data[i].classification, data[i].type, data[1].imagePath));
        }
        this.error = '';
        this.fetchedHotels = true;
      },
      error => {
          this.error = error.message;

      });
  }

  onErrorClose() {
    this.error = null;
  }

  onSuccessClose(){
    this.success = null;
  }

  private getHotelByIdHotel(idHotel: number){
    // console.log('hotel id: ' + idHotel);
    return this.hotels.find(x => x.id === idHotel);
  }

  private getServiceByIdService(idService: number) {
    // console.log('service id: '+ idService);
    return this.services.find(x => x.id === idService);
  }

  private getRoomByIdRoom(idRoom: number) {
    // console.log('service id: '+ idService);
    return this.rooms.find(x => x.id === idRoom);
  }

  private getHotelIndex(hotel: Hotel) {
    // console.log('hotel name: ' + hotel.name);
    return this.hotels.findIndex(x => x.id === hotel.id);
  }

  private getServiceIndex(service: Service) {
    // console.log('service name: ' + service.name);
    return this.services.findIndex(x => x.id === service.id);
  }

  private getRoomIndex(room: Room){
    // console.log('hotel room: ' + room.id);
    return this.rooms.findIndex(x => x.id === room.id);
  }

  private getServicesByHotelId(idHotel: number) {
    // console.log('getServicesByHotelId hotel id: ' + idHotel);
    this.fetchedHotelServices = false;
    this.isFetching = true;
    this.hotelsServicesService.getServicesByHotelId(idHotel).subscribe(hotelsServices => {
      this.isFetching = false;
      this.servicesFromHotel = [];
      for (let i = 0, len = hotelsServices.length; i < len; i++) {
          // console.log(hotelsServices[i].id);
          if ((''+hotelsServices[i].id) != '')
            this.servicesFromHotel.push(new HotelService(hotelsServices[i].id, hotelsServices[i].idHotel, hotelsServices[i].idService, hotelsServices[i].status));
        }
        this.fetchedHotelServices = true;
        // console.log(this.servicesFromHotel);
    },
    error => {
        this.error = error.message;
    });
  }

  private getRoomsByHotelId(idHotel: number) {
    // console.log('getRoomsByHotelId hotel id: '+ idHotel);
    this.fetchedHotelRooms = false;
    this.isFetching = true;
    this.subscription = this.hotelsRoomsService.getRoomsByHotelId(idHotel).subscribe(hotelsRooms => {
      this.isFetching = false;
      this.roomsFromHotel = [];
        for (var i = 0, len = hotelsRooms.length; i < len; i++) {
        // console.log(hotelsRooms[i].id);
          if((''+hotelsRooms[i].id) != '')
            this.roomsFromHotel.push(new HotelRoom(hotelsRooms[i].id, hotelsRooms[i].idHotel, hotelsRooms[i].idRooms, hotelsRooms[i].roomNumber, hotelsRooms[i].cost));
        }
        this.fetchedHotelRooms = true;
        // console.log(this.roomsFromHotel);
    },
    error => {
        this.error = error.message;
    });
  }

}
