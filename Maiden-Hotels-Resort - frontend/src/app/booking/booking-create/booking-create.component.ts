import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Hotel } from 'src/app/hotels/models/hotels.model';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { HotelsService } from 'src/app/hotels/services/hotels.service';
import { Room } from 'src/app/rooms/rooms.model';
import { HotelRoom } from 'src/app/hotels/models/hotels-rooms.model';
import { HotelsRoomsService } from 'src/app/hotels/services/hotels-rooms.service';
import { RoomsService } from 'src/app/rooms/rooms.service';
import { Booking } from '../models/bookings.model';
import { BookingsService } from '../services/bookings.service';
import { AppComponent } from 'src/app/app.component';
import { Guest } from 'src/app/guests/guests.model';
import { formatDate } from '@angular/common';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-booking-create',
  templateUrl: './booking-create.component.html',
  styleUrls: ['./booking-create.component.css']
})
export class BookingCreateComponent implements OnInit, OnDestroy {

  dateForm: FormGroup;
  guestForm: FormGroup;
  paymentForm: FormGroup;

  guestsForm: FormGroup;

  public error = '';
  public success = '';
  public fetchedHotel = false;
  public fetchedHotelRoom = false;

  public roomHotelid: number;
  public hotelId: number;
  public checkInDate: string;
  public checkOutDate: string;

  public openMoreRooms: false;

  public hotel: Hotel;
  public hotelRooms: HotelRoom[];
  public rooms: Room[];

  public numberOfGuests = 0;
  public numberG: number;

  public priceToPay: number;
  public pay: boolean;

  public guest: Guest;

  private subscription: Subscription;

  constructor(private route: ActivatedRoute, private hotelsService: HotelsService,
              private hotelsRoomsService: HotelsRoomsService, private roomsService: RoomsService,
              private bookingsService: BookingsService, private router: Router, private app: AppComponent ) { }

  ngOnInit() {

    this.route.params.subscribe( (params: Params) => {
          this.roomHotelid = +params['roomid'];
          this.hotelId = +params['idHotel'];
          this.checkInDate = params['dateIn'];
          this.checkOutDate = params['dateOut'];
          // console.log(this.roomHotelid + ' ' + this.hotelId + ' ' + this.checkInDate + ' ' + this.checkOutDate);
          this.fetchHotel();
          this.fetchHotelRooms();
          this.fetchRooms();
        }
    );

    this.paymentForm = new FormGroup ({
        bookingCardNumber: new FormControl(null, [Validators.required, Validators.maxLength(16), Validators.pattern('^[0-9]*[0-9]$')]),
        bookingCardSecurity: new FormControl(null, [Validators.required, Validators.maxLength(3), Validators.pattern('^[0-9]*[0-9]$')])
        // bookingCardValue: new FormControl(null, [Validators.required, Validators.pattern('^[0-9]*[0-9]$')])
      });
  }

  ngOnDestroy() {
      this.subscription.unsubscribe();
  }

                    // -- HOTELS -- //
  private fetchHotel() {

    this.fetchedHotel = false;
    // console.log('Fetching hotels...' + this.hotelId);
    this.subscription = this.hotelsService.getHotel(this.hotelId).subscribe (data => {

        // console.log(data);
        this.hotel = new Hotel(data[0].id, data[0].name, data[0].location, data[0].classification, data[0].type, data[0].imagePath);
        this.error = '';
        this.fetchedHotel = true;
      },
      error => {
          this.error = error.message;

      });
  }

