import { Component, OnInit, ɵɵNgOnChangesFeature, OnChanges, OnDestroy } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Hotel } from '../../models/hotels.model';
import { HotelsService } from '../../services/hotels.service';
import { HotelRoom } from '../../models/hotels-rooms.model';
import { HotelService } from '../../models/hotels-services.model';
import { HotelsRoomsService } from '../../services/hotels-rooms.service';
import { HotelsServicesService } from '../../services/hotels-services.service';
import { Service } from 'src/app/services/services.model';
import { Room } from 'src/app/rooms/rooms.model';
import { ServicesService } from 'src/app/services/services.service';
import { RoomsService } from 'src/app/rooms/rooms.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Booking } from 'src/app/booking/models/bookings.model';
import { BookingsService } from 'src/app/booking/services/bookings.service';
import { DatePipe, formatDate } from '@angular/common';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-hotel-item',
  templateUrl: './hotel-item.component.html',
  styleUrls: ['./hotel-item.component.css']
})
export class HotelItemComponent implements OnInit, OnDestroy {


  dateForm: FormGroup;

  public isFetching = false;
  public error = '';
  public success = '';

  public hotel: Hotel;
  public hotelServices: HotelService[];
  public services: Service[];
  public hotels: Hotel[] = [];
  public fetchedHotels: boolean;
  public fetchedHotel = false;
  public id: number = null;

  public checkInInput: string;
  public checkOutInput: string;
  public today: Date;
  public tomorrow: Date;

  private subscription: Subscription;

  constructor(private hotelsService: HotelsService, private hotelsServicesService: HotelsServicesService,
              private route: ActivatedRoute, private servicesService: ServicesService,
              private roomsService: RoomsService, private router: Router,
              private bookingsService: BookingsService, private datePipe: DatePipe ) {
                const todayDate = new Date();
                const tomorrow = new Date((todayDate.getTime()) + 1000 * 60 * 60 * 24);
                const twoDays = new Date((tomorrow.getTime()) + 1000 * 60 * 60 * 24);
                this.today = todayDate;
                this.tomorrow = tomorrow;
                this.checkInInput = formatDate(tomorrow, 'yyyy-MM-dd', 'en');
                this.checkOutInput = formatDate(twoDays, 'yyyy-MM-dd', 'en');

              }

    ngOnInit() {

        this.route.params
        .subscribe(
        (params: Params) => {
            this.id = +params.id;
            this.fetchHotel();
            this.fetchHotels();

            this.fetchHotelServices();
            this.fetchServices();
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

  private fetchHotel() {
    this.isFetching = true;
    this.fetchedHotel = false;
    // console.log('Fetching hotels...' + this.id);
    this.subscription = this.hotelsService.getHotel(this.id).subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.hotel = new Hotel(data[0].id, data[0].name, data[0].location, data[0].classification, data[0].type, data[0].imagePath);
        this.error = '';
        this.fetchedHotel = true;
      },
      error => {
          this.error = error.message;

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
          this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location, data[i].classification, data[i].type, data[i].imagePath));
        }
        this.error = '';
        this.fetchedHotels = true;
      },
      error => {
          this.error = error.message;

      });
  }

            // -- SERVICES && HOTELSERVICES -- //
  private fetchHotelServices() {
    this.isFetching = true;
    // console.log('Fetching hotelServices...');
    this.subscription = this.hotelsServicesService.getServicesByHotelId(this.id).subscribe(hotelsServices => {
      this.isFetching = false;
      this.hotelServices = [];
      for (let i = 0, len = hotelsServices.length; i < len; i++) {
        if (hotelsServices[i].status !== 'inactive' && hotelsServices[i].status !== 'Inactive' ) {

          this.hotelServices.push(new HotelService(hotelsServices[i].id, hotelsServices[i].idHotel,
            hotelsServices[i].idService, hotelsServices[i].status));
        }
      }
    },
    error => {
        this.error = error.message;
    });
  }

  fetchServices() {
    // this.fetchedServices = false;
    // console.log('Fetching Services...');
    this.subscription = this.servicesService.fetchServices().subscribe(services => {
      // this.fetchedServices = true;
      this.services = [];
      for (let i = 0, len = services.length; i < len; i++) {
        this.services.push(new Service(services[i].id, services[i].name));
      }
      // console.log(this.services);
        // this.fetchedServices = true;
    },
    error => {
        this.error = error.message;
    });
  }

  private getServiceByIdService(idService: number) {
    // console.log("service id: "+ idService);
    return this.services.find(x => x.id === idService);
  }

  // confirming if given dates are bigger than today
  selectDate() {

    const todayDate = new Date();
    const tomorrow = new Date((todayDate.getTime()) + 1000 * 60 * 60 * 24);
    const checkIn =  new Date(this.checkInInput);
    const checkOut =  new Date(this.checkOutInput);

    if (checkIn >= todayDate) {
        if (checkOut >= tomorrow) {
            return true;
        }
    }
    return false;
  }

}
