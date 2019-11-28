export class Booking {

  public id: number;
  public date: string;
  public checkInDate: string;
  public checkOutDate: string;
  public status: string;

  public bkGuests: {
    id: number,
    firstName: string,
    lastName: string,
    dateOfBirth: string,
    idNumber: string,
    address: string,
    contactNumber: number,
    gender: string,
    status: string,
    email: string }[];

  public bkServices: {
    id: number,
    cost: number,
    idHotel: number,
    idService: number }[];

  public bkroomsHotel: {
    id: number,
    cost: number,
    idHotel: number,
    idRooms: number,
    roomNumber: number }[];

  constructor(
    id: number, date: string, checkInDate: string, checkOutDate: string, status: string,
    bkGuests: {
      id: number,
      firstName: string,
      lastName: string,
      dateOfBirth: string,
      idNumber: string,
      address: string,
      contactNumber: number,
      gender: string,
      status: string,
      email: string, }[],
    bkServices: {
      id: number,
      cost: number,
      idHotel: number,
      idService: number }[],
    bkroomsHotel: {
      id: number,
      cost: number,
      idHotel: number,
      idRooms: number,
      roomNumber: number }[] ) {

      this.id = id;
      this.date = date;
      this.checkInDate = checkInDate;
      this.checkOutDate = checkOutDate;
      this.status = status;
      this.bkGuests = bkGuests;
      this.bkServices = bkServices;
      this.bkroomsHotel = bkroomsHotel;
    }

}
