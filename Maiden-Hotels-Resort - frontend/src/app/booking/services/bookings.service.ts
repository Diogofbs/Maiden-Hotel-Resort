import { Injectable } from '@angular/core';
import { Booking } from '../models/bookings.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { HotelRoom } from 'src/app/hotels/models/hotels-rooms.model';
import { BookingService } from '../models/bookingService.model';

@Injectable({
  providedIn: 'root'
})
export class BookingsService {

  constructor(private http: HttpClient, private constants: ConstantsService) { }

  createAndStoreBooking(bookingRequest: Booking) {
    // console.log('inserting booking ');

    // console.log(bookingRequest);
    return this.http.post(this.constants.webServicesUrl + '/Bookings/BookingCreate', bookingRequest);
}

updateBooking(bookingRequest: Booking) {

    return this.http.post(this.constants.webServicesUrl + '/Bookings/BookingUpdate', bookingRequest);
}

deleteBooking(bookingRequest: Booking) {

    return this.http.post(this.constants.webServicesUrl + '/Bookings/BookingDelete', bookingRequest);
}

fetchBookings() {

    return this.http.get<Booking[]>(this.constants.webServicesUrl + '/Bookings');
}

getBookingByID(bookingRequest: Booking) {

  return this.http.post<Booking[]>(this.constants.webServicesUrl + '/Bookings/BookingByParam', bookingRequest);
}

getBookingByRoom(bookingRequest: Booking) {

  return this.http.post<Booking[]>(this.constants.webServicesUrl + '/Bookings/BookingByRoom', bookingRequest);
}

getOccupiedRooms(bookingRequest: Booking) {

    // console.log('fectching occupied rooms');
    return this.http.post<HotelRoom[]>(this.constants.webServicesUrl + '/Bookings/BookingGetOccupiedRooms', bookingRequest);
}


// BookingService
createServiceBooking(bookingServiceRequest: BookingService) {

    return this.http.post(this.constants.webServicesUrl + '/Bookings/BookingInsertServices', bookingServiceRequest);
}

deleteServiceBooking(bookingServiceRequest: BookingService) {

    return this.http.post(this.constants.webServicesUrl + '/Bookings/BookingDeleteServices', bookingServiceRequest);
}

}
