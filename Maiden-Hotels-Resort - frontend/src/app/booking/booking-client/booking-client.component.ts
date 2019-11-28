import { Component, OnInit, OnDestroy } from '@angular/core';
import { Backoffice } from 'src/app/backoffices/models/backoffice.model';
import { Guest } from 'src/app/guests/guests.model';
import { Booking } from '../models/bookings.model';
import { Hotel } from 'src/app/hotels/models/hotels.model';
import { AppComponent } from 'src/app/app.component';
import { BookingsService } from '../services/bookings.service';
import { HotelsService } from 'src/app/hotels/services/hotels.service';
import { Router, ActivatedRoute } from '@angular/router';
import { formatDate } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-booking-client',
  templateUrl: './booking-client.component.html',
  styleUrls: ['./booking-client.component.css']
})
export class BookingClientComponent implements OnInit, OnDestroy {

    public hotels: Hotel[];
    public booking: Booking;


    public error = '';
    public success = '';

    public guest: Guest;
    public employer: Backoffice;
    public permission: boolean;

    public date: string;
    public idHotel: number;
    public idBooking: number;
    public roomNumber: number;

    private subscription: Subscription;

  constructor(private app: AppComponent, private bookingService: BookingsService, private hotelsService: HotelsService,
              private route: ActivatedRoute, private router: Router) {

        const today = new Date();
        this.date = formatDate(today, 'yyyy-MM-dd', 'en');
    }

  ngOnInit() {

    this.onRefresh();
  }

  ngOnDestroy() {
      this.subscription.unsubscribe();
  }

  private onRefresh() {

    this.guest = this.app.guest;
    this.employer = this.app.employer;
    this.fetchHotels();

}

 // -- Hotels -- //
 private fetchHotels() {

    // console.log('Fetching hotels...');
    this.subscription = this.hotelsService.fetchHotels().subscribe(data => {

        // console.log(data);
        this.hotels = [];
        for (let i = 0, len = data.length; i < len; i++) {
          this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location, data[i].classification,
                                    data[i].type, data[1].imagePath));
        }
        this.error = '';

      },
      error => {
          this.error = error.message;

    });
}
private getHotelByIdHotel(idHotel: number) {
    // console.log('hotel id: ' + idHotel);
    return this.hotels.find(x => x.id === idHotel);
}

 // -- Button -- //
 select() {
    // console.log(this.idHotel + ' ' + this.date + ' idBooking: ' + this.idBooking + ' roomNumber: ' + this.roomNumber);
    if (this.idBooking) {
        this.fetchBookingByID();
        // console.log(1);

    } else if (this.idHotel && this.date && this.roomNumber) {
        this.fetchBookingByRoom();
        // console.log(2);
    } else {
        this.error = 'invalid input';
        this.booking = null;
        // console.log(3);
    }

}

OnSelectedBooking(id: number) {
    this.router.navigate(['../booking', id, 'info']);
}

// -- Bookings -- //
private fetchBookingByID() {

    this.booking = null;
    this.permission = false;
    // console.log('Fetching booking...' + this.idBooking);
    let requestBooking: Booking;

    requestBooking = new Booking(this.idBooking, '', '', '', '', null, null, null);
    // console.log(requestBooking);

    this.subscription = this.bookingService.getBookingByID(requestBooking).subscribe (data => {

        // console.log(data);
        this.booking = new Booking(data[0].id, data[0].date.substring(0, 10), data[0].checkInDate.substring(0, 10),
        data[0].checkOutDate.substring(0, 10), data[0].status, data[0].bkGuests, data[0].bkServices, data[0].bkroomsHotel);

        this.error = '';

        for (let guestBooking of this.booking.bkGuests) {
            // console.log(guestBooking);
            // console.log(guestBooking.email);
            // console.log(this.guest.email);
            if (guestBooking.email === this.guest.email) {
                this.permission = true;
                // console.log(this.permission);
            }
        }

      },
      error => {
          this.error = error.message;

      });

}

private fetchBookingByRoom() {

    this.booking = null;
    // console.log('Fetching booking...' + this.roomNumber);
    let requestBooking: Booking;

    const bkroomsHotel: { id: number, cost: number, idHotel: number, idRooms: number,
        roomNumber: number }[] = [];

    bkroomsHotel.push({id: null, cost: null, idHotel: this.idHotel, idRooms: null,
                        roomNumber: this.roomNumber});

    requestBooking = new Booking(null, '', this.date, this.date, '', null, null, bkroomsHotel);
    // console.log(requestBooking);
    this.subscription = this.bookingService.getBookingByRoom(requestBooking).subscribe (data => {

        // console.log(data);
        this.booking = new Booking(data[0].id, data[0].date.substring(0, 10), data[0].checkInDate.substring(0, 10),
        data[0].checkOutDate.substring(0, 10), data[0].status, data[0].bkGuests, data[0].bkServices, data[0].bkroomsHotel);
        this.error = '';

        for (let guestBooking of this.booking.bkGuests) {
            // console.log(guestBooking);
            // console.log(guestBooking.email);
            // console.log(this.guest.email);
            if (guestBooking.email === this.guest.email) {
                this.permission = true;
                // console.log(this.permission);
            }
        }

      },
      error => {
          this.error = error.message;

      });
}

}
