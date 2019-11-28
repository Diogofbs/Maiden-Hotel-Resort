package com.maidenhotels.Backend.controllers.factory;

import com.maidenhotels.Backend.tibco.schemas.*;
import com.maidenhotels.Backend.tibco.schemas.Clients;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class RequestCreator {
    // ---- ---- HOTELS ---- ---- //
    public Hotels createHotels(String classification, String id, String imagePath, String location, String name, String type) {

        Hotel hotel = new Hotel(classification, id, imagePath, location, name, type);
        Hotels request = new Hotels();
        request.getHotel().add(hotel);
        return request;
    }
    public Hotels createHotelsID(String id) {

        Hotel hotel = new Hotel();
        hotel.setId(id);
        Hotels request = new Hotels();
        request.getHotel().add(hotel);
        return request;
    }

    // ---- ---- ROOMS ---- ---- //
    public Rooms createRooms(String beds, String divisions, String id, String size, String type) {

        Room room = new Room(beds, divisions, id, size, type);
        Rooms request = new Rooms();
        request.getRoom().add(room);
        return request;
    }
    public Rooms createRoomsID(String id) {

        Room room = new Room();
        room.setId(id);
        Rooms request = new Rooms();
        request.getRoom().add(room);
        return request;
    }

    // ---- ---- ROOMSHOTEL ---- ---- //
    public RoomsHotels createRoomsHotel(String cost, String id, String idHotel, String idRooms, String roomNumber) {

        RoomsHotel roomsHotel = new RoomsHotel(cost, id, idHotel, idRooms, roomNumber);
        RoomsHotels request = new RoomsHotels();
        request.getRoomsHotel().add(roomsHotel);
        return request;
    }
    public RoomsHotels createRoomsHotelID(String id, String idHotel) {

        RoomsHotel roomsHotel = new RoomsHotel();
        roomsHotel.setId(id);
        roomsHotel.setIdHotel(idHotel);
        RoomsHotels request = new RoomsHotels();
        request.getRoomsHotel().add(roomsHotel);
        return request;
    }

    // ---- ---- SERVICES ---- ---- //
    public Services createServices(String id, String name) {

        Service service = new Service(id, name);
        Services request = new Services();
        request.getService().add(service);
        return request;
    }
    public Services createServicesID(String id) {

        Service service = new Service();
        service.setId(id);
        Services request = new Services();
        request.getService().add(service);
        return request;
    }

    // ---- ---- SERVICESHOTEL ---- ---- //
    public ServicesHotels createServicesHotel(String id, String idHotel, String idService, String status) {

        ServiceHotel serviceHotel = new ServiceHotel(id, idHotel, idService, status);
        ServicesHotels request = new ServicesHotels();
        request.getServiceHotel().add(serviceHotel);
        return request;
    }
    public ServicesHotels createServicesHotelID(String idHotel) {

        ServiceHotel serviceHotel = new ServiceHotel();
        serviceHotel.setIdHotel(idHotel);
        ServicesHotels request = new ServicesHotels();
        request.getServiceHotel().add(serviceHotel);
        return request;
    }

    // ---- ---- CLIENTS ---- ---- //
    public Clients createClients(List<GuestClient> guestClient, String id, String idGuest, String moneySpent, String password) {

        Client client = new Client(guestClient,  id,  idGuest, moneySpent, password);
        Clients request = new Clients();
        request.getClient().add(client);
        return request;
    }
    public Clients createClientsID(String id) {

        Client client = new Client();
        client.setId(id);
        Clients request = new Clients();
        request.getClient().add(client);
        return request;
    }

    // ---- ---- GUESTS ---- ---- //
    public Guests createGuests(String address, String contactNumber, String dateOfBirth, String email, String firstName, String gender, String id, String idNumber, String lastName, String status) {

        Guest guest = new Guest(address, contactNumber, dateOfBirth, email, firstName, gender, id, idNumber, lastName, status);
        Guests request = new Guests();
        request.getGuest().add(guest);
        return request;
    }
    public Guests createGuestsID(String id) {

        Guest guest = new Guest();
        guest.setId(id);
        Guests request = new Guests();
        request.getGuest().add(guest);
        return request;
    }

    // ---- ---- BOOKINGS ---- ---- //
    public Bookings createBookings(List<BkGuests> bkGuests, List<BkServices> bkServices, List<BkroomsHotel> bkroomsHotel, String checkInDate, String checkOutDate, String date, String id, String status) {

        Booking guest = new Booking(bkGuests, bkServices, bkroomsHotel, checkInDate, checkOutDate, date, id, status);
        Bookings request = new Bookings();
        request.getBooking().add(guest);
        return request;
    }
    public Bookings createBookingsID(String id) {

        Booking guest = new Booking();
        guest.setId(id);
        Bookings request = new Bookings();
        request.getBooking().add(guest);
        return request;
    }

    // ---- ---- ROLES ---- ---- //
    public Roles createRoles(String id, String name) {

        Role role = new Role(id, name);
       Roles request = new Roles();
        request.getRole().add(role);
        return request;
    }

    public Roles createRolesID(String id) {

        Role role = new Role();
        role.setId(id);
        Roles request = new Roles();
        request.getRole().add(role);
        return request;
    }

    // ---- ---- BACKOFFICES ---- ---- //
    public Backoffices createBackoffices(String id, String idRole, String name, String password, String username) {

        Backoffice backoffice = new Backoffice(id, idRole, name, password, username);
        Backoffices request = new Backoffices();
        request.getBackoffice().add(backoffice);
        return request;
    }

    public Backoffices createBackofficesID(String id) {

        Backoffice backoffice = new Backoffice();
        backoffice.setId(id);
        Backoffices request = new Backoffices();
        request.getBackoffice().add(backoffice);
        return request;
    }
}
