import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { BookingsService } from 'src/app/booking/services/bookings.service';
import { HotelsService } from 'src/app/hotels/services/hotels.service';
import { ServicesService } from '../services.service';
import { HotelsServicesService } from 'src/app/hotels/services/hotels-services.service';
import { Hotel } from 'src/app/hotels/models/hotels.model';
import { Guest } from 'src/app/guests/guests.model';
import { Backoffice } from 'src/app/backoffices/models/backoffice.model';
import { formatDate } from '@angular/common';
import { Booking } from 'src/app/booking/models/bookings.model';
import { Service } from '../services.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HotelService } from 'src/app/hotels/models/hotels-services.model';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BookingService } from 'src/app/booking/models/bookingService.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-charge-services',
  templateUrl: './charge-services.component.html',
  styleUrls: ['./charge-services.component.css']
})
export class ChargeServicesComponent implements OnInit, OnDestroy {

    public insertForm: FormGroup;

    public hotels: Hotel[];
    public booking: Booking;
    public services: Service[];
    public hotelServices: HotelService[];


    public error = '';
    public success = '';

    public guest: Guest;
    public employer: Backoffice;

    public date: string;
    public idHotel: number;
    public idBooking: number;
    public roomNumber: number;

    private subscrition: Subscription;



    constructor(private app: AppComponent, private bookingService: BookingsService, private hotelsService: HotelsService,
                private servicesService: ServicesService, private hotelsServicesService: HotelsServicesService,
                private route: ActivatedRoute) {

        this.employer = this.app.employer;
        const today = new Date();
        this.date = formatDate(today, 'yyyy-MM-dd', 'en');
        // this.date2 = formatDate(today, 'yyyy-MM-dd', 'en');
    }

    ngOnInit() {

        this.route.params.subscribe( (params: Params) => {
            this.idBooking = +params.idBooking;
            this.select();
          }
      );

        this.onRefresh();

        // using Reactive Forms
        this.insertForm = new FormGroup({
            serviceId : new FormControl(null, Validators.required),
            cost: new FormControl(null, Validators.required)
      });
    }

    ngOnDestroy() {
        this.subscrition.unsubscribe();
    }

    private onRefresh() {

        /*this.guest = this.app.guest;
        this.employer = this.app.employer;*/
        this.fetchHotels();
        this.fetchServices();

    }

     // -- Hotels -- //
     private fetchHotels() {

        // console.log('Fetching hotels...');
        this.subscrition = this.hotelsService.fetchHotels().subscribe(data => {

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

                        // -- Services and hotelServices -- //
    fetchServices() {

        this.subscrition = this.servicesService.fetchServices().subscribe(services => {

            this.services = [];

            for (let i = 0, len = services.length; i < len; i++) {
              this.services.push(new Service(services[i].id, services[i].name));
            }
            // console.log(this.services);

        },
        error => {
            this.error = error.message;
        });
    }

    fetchHotelServices() {

        this.subscrition = this.hotelsServicesService.getServicesByHotelId(this.booking.bkroomsHotel[0].idHotel).subscribe(services => {

            this.hotelServices = [];

            for (let i = 0, len = services.length; i < len; i++) {
                if (services[i].status !== 'inactive' && services[i].status !== 'Inactive' ) {
                    this.hotelServices.push(new HotelService(services[i].id, services[i].idHotel,
                        services[i].idService, services[i].status));
                }
            }
            // console.log(this.services);

        },
        error => {
            this.error = error.message;
        });
    }


    private getServiceByIdService(idService: number) {

        return this.services.find(x => x.id === idService);
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

        // console.log(this.booking);
    }

    // -- BookingService actions -- //
    addServiceToBooking() {

        // console.log(this.insertForm);

        const bkServices = new BookingService (null, this.insertForm.value.cost, this.booking.id, this.insertForm.value.serviceId);

        // console.log(bkServices);

        this.subscrition = this.bookingService.createServiceBooking(bkServices).subscribe(responseData => {
            // console.log(responseData);
            if (responseData === -1) {
            this.error = 'Something went wrong inserting the service...';
            this.success = '';
            } else {
            this.success = 'Service added created';
            this.error = '';
            this.select();

            this.insertForm = new FormGroup({
                serviceId : new FormControl(null, Validators.required),
                cost: new FormControl(null, Validators.required)
          });
            }
        },
        error => {
            this.error = error.message;
        });
    }

    deleteServiceToBooking(id: number) {


        const bkServices = new BookingService (id, null, null, null);

        // console.log(bkServices.id);

        this.subscrition = this.bookingService.deleteServiceBooking(bkServices).subscribe(responseData => {
            // console.log(responseData);
            if (responseData === 0) {
            this.error = 'Something went wrong inserting the service...';
            this.success = '';
            } else {
            this.success = 'Service deleted';
            this.error = '';
            this.select();
            }
        },
        error => {
            this.error = error.message;
        });
    }

    // -- Bookings -- //
    private fetchBookingByID() {

        this.booking = null;
        // console.log('Fetching booking...' + this.idBooking);
        let requestBooking: Booking;

        requestBooking = new Booking(this.idBooking, '', '', '', '', null, null, null);
        // console.log(requestBooking);

        this.subscrition = this.bookingService.getBookingByID(requestBooking).subscribe (data => {

            // console.log(data);
            this.booking = new Booking(data[0].id, data[0].date.substring(0, 10), data[0].checkInDate.substring(0, 10),
            data[0].checkOutDate.substring(0, 10), data[0].status, data[0].bkGuests, data[0].bkServices, data[0].bkroomsHotel);
            this.fetchHotelServices();

            this.error = '';

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
        this.subscrition = this.bookingService.getBookingByRoom(requestBooking).subscribe (data => {

            // console.log(data);
            this.booking = new Booking(data[0].id, data[0].date.substring(0, 10), data[0].checkInDate.substring(0, 10),
            data[0].checkOutDate.substring(0, 10), data[0].status, data[0].bkGuests, data[0].bkServices, data[0].bkroomsHotel);
            this.fetchHotelServices();
            this.error = '';

          },
          error => {
              this.error = error.message;

          });
    }

}
