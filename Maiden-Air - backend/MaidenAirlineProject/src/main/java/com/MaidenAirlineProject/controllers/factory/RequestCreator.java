package com.MaidenAirlineProject.controllers.factory;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class RequestCreator {

                                    // ---- ---- BOOKINGS ---- ---- //
    public Bookings createBookings(List<Passengers> passengers, List<SelectedFlights> selectedFlights, String bookingType, String date, String idClient){

        Booking booking = new Booking(passengers, selectedFlights, bookingType, date, "", idClient);
        Bookings request = new Bookings();
        request.getBooking().add(booking);
        return request;
    }
    public Bookings createBookingsID(String id){

        Booking booking = new Booking();
        booking.setID(id);
        Bookings request = new Bookings();
        request.getBooking().add(booking);
        return request;
    }

                                    // ---- ---- BOOKING_CLIENT_FLIGHTS ---- ---- //
    public BookingClientFlights createBookingCLientFlights(String checkIn, String id, String idBooking, String idClient, String idFlight, String seat){

        BookingClientFlight bookingClientFlight = new BookingClientFlight(checkIn ,id, idBooking, idClient, idFlight, seat);
        BookingClientFlights request = new BookingClientFlights();
        request.getBookingClientFlight().add(bookingClientFlight);
        return request;
    }

                                    // ---- ---- CLIENTS ---- ---- //
    public Clients createClients( String address, String contactNumber, String dateOfBirth, String email, String firstName,
                                 String gender,String id, String idNumber, String idTypeClient, String lastName, String miles, String password, String photo) {

        Client client = new Client(address, contactNumber, dateOfBirth, email, firstName, gender, id, idNumber, idTypeClient, lastName, miles, password, photo);
        Clients request = new Clients();
        request.getClient().add(client);
        return request;
    }
    public Clients createClientsID(String id) {

        Client client = new Client();
        client.setID(id);
        Clients request = new Clients();
        request.getClient().add(client);
        return request;
    }


                                    // ---- ---- CLIENT_TYPES ---- ---- //
    public ClientTypes createClientTypes(String annualFee, String bonusMiles,String id, String monthlyMiles,String name, String welcomeBonus) {

        ClientType clientType = new ClientType(annualFee,bonusMiles,id,monthlyMiles,name,welcomeBonus);
        ClientTypes request = new ClientTypes();
        request.getClientType().add(clientType);
        return request;
    }
    public ClientTypes createClientTypesID(String id) {

        ClientType clientType = new ClientType();
        clientType.setID(id);
        ClientTypes request = new ClientTypes();
        request.getClientType().add(clientType);
        return request;
    }

                                    // ---- ---- FLIGHTS ---- ---- //
    public Flights createFlights( String arrivalAirport, String arrivalDate, String departureAirport, String departureDate,
                                  String flightNumber, String gate, String id, String idAirplane, String miles, String price,  String status) {

        Flight flight = new Flight(arrivalAirport, arrivalDate, departureAirport, departureDate, flightNumber, gate, id, idAirplane, miles, price,status);

        Flights request = new Flights();
        request.getFlight().add(flight);
        return request;
    }
    public Flights createFlightsID( String id) {

        Flight flight = new Flight();
        flight.setID(id);
        Flights request = new Flights();
        request.getFlight().add(flight);
        return request;
    }

                                    // ---- ---- AIRPLANES ---- ---- //
    public Airplanes createAirplanes(String cargoHoldCapacity, String id,  String model, String numberSeats) {

        Airplane airplane = new Airplane(cargoHoldCapacity, id, model, numberSeats);
        Airplanes request = new Airplanes();
        request.getAirplane().add(airplane);
        return request;
    }
    public Airplanes createAirplanesID(String id) {

        Airplane airplane = new Airplane();
        airplane.setID(id);
        Airplanes request = new Airplanes();
        request.getAirplane().add(airplane);
        return request;
    }

                                    // ---- ---- BOOKING_TYPES ---- ---- //
    public BookingTypes createBookingTypes(String cancelBooking, String changeDate, String checkedBaggage, String cost, String handBaggage, String id, String name) {

        BookingType bookingType = new BookingType(cancelBooking, changeDate, checkedBaggage, cost, handBaggage, id, name);
        BookingTypes request = new BookingTypes();
        request.getBookingType().add(bookingType);
        return request;
    }
    public BookingTypes createBookingTypesID(@RequestParam String id) {

        BookingType bookingType = new BookingType();
        bookingType.setID(id);
        BookingTypes request = new BookingTypes();
        request.getBookingType().add(bookingType);
        return request;
    }

                                    // ---- ---- AIRPORTS ---- ---- //
    public Airports createAirports(String city, String country, String id, String name, String shortName, String imagePath) {

        Airport airport = new Airport(city, country, id, name, shortName, imagePath);
        Airports request = new Airports();
        request.getAirport().add(airport);
        return request;
    }
    public Airports createAirportID(String id) {

        Airport airport = new Airport();
        airport.setID(id);
        Airports request = new Airports();
        request.getAirport().add(airport);
        return request;
    }

                                    // ---- ---- BACKOFFICES ---- ---- //
    public Backoffices createBackoffices( String id, String idRole, String name, String password, String username) {

        Backoffice backoffice = new Backoffice(id,idRole,name,password,username);
        Backoffices request = new Backoffices();
        request.getBackoffice().add(backoffice);
        return request;
    }
    public Backoffices createBackofficesID( String id) {

        Backoffice backoffice = new Backoffice();
        backoffice.setID(id);
        Backoffices request = new Backoffices();
        request.getBackoffice().add(backoffice);
        return request;
    }

                                    // ---- ---- ROLES ---- ---- //
    public Roles createRoles(String id, String name) {

        Role role = new Role(id,name);
        Roles request = new Roles();
        request.getRole().add(role);
        return request;
    }
    public Roles createRolesID(String id) {

        Role role = new Role();
        role.setID(id);
        Roles request = new Roles();
        request.getRole().add(role);
        return request;
    }
}