                    // -- GUESTS -- //
   // BUTTON
  addForm(guestsRows: number) {

    // console.log(guestsRows);
    const array = new FormArray([]);

    this.guest = this.app.guest;

    if (this.guest) {
        const dateBirth = new Date(this.guest.dateOfBirth);
        const date = formatDate(dateBirth, 'yyyy-MM-dd', 'en');

        array.push(new FormGroup({
            id: new FormControl(null),
            firstName : new FormControl(this.guest.firstName, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
            lastName : new FormControl(this.guest.lastName, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
            dateOfBirth : new FormControl(date, Validators.required),
            gender : new FormControl(this.guest.gender, Validators.required),
            idNumber : new FormControl(this.guest.idNumber, [Validators.required, Validators.maxLength(16),
                    Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
            address : new FormControl(this.guest.address, [Validators.required,
                    Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
            contactNumber : new FormControl(this.guest.contactNumber, [Validators.required, Validators.maxLength(9),
                    Validators.minLength(9), Validators.pattern('^[0-9]*[0-9]$')]),
            email : new FormControl(this.guest.email, [Validators.required, Validators.email]),
            status: new FormControl('')
            }));

        for (let i = 0, len = guestsRows - 1; i < len; i++) {
            array.push(new FormGroup({
                id: new FormControl(null),
                firstName : new FormControl(null, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
                lastName : new FormControl(null, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
                dateOfBirth : new FormControl(null, Validators.required),
                gender : new FormControl(null, Validators.required),
                idNumber : new FormControl(null, [Validators.required, Validators.maxLength(16),
                      Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
                address : new FormControl(null, [Validators.required, Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
                contactNumber : new FormControl(null, [Validators.required, Validators.maxLength(9),
                      Validators.minLength(9), Validators.pattern('^[0-9]*[0-9]$')]),
                email : new FormControl(null, [Validators.required, Validators.email]),
                status: new FormControl('')
            }));
        }

    } else {

        for (let i = 0, len = guestsRows; i < len; i++) {
            array.push(new FormGroup({
                id: new FormControl(null),
                firstName : new FormControl(null, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
                lastName : new FormControl(null, [Validators.required, Validators.pattern('^[A-ZÀ-Ù]*[a-zà-ú]*$')]),
                dateOfBirth : new FormControl(null, Validators.required),
                gender : new FormControl(null, Validators.required),
                idNumber : new FormControl(null, [Validators.required, Validators.maxLength(16),
              Validators.minLength(8), Validators.pattern('^[0-9]*[0-9]$')]),
                address : new FormControl(null, [Validators.required, Validators.pattern('^[a-zA-ZÀ-ú0-9_.+-ªº ]*[a-zA-ZÀ-ú0-9-.ºª ]+$')]),
                contactNumber : new FormControl(null, [Validators.required, Validators.maxLength(9),
              Validators.minLength(9), Validators.pattern('^[0-9]*[0-9]$')]),
                email : new FormControl(null, [Validators.required, Validators.email]),
                status: new FormControl('')
            }));
        }

    }

    this.guestsForm = new FormGroup ({
        form: array
    });

    this.numberG = guestsRows;

  }

  getControls() {
      return (this.guestsForm.get('form') as FormArray).controls;
  }


                    // -- ROOMS && HOTELROOMS -- //
  private fetchHotelRooms() {

    // console.log('Fetching hotelRoom...');
    this.subscription = this.hotelsRoomsService.getRoomsById(this.roomHotelid).subscribe(hotelsRooms => {
        this.hotelRooms = [];
        for (let i = 0, len = hotelsRooms.length; i < len; i++) {
          this.hotelRooms.push(new HotelRoom(hotelsRooms[i].id, hotelsRooms[i].idHotel,
             hotelsRooms[i].idRooms, hotelsRooms[i].roomNumber, hotelsRooms[i].cost));
        }
    },
    error => {
        this.error = error.message;
    });
  }

  fetchRooms() {
    // console.log('Fetching rooms...');
    this.subscription = this.roomsService.fetchRooms().subscribe(rooms => {

      this.rooms = [];
      for (let i = 0, len = rooms.length; i < len; i++) {
        this.rooms.push(new Room(rooms[i].id, rooms[i].beds, rooms[i].divisions, rooms[i].type, rooms[i].size));
        }
      // console.log(this.rooms);
    },
    error => {
        this.error = error.message;
    });
  }

  private getRoomByIdRoom(idRoom: number) {
    return this.rooms.find(x => x.id === idRoom);
  }

  // BUTTON
  removeHotelRooms(index: number) {
      this.hotelRooms.splice(index, 1);
      // console.log(this.hotelRooms);
  }

  receiveRoomHotel($event) {

    this.subscription = this.hotelsRoomsService.getRoomsById($event).subscribe(hotelsRooms => {

        for (let i = 0, len = hotelsRooms.length; i < len; i++) {
          this.hotelRooms.push(new HotelRoom(hotelsRooms[i].id, hotelsRooms[i].idHotel,
             hotelsRooms[i].idRooms, hotelsRooms[i].roomNumber, hotelsRooms[i].cost));
        }
    },
    error => {
        this.error = error.message;
    });

    // console.log(this.hotelRooms);

  }

                    // -- Creating Booking -- //
   // BUTTON
  goToPayment() {
        this.pay = true;
  }
  totalCost(): number {
    const time = new Date(this.checkOutDate).getTime() - new Date(this.checkInDate).getTime();
    // console.log(time);
    const numberOfDays = time / (24 * 3600 * 1000);
    // console.log(numberOfDays);
    let totalCost = +0;
    for (const room of this.hotelRooms) {
        totalCost +=  +room.cost;
      }
    totalCost = totalCost * numberOfDays;
    this.priceToPay = Math.round(0.1 * totalCost);
    return totalCost;
  }

  onCreateBooking() {
      // if (this.paymentForm.value.bookingCardValue === this.priceToPay) {
        this.CreateBooking();
      /*} else {
        console.log('failing payment' + this.paymentForm.value.bookingCardValue);
        this.error = 'Payment value does not match...';
        this.success = '';
      }*/

  }

  CreateBooking() {
    // console.log(this.guestsForm);

    const bbkGuests: { id: number, firstName: string, lastName: string, dateOfBirth: string,
                    idNumber: string, address: string, contactNumber: number, gender: string,
                    status: string, email: string }[] = this.guestsForm.value.form;

    const bkroomsHotel: { id: number, cost: number, idHotel: number, idRooms: number,
        roomNumber: number }[] = this.hotelRooms;

    const bkServices: {id: number, cost: number, idHotel: number, idService: number }[] = [];
    bkServices.push({id: null, cost: null, idHotel: null, idService: null });

    const booking = new Booking (null, '', this.checkInDate, this.checkOutDate, 'booked', bbkGuests, bkServices, bkroomsHotel);

    // console.log(booking);

    this.subscription = this.bookingsService.createAndStoreBooking(booking).subscribe(responseData => {
        // console.log(responseData);
        if (responseData === -1) {
          this.error = 'Something went wrong inserting the Booking...';
          this.success = '';
        } else {
          this.success = 'Booking created';
          this.error = '';
          this.router.navigate(['../../../../', responseData, 'info' ], {relativeTo: this.route});
        }
    },
    error => {
        this.error = error.message;
    });
  }

  onCancel() {
    this.router.navigate(['../../../../../hotels/all' ], {relativeTo: this.route});
  }

}
