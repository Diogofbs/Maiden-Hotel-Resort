package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.exceptions.BookingNotCreatedException;
import com.MaidenAirlineProject.exceptions.PassengerInputNotParsedException;
import com.MaidenAirlineProject.exceptions.PassengerNotCreatedException;
import com.MaidenAirlineProject.services.BookingsPlus_services;
import com.MaidenAirlineProject.services.Bookings_services;
import com.MaidenAirlineProject.services.Clients_services;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("Bookings")
public class Booking_Controller {

    @Autowired
    private Bookings_services bookingServices;
    @Autowired
    private BookingsPlus_services bookingsPlus;  // Services related with seats and costs
    @Autowired
    private Clients_services clients; // important to use the clientAge method in UpdateBookingCLientFlight by flightID

    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add CLient to Clients
    @Autowired
    private RequestCreator factory;

    //Logger logger = LoggerFactory.getLogger(Booking_Controller.class);

    @GetMapping
    public List<Booking> getAllBookings(){

        return bookingServices.getAll();
    }

    @PostMapping("BookingByID")
    public List<Booking> getBooking(@RequestParam String id) {

        Bookings request = factory.createBookingsID(id);

        return bookingServices.getByID(request);
    }


    @PostMapping("BookingCreate")
    public Map<String,Double> createBooking(@RequestBody String bookings) {

        //replaceAll - substring the requestBody String to allow the creation of Booking object
        //{\"id\":null,\"idClient\":\"22\",\"bookingType\":\"2\",\"passengers\":[{\"id\":null,\"firstName\":\"Daniel\",\"lastName\":\"Costa\",\"dateOfBirth\":\"1995-02-22\",\"IdNumber\":\"5558887\",\"address\":null,\"contactNumber\":null,\"gender\":\"Male\",\"numberMiles\":null,\"photo\":null,\"idTypeClient\":6,\"email\":null,\"password\":null}],\"selectedFlights\":[\"12\"]}
        bookings = bookings.replaceAll("\r","");
        bookings = bookings.replaceAll("\n","");
        bookings = bookings.replaceAll("\t","");
        bookings = bookings.replaceAll("\"bookings\":","");
        bookings = bookings.substring(1);

        //Transforming string  into List<Passengers>
        ObjectMapper objectMapper = new ObjectMapper();
        Booking booking = new Booking();
        try{
            booking = objectMapper.readValue(bookings, new TypeReference<Booking>(){});
        }catch(IOException e){
            throw  new PassengerInputNotParsedException();
        }

        //Checking if there are available seats
        for(SelectedFlights flight : booking.getSelectedFlights()){

            //Check if some of the passengers are less or equal than 2 years old
            int passengerListSize = 0;
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate now = LocalDate.now();
            for(Passengers person : booking.getPassengers()){

                String date = person.getDateOfBirth().substring(0,10);
                LocalDate dateOfBirth = LocalDate.parse(date,formatter);
                int age = Period.between(dateOfBirth,now).getYears();

                if(age>2){
                    passengerListSize +=1;
                    continue;
                }
            }
            //Checking available seats
            int numberOfAvailableSeats = bookingsPlus.AvailableSeats(flight.getID());
            boolean availableSeat;
            if(numberOfAvailableSeats-(passengerListSize+1)<0){
                availableSeat = false;
                String notAvailable = "Flight number " + flight.getID() + " not available!";
                Map<String,Double> error = new HashMap<>();
                error.put(notAvailable,Double.parseDouble(flight.getID()));
                return error; // returning error if there is not seats in the flight
            }else{
                availableSeat = true;
            }
        }


        //Checking the date of the booking
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        booking.setDate(date);

        Bookings request = new Bookings();
        request.getBooking().add(booking);

        //Creating booking in dataBase...
        String response = bookingServices.create(request);

        //if booking not created
        if(response.equals("-1")){
            throw new BookingNotCreatedException();
        }
        //if booking created and if exists passengers: confirms if passenger were created as clients. if not: delete booking and throw exception
        if(booking.getPassengers().size()>0){
            for(Passengers passenger : booking.getPassengers()){
                Clients client = factory.createClients("","","","","","","",passenger.getIdNumber(),"","","","","");
                if(clients.getByID_Number(client)==null){
                    bookingServices.delete(factory.createBookingsID(response));
                    throw new PassengerNotCreatedException();
                }
            }
        }
        //if booking created - returning cost of the booking and number of the booking...
        Map<String,Double> finalResponse;
        finalResponse = bookingsPlus.totalCost(booking.getPassengers(),booking.getSelectedFlights(),booking.getBookingType());
        finalResponse.put("BookingID",Double.parseDouble(response));
        return finalResponse;
    }

