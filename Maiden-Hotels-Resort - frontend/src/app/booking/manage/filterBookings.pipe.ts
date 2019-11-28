import { PipeTransform, Pipe } from '@angular/core';
import { Booking } from '../models/bookings.model';
import { formatDate } from '@angular/common';

@Pipe({
    name: 'filterBooking'
  })
  export class FilterBookingsPipe implements PipeTransform {
    transform(value: Booking[], idHotel: number, roomNumber: number, idBooking: number, date: Date ): Booking[] {


        /*console.log(date);
        console.log(idBooking);
        console.log(roomNumber);
        console.log(idHotel);*/

        if (idHotel == null && roomNumber == null && idBooking == null && date == null) {
            return value;
        } else if (idBooking != null) {
            value = value.filter(booking => +booking.id === idBooking);
            return value;
        } else if (idHotel != null && roomNumber != null && date != null) {

            date = new Date(date);
            value = value.filter(booking => date >= new Date(booking.checkInDate.substring(0, 10)) &&
            date <= new Date(booking.checkOutDate.substring(0, 10)));
            value = value.filter(booking => booking.bkroomsHotel[0].idHotel == idHotel);
            value = value.filter(booking => {
                for (const hotelRoom of booking.bkroomsHotel) {
                    if (+hotelRoom.roomNumber === roomNumber) {
                        return true;
                    }
                }


            });
            return value;

        } else if (date != null && idHotel != null) {
            date = new Date(date);
            value = value.filter(booking => date >= new Date(booking.checkInDate.substring(0, 10)) &&
            date <= new Date(booking.checkOutDate.substring(0, 10)));
            value = value.filter(booking => booking.bkroomsHotel[0].idHotel == idHotel);
            return value;

        } else if (date != null) {
            date = new Date(date);
            value = value.filter(booking => date >= new Date(booking.checkInDate.substring(0, 10)) &&
            date <= new Date(booking.checkOutDate.substring(0, 10)));
            return value;

        } else if (idHotel != null) {
            value = value.filter(booking => booking.bkroomsHotel[0].idHotel == idHotel);
            return value;

        }
            /*value = value.filter(booking => +booking.bkroomsHotel[0].idHotel === idHotel);
            value = value.filter(booking => booking.bkroomsHotel.filter(room => +room.roomNumber === roomNumber));*/
        return null;




    }


}


