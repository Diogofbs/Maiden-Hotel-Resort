package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.exceptions.BookingTypeNotExistException;
import com.MaidenAirlineProject.exceptions.FlightNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Services related with seats and costs
@Service
public class BookingsPlus_services {

    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;
    @Autowired
    private Flights_services flights;
    @Autowired
    private Clients_services clients;
    @Autowired
    private ClientTypes_services clientTypes;
    @Autowired
    private Airplanes_services airplanes;
    @Autowired
    private Bookings_services bookings;
    @Autowired
    private BookingTypes_services bookingTypes;

    public int AvailableSeats(String flightId){

        //Request flight with numberId provided
        Flights requestFlight = factory.createFlightsID(flightId);
        Flight responseFlight = flights.getByID(requestFlight).get(0);
        // Possible access: Id airplane

        if(responseFlight.getID().equals("")){
            throw new FlightNotExistException();
        }

        //request airplane with numberId provided by Flight
        Airplanes requestAirplane = factory.createAirplanesID(responseFlight.getIDAirplane());
        Airplane responseAirplane = airplanes.getByID(requestAirplane).get(0);
        //possible access: Number_Seats max capacity

        //request bookings with flightId provided
        BookingClientFlights request = factory.createBookingCLientFlights("","","","",flightId,"");
        List<BookingClientFlight> responseListBookings =  bookings.getByBookingCLientFlight(request);
        // possible access: number of people already in the flight

        // cicle to calculate number of passenger with less or equal than 2(age)
        // Children with less or equal than 2 go in same seat as one of the parents
        int totalChildrenWithLessOrEqualThan2 =0;
        for(BookingClientFlight booking : responseListBookings){


            if(booking.getIdClient().length()<1){
                continue;
            }

            Clients requestClient = factory.createClientsID(booking.getIdClient());
            int age = clients.getClientAge(requestClient);

            if(age<=2){
                totalChildrenWithLessOrEqualThan2 +=1;
            }
        }

        int maxCapacitySeats =  Integer.parseInt(responseAirplane.getNumberSeats());
        int numberOfPassengers = responseListBookings.size()-totalChildrenWithLessOrEqualThan2;

        return maxCapacitySeats-numberOfPassengers; //available seats
    }



    public Map<String,Double> totalCost(List<Passengers> passengersAge, List<SelectedFlights> flightsID, String bookingTypeID){

        double value = 0; //inicial value - important to calculate the cost of one adult person flight
        Map<String,Double> totalInfo = new HashMap<>();  //return this.Map

        // check price of selected flight and add to value
        for(SelectedFlights selectedFlight: flightsID){

            //Request flight with numberId provided
            Flights requestFlight = factory.createFlightsID(selectedFlight.getID());
            Flight responseFlight = flights.getByID(requestFlight).get(0);
            // Possible access: cost

            //if selected flight does not exist throw exception
            if(responseFlight.getID().equals("")){
                throw new FlightNotExistException();
            }
            value+= Double.parseDouble(responseFlight.getPrice()); // add cost of the flights for one adult passenger
        }

        totalInfo.put("Flights", value); // adding to Map price of the flight for one adult person

        // list of age discount of the passengers
        List<Double> ageDiscount = new ArrayList<>();
        ageDiscount.add(1.0); // passenger-Client needs to be an adult
        totalInfo.put("Passenger 1", 1.0); //adding client ageDiscount to Map
        int count = 1;
        for(Passengers passengers : passengersAge ){ //Dealing with the age of the other passengers

            count +=1;
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate dateOfBirth = LocalDate.parse(passengers.getDateOfBirth(),formatter);
            LocalDate now = LocalDate.now();
            int age = Period.between(dateOfBirth,now).getYears(); // age of the passenger

            //Adding to Map: ageDiscount of the others passengers
            if(age<=2){
                ageDiscount.add(0.9);
                totalInfo.put("Passenger " + count, 0.9);
                continue;
            }else if(age>2 && age<=10){
                ageDiscount.add(0.95);
                totalInfo.put("Passenger " + count, 0.95);
                continue;
            }else{
                ageDiscount.add(1.0);
                totalInfo.put("Passenger " + count, 1.0);
                continue;
            }
        }

        // Checking price of the booking type selected
        //Request bookingType with numberId provided
        BookingTypes request = factory.createBookingTypesID(bookingTypeID);
        double responseBookingTypeCost = 0;
        try{
            responseBookingTypeCost = Double.parseDouble(bookingTypes.getByID(request).get(0).getCost());
        }catch(Exception e){
            throw new BookingTypeNotExistException();
        }
        // Possible access: cost

        totalInfo.put("BookingTypeCost", responseBookingTypeCost); // adding to Map the cost of the BookingType
        value+=responseBookingTypeCost; // add cost of the bookingType

        //total price to be paid
        double totalCost = 0;

        // Adding cost for each passenger with ageDiscount
        for(Double percentage : ageDiscount){
            totalCost += percentage*value;
        }

        totalInfo.put("TotalCost", totalCost); //adding to Map the totalCost of the flights

//                  Map return example:
//                                "Total Cost": 1928.0,
//                                "Flights": 934.0,
//                                "BookingType cost": 30.0,
//                                "Passenger 2": 1.0,
//                                "Passenger 1": 1.0
        return totalInfo; // returning Map
    }

    public boolean addMiles(String idClient, String idFlight){

        // Request Client with idClient provided
        Clients requestClient = factory.createClientsID(idClient);
        Client clientResponse = clients.getByID(requestClient).get(0);
        if(clientResponse.getIdNumber().equals("")){
            return false;  // Client does not exist
        }

        // Change currentMiles to a double
        String current = clientResponse.getNumberMiles();
        Double currentMiles = Double.parseDouble(current);

        // Request ClientType info
        ClientTypes requestClientType = factory.createClientTypesID(clientResponse.getIdTypeClient());
        String bonusMiles = clientTypes.getByID(requestClientType).get(0).getBonusMiles();

        // Change bonus miles to a double and divide for 100 = Percentage
        Double bonusClient = Double.parseDouble(bonusMiles);
        bonusClient = bonusClient * 0.01;

        // Request Flight with idFlight provided
        Flights requestFlight = factory.createFlightsID(idFlight);
        Flight responseFLight = flights.getByID(requestFlight).get(0);
        if(responseFLight.getID().equals("")){
            return false;  //Flight does not exist...
        }
        String flightMiles = responseFLight.getMiles();

        // Change milesEarned to a double and changed it to 10% of its value
        Double milesEarned = Double.parseDouble(flightMiles);
        milesEarned = milesEarned * 0.10; // A normal Passenger only wins 10% of the miles

        // addding all the mills values
        double totalMilesEarned = milesEarned + milesEarned*bonusClient + currentMiles;
        // round double value - miles
        int totalMiles = (int) Math.round(totalMilesEarned);
        // transforming miles into String
        String finalMiles = Integer.toString(totalMiles);
        //set Client miles
        requestClient.getClient().get(0).setNumberMiles(finalMiles);

        //Updating miles
        String response = new String();
        response = clients.update(requestClient);

        if (response.equals("-1")){ //if miles not updated
            return false;
        }

        return true; //if miles updated
    }
}
