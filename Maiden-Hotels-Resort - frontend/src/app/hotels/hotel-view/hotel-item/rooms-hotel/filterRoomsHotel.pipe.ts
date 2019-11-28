import { Pipe, PipeTransform } from '@angular/core';
import { HotelRoom } from '../../../models/hotels-rooms.model';

@Pipe({
  name: 'filterRoomsHotel'
})
export class FilterRoomsHotelPipe implements PipeTransform {
  transform(value: HotelRoom[], hotelRoomsOccupied: HotelRoom[]): HotelRoom[] {

    for (let i = 0, len = hotelRoomsOccupied.length; i < len; i++) {
        value = value.filter(hotelRoom => hotelRoom.id !== hotelRoomsOccupied[i].id);

    }
    return value;

  }
}
