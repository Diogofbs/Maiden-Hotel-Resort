export class BookingService {

    public id: number;
    public cost: number;
    public idBooking: number;
    public idServiceHotel: number;

    constructor( id: number, cost: number, idBooking: number, idServiceHotel: number) {

          this.id = id;
          this.cost = cost;
          this.idBooking = idBooking;
          this.idServiceHotel = idServiceHotel;

    }
}
