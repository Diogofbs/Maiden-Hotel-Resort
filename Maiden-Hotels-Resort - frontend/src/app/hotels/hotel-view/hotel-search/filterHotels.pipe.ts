import { PipeTransform, Pipe } from '@angular/core';
import { Hotel } from '../../models/hotels.model';

@Pipe({
  name: 'filterHotel'
})
export class FilterHotelsPipe implements PipeTransform {
  transform(value: Hotel[], type: string, location: string): Hotel[] {
    if (location.length > 0) {
      value = value.filter(hotel => hotel.location === location);
      // console.log(location);
      return value;
    } else if (type === 'All') {
      // console.log(location);
      return value;

    } else {
      value = value.filter(hotel => hotel.type === type);
      // console.log(location);
      return value;

    }

  }
}
