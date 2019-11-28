import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { Guest } from 'src/app/guests/guests.model';
import { Backoffice } from 'src/app/backoffices/models/backoffice.model';
import { BookingsService } from '../services/bookings.service';
import { Booking } from '../models/bookings.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Hotel } from 'src/app/hotels/models/hotels.model';
import { HotelsService } from 'src/app/hotels/services/hotels.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.css']
})
export class ManageComponent implements OnInit, OnDestroy {

    public guest: Guest;
    public employer: Backoffice;

    public error = '';
    public success = '';

    public bookings: Booking[];
    public hotels: Hotel[];

    public date: string;
    public idHotel: number;
    public idBooking: number;
    public roomNumber: number;

    private subscription: Subscription;

    constructor(private app: AppComponent, private bookingsService: BookingsService,
                private route: ActivatedRoute, private router: Router, private hotelsService: HotelsService) {
        this.guest = this.app.guest;
        this.employer = this.app.employer;
                 }

    ngOnInit() {

        this.onRefresh();

    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    public onRefresh() {

        /*this.guest = this.app.guest;
        this.employer = this.app.employer;*/
        this.fetchBookings();
        this.fetchHotels();


    }

    private fetchBookings() {

        // console.log('Fetching Bookings...');

        this.bookings = [];

        this.subscription = this.bookingsService.fetchBookings().subscribe(data => {

            // console.log(data);
            this.bookings = [];
            for (const booking of data) {
              this.bookings.push(new Booking(booking.id, booking.date, booking.checkInDate,
                booking.checkOutDate, booking.status, booking.bkGuests, booking.bkServices, booking.bkroomsHotel));
            }
          },
          error => {
              this.error = error.message;

          });
    }

    // -- Button -- //
    OnSelectedBooking(id: number) {
        this.router.navigate(['../booking', id, 'info']);
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

}
