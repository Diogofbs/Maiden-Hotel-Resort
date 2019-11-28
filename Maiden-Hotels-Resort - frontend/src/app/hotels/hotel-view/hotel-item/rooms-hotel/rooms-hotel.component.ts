import { Component, OnInit, Input, EventEmitter, Output, OnChanges, OnDestroy } from '@angular/core';
import { BookingsService } from 'src/app/booking/services/bookings.service';
import { Router, ActivatedRoute, Route } from '@angular/router';
import { RoomsService } from 'src/app/rooms/rooms.service';
import { HotelsRoomsService } from 'src/app/hotels/services/hotels-rooms.service';
import { HotelRoom } from 'src/app/hotels/models/hotels-rooms.model';
import { Room } from 'src/app/rooms/rooms.model';
import { Booking } from 'src/app/booking/models/bookings.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-rooms-hotel',
  templateUrl: './rooms-hotel.component.html',
  styleUrls: ['./rooms-hotel.component.css']
})
export class RoomsHotelComponent implements OnInit, OnChanges, OnDestroy {

  public isFetching = false;
  public error = '';
  public success = '';


  public hotelRooms: HotelRoom[];
  public hotelRoomsOccupied: HotelRoom[];
  public rooms: Room[];

  @Input() id: number = null;
  @Input() checkInInput: string;
  @Input() checkOutInput: string;
  @Input() roomHotelSelected: HotelRoom[];

  @Output() idRoomHotel = new EventEmitter<number>();

  private subscription: Subscription;

  constructor(private hotelsRoomsService: HotelsRoomsService, private route: ActivatedRoute,
              private roomsService: RoomsService, private router: Router,
              private bookingsService: BookingsService) { }

  ngOnInit() {

    // console.log(this.id);
    // this.checkRooms();
    this.fetchHotelRooms();
    this.fetchRooms();

  }

  ngOnChanges() {

    /*console.log('changing');
    const todayDate = new Date();
    const tomorrow = new Date((todayDate.getTime()) + 1000 * 60 * 60 * 24);
    const checkIn =  new Date(this.checkInInput);
    const checkOut =  new Date(this.checkOutInput);

    if (checkIn < todayDate) {
        this.checkInInput = formatDate(todayDate, 'yyyy-MM-dd', 'en');
    }
    if (checkOut <= todayDate) {
        this.checkInInput = formatDate(tomorrow, 'yyyy-MM-dd', 'en');
    }*/
    this.checkRooms();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }



   // -- ROOMS && HOTELROOMS -- //
   private fetchHotelRooms() {
    this.isFetching = true;
    // console.log('Fetching hotelRoom...');
    this.subscription = this.hotelsRoomsService.getRoomsByHotelId(this.id).subscribe(hotelsRooms => {
      this.isFetching = false;
      this.hotelRooms = [];
      // console.log(this.roomHotelSelected);
      // if call from booking - hotelRooms already selected is not pushed
      if (this.roomHotelSelected && this.roomHotelSelected.length > 0) {

        for (const room of hotelsRooms) {
                this.hotelRooms.push(new HotelRoom(room.id, room.idHotel,
                                    room.idRooms, room.roomNumber, room.cost));

        }

        for (const roomSelected of this.roomHotelSelected) {
            for (const room of this.hotelRooms) {
                if (room.roomNumber === roomSelected.roomNumber) {
                    const index = this.hotelRooms.indexOf(room);
                    // console.log('index is' + index);
                    this.hotelRooms.splice(index, 1);
                    break;

                }
            }
        }

      } else {

        for (const room of hotelsRooms) {
            this.hotelRooms.push(new HotelRoom(room.id, room.idHotel,
                                room.idRooms, room.roomNumber, room.cost));
        }
      }
    },
    error => {
        this.error = error.message;
    });

  }

  fetchRooms() {
    // this.fetchedRooms = false;
    // console.log('Fetching rooms...');
    this.subscription = this.roomsService.fetchRooms().subscribe(rooms => {
      // this.fetchedRooms = true;
      this.rooms = [];
      for (let i = 0, len = rooms.length; i < len; i++) {
        this.rooms.push(new Room(rooms[i].id, rooms[i].beds, rooms[i].divisions, rooms[i].type, rooms[i].size));
        }
      // console.log(this.rooms);
        // this.fetchedRooms = true;
    },
    error => {
        this.error = error.message;
    });
  }

  private getRoomByIdRoom(idRoom: number) {
    // console.log("service id: " + idService);
    return this.rooms.find(x => x.id === idRoom);
  }

  onBookRoom(roomHotelid: string) {

    /*// -- adding 1 day to checkIn to corect date from checkRooms() -- //
    let check =  new Date(this.checkInInput);
    const checkIn = new Date(check.getTime() + 1000 * 60 * 60 * 24);
    console.log('check in date: ' + checkIn);
    this.checkInInput = formatDate(checkIn, 'yyyy-MM-dd', 'en');

    // -- subtracting 1 day to checkOut to corect date from checkRooms() -- //
    check =  new Date(this.checkOutInput);
    const checkOut = new Date(check.getTime() - 1000 * 60 * 60 * 24);
    console.log('check in date: ' + checkOut);
    this.checkOutInput = formatDate(checkOut, 'yyyy-MM-dd', 'en');*/
    this.router.navigate(['../../../', 'booking', this.checkInInput, this.checkOutInput, this.id, roomHotelid],
    {relativeTo: this.route});
  }

  checkRooms() {

    const bkroomsHotel: { id: number, cost: number, idHotel: number, idRooms: number,
                        roomNumber: number }[] = [
        {id: null, cost: null, idHotel: this.id, idRooms: null, roomNumber: null}
      ];

    /*// -- subtracting 1 day to checkIn to check available rooms (TIBCO SERVICE - check dates are not inclusive) -- //
    let check =  new Date(this.checkInInput);
    const checkIn = new Date(check.getTime() - 1000 * 60 * 60 * 24);
    console.log('check in date: ' + checkIn);
    this.checkInInput = formatDate(checkIn, 'yyyy-MM-dd', 'en');

    // -- adding 1 day to checkOut to check available rooms (TIBCO SERVICE - check dates are not inclusive) -- //
    check =  new Date(this.checkOutInput);
    const checkOut = new Date(check.getTime() + 1000 * 60 * 60 * 24);
    console.log('check in date: ' + checkOut);
    this.checkOutInput = formatDate(checkOut, 'yyyy-MM-dd', 'en');*/

    const booking = new Booking (null, '', this.checkInInput, this.checkOutInput, '', null, null, bkroomsHotel);

    // console.log(booking);

    this.subscription = this.bookingsService.getOccupiedRooms(booking).subscribe(rooms => {
        this.hotelRoomsOccupied = rooms;
        // console.log(this.hotelRoomsOccupied);
    },
    error => {
        this.error = error.message;
    });

  }

  sendOutput(room: HotelRoom) {

      this.hotelRooms = this.hotelRooms.filter(roomToFilter => roomToFilter !== room);
      this.idRoomHotel.emit(room.id);

  }



}