    @PostMapping("BookingDelete")
    public String deleteClient(@RequestParam String id) {

        Bookings request = factory.createBookingsID(id);

        return bookingServices.delete(request);
    }

    @PostMapping("CheckSeats")
    public int checkSeats(@RequestParam String flightId) {

        return bookingsPlus.AvailableSeats(flightId);
    }

    @PostMapping("BookingCost")
    public Map<String,Double> checkCost(@RequestParam List<String> dateOfBirth, @RequestParam List<String> flightsId, @RequestParam String bookingType) {

        //Creating list of passengers with only date-of-birth in it
        List<Passengers> passengersList = new ArrayList<>();
        for(String date : dateOfBirth) {

            Passengers passenger = new Passengers();
            passenger.setDateOfBirth(date);
            passengersList.add(passenger);
        }

        //Creating selected flight
        List<SelectedFlights> selectedFlightsList = new ArrayList<>();
        for(String id : flightsId ) {
            SelectedFlights flight = new SelectedFlights();
            flight.setID(id);
            selectedFlightsList.add(flight);
        }

        return bookingsPlus.totalCost(passengersList,selectedFlightsList,bookingType);
    }

    // ---- Booking Client Flights Services ---- //
    // ---- ---- including **CheckIn** ---- //

    @GetMapping("BookingClientFlights")
    public List<BookingClientFlight> getAllBookingClientFlights(){

        return bookingServices.getAllClientFlights();
    }

    @PostMapping("BookingByClientFlight")
    public List<BookingClientFlight> getBookingClientFlight(@RequestParam String id,@RequestParam String idBooking, @RequestParam String idFlight, @RequestParam String idClient) {

        BookingClientFlights request = factory.createBookingCLientFlights("",id, idBooking, idClient, idFlight, "");

        return bookingServices.getByBookingCLientFlight(request);
    }

    @PostMapping("CheckIn") //UPDATE - seat and checkIn
    public String checkIn(@RequestParam String id, @RequestParam String idClient, @RequestParam String idFlight, @RequestParam String seat, @RequestParam String checkIn){

        BookingClientFlights request = factory.createBookingCLientFlights(checkIn, id, "", "", "", seat);
        BookingClientFlight responseBooking = bookingServices.getByBookingCLientFlight(request).get(0);

        //confirming if idFLight and idCLient match with the information of the given idBooking
        if(!responseBooking.getIdFlight().equals(idFlight)){
            return "id flight does not match with idFlight of the requested booking";
        }else if(!responseBooking.getIdClient().equals(idClient)){
            return "id client does not match with idClient of the requested booking";
        }
        //Checking if passenger had already done the check in
        String check_checkIn = responseBooking.getCheckIn();
        if(check_checkIn.equals("true") && checkIn.equals("1")){
            BookingClientFlights requestSeat = factory.createBookingCLientFlights("1", id, "", "", "", seat);
            String seatChangeResponse = bookingServices.update_BookingClientFLight(requestSeat);
            if(seatChangeResponse.equals("-1")) {
                return "Problems changing seat. Input invalid";
            }else {
                return "Seat: "+ seat + " confirm. CheckIn has already been done";
            }
        }

        //Doing check In.
        String response = bookingServices.update_BookingClientFLight(request);
        if(response.equals("-1")) {
            return "Problems with checkIn. Input invalid";
        }

        //Checking if checkIn is being reversed
        if(checkIn.equals("0")){
            return "Check In reversed";
        }

        //After check In... adding miles
        boolean updateMiles = bookingsPlus.addMiles(idClient,idFlight);

        if(updateMiles){
            return "Check succeeded and miles updated";
        }else{
            return "Check In succeeded, BUT miles not updated...";
        }
    }

    @PostMapping("UpdateBookingClientFlight") //UPDATE - only Flight
    public String updateBookingClientFlight_ByFlightID( @RequestParam String id,@RequestParam String flightID, @RequestParam String clientId){


        //Request age of the client (Service from clients_services)
        Clients requestClient = factory.createClientsID(clientId);
        int age = clients.getClientAge(requestClient);
        // if age bigger than 2, check if there are available seats on the requested flight
        if(age>=2){
            int availableSeats = bookingsPlus.AvailableSeats(flightID);
            if(availableSeats<1){
                return "No seats available, you can't change for this flight"; //No seat available - Stop update...
            }
        }
        //Updating...
        BookingClientFlights request = factory.createBookingCLientFlights("", id, "", "", flightID, "");

        return bookingServices.update_BookingClientFLightByFlightID(request);
    }
}
