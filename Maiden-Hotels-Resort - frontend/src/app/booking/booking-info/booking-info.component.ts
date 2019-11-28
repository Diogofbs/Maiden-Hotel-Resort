import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { BookingsService } from '../services/bookings.service';
import { Booking } from '../models/bookings.model';
import { HotelsService } from 'src/app/hotels/services/hotels.service';
import { Hotel } from 'src/app/hotels/models/hotels.model';
import { Service } from 'src/app/services/services.model';
import { ServicesService } from 'src/app/services/services.service';
import { Room } from 'src/app/rooms/rooms.model';
import { RoomsService } from 'src/app/rooms/rooms.service';
import { Guest } from 'src/app/guests/guests.model';
import { Backoffice } from 'src/app/backoffices/models/backoffice.model';
import { AppComponent } from 'src/app/app.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-booking-info',
  templateUrl: './booking-info.component.html',
  styleUrls: ['./booking-info.component.css']
})
export class BookingInfoComponent implements OnInit, OnDestroy {

    public idBooking: number;
    public booking: Booking;
    public hotel: Hotel;
    public services: Service[];
    public rooms: Room[];

    public error = '';
    public success = '';

    public numberOfDays: number;
    public pricePayd: number;

    public guest: Guest;
    public employer: Backoffice;

    private subscription: Subscription;

    constructor(private app: AppComponent, private route: ActivatedRoute, private bookingService: BookingsService,
                private hotelsService: HotelsService, private servicesService: ServicesService, private roomsService: RoomsService,
                private router: Router) {
        this.guest = this.app.guest;
        this.employer = this.app.employer;
                }

    ngOnInit() {

        this.route.params.subscribe( (params: Params) => {
            this.idBooking = +params.idBooking;
            // console.log(this.idBooking);
            this.onRefresh();
        });


    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

    private fetchBooking() {

        // console.log('Fetching booking...' + this.idBooking);
        let requestBooking: Booking;

        /*const bkGuests: { id: number, firstName: string, lastName: string, dateOfBirth: string,
            idNumber: string, address: string, contactNumber: number, gender: string,
            status: string, email: string }[] = [];
        bkGuests.push({ id: null, firstName: '', lastName: '', dateOfBirth: '',
            idNumber: '', address: '', contactNumber: null, gender: '',
            status: '', email: ''});

        const bkroomsHotel: { id: number, cost: number, idHotel: number, idRooms: number,
        roomNumber: number }[] = [];
        bkroomsHotel.push({id: null, cost: null, idHotel: null, idRooms: null,
            roomNumber: null});

        const bkServices: {id: number, cost: number, idHotel: number, idService: number }[] = [];
        bkServices.push({id: null, cost: null, idHotel: null, idService: null });*/

        requestBooking = new Booking(this.idBooking, '', '', '', '', null, null, null);
        this.bookingService.getBookingByID(requestBooking).subscribe (data => {

            // console.log(data);

            this.booking = new Booking(data[0].id, data[0].date.substring(0, 10), data[0].checkInDate.substring(0, 10),
                                    data[0].checkOutDate.substring(0, 10), data[0].status, data[0].bkGuests,
                                    data[0].bkServices, data[0].bkroomsHotel);

            // console.log(this.booking);
            this.fetchHotel();
            const time = new Date(this.booking.checkOutDate).getTime() - new Date(this.booking.checkInDate).getTime();
            this.numberOfDays = time / (24 * 3600 * 1000);



            this.error = '';

          },
          error => {
              this.error = error.message;

          });
    }

    private fetchHotel() {

        this.subscription = this.hotelsService.getHotel( this.booking.bkroomsHotel[0].idHotel).subscribe(data => {

            // console.log(data);
            this.hotel = new Hotel(data[0].id, data[0].name, data[0].location, data[0].classification, data[0].type, data[0].imagePath);
            this.error = '';

          },
          error => {
              this.error = error.message;

          });
    }

    private onRefresh() {
        this.fetchBooking();
        /*this.guest = this.app.guest;
        this.employer = this.app.employer;*/

        this.fetchServices();
        this.fetchRooms();

        /*return new Promise(resolve => {
            setTimeout(() => {
                this.fetchHotel();
                const time = new Date(this.booking.checkOutDate).getTime() - new Date(this.booking.checkInDate).getTime();
                // console.log(time);
                this.numberOfDays = time / (24 * 3600 * 1000);
                // console.log(this.numberOfDays);
            }, 2000);
        });*/
    }

                        // -- Services -- //
    fetchServices() {
        // this.fetchedServices = false;
        this.subscription = this.servicesService.fetchServices().subscribe(services => {

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


    private getServiceByIdService(idService: number) {

        return this.services.find(x => x.id === idService);
    }

                    // -- Rooms -- //
    fetchRooms() {

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

                    // -- Buttons -- //
    OnDeleteBooking() {

        const bookingRequest = new Booking (this.booking.id, '', '', '', '', null, null, null);
        // console.log(bookingRequest);

        this.subscription = this.bookingService.deleteBooking(bookingRequest).subscribe(dataResponse => {
            this.success = 'booking Deleted.';
            // console.log(dataResponse);
            if (dataResponse > 0) {
                this.error = '';
                this.router.navigate(['../../../manage-bookings'], {relativeTo: this.route});
            } else {
                this.error = 'booking was not deleted';
                this.success = '';
            }

        },
        error => {
            this.error = error.message;
            this.success = '';
        });

    }

    OnCheckIn() {

        const bookingRequest = new Booking (this.booking.id, '', '', '', 'active', null, null, null);
        // console.log(bookingRequest);

        this.subscription = this.bookingService.updateBooking(bookingRequest).subscribe(dataResponse => {
            this.success = 'booking Deleted.';
            // console.log(dataResponse);
            if (dataResponse > 0) {
                this.error = '';
                this.success = 'check-in was done!';
                this.onRefresh();
            } else {
                this.error = 'check-in failed';
                this.success = '';
            }

        },
        error => {
            this.error = error.message;
            this.success = '';
        });
    }

    OnCheckOut() {

        const bookingRequest = new Booking (this.booking.id, '', '', '', 'inactive', null, null, null);
        // console.log(bookingRequest);

        this.subscription = this.bookingService.updateBooking(bookingRequest).subscribe(dataResponse => {
            this.success = 'booking Deleted.';
            // console.log(dataResponse);
            if (dataResponse > 0) {
                this.error = '';
                this.success = 'check-out was done!';
                this.onRefresh();
            } else {
                this.error = 'check-in failed';
                this.success = '';
            }

        },
        error => {
            this.error = error.message;
            this.success = '';
        });
    }

    chargeService() {
        this.router.navigate(['../../', this.booking.id, 'charge-services'], {relativeTo: this.route});

    }


    // -- costs info -- //
    round(value: number): number {

        return Math.round(value);

    }
    totalServiceCost(): number {

        let totalcost = +0;

        for (const service of this.booking.bkServices) {
            totalcost += +service.cost;
        }

        return totalcost;

    }

    totalRoomCost(): number {

        let totalcost = +0;

        for (const room of this.booking.bkroomsHotel) {
            totalcost += +room.cost;
        }
        return this.numberOfDays * totalcost;

    }


}
