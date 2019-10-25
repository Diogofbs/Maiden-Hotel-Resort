package com.MaidenAirlineProject.controllers;


import com.MaidenAirlineProject.TIBCO.generatedSchemas.Flight;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Flights;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.Flights_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Flights")
public class Flight_Controller {

    @Autowired
    private Flights_services flights;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<Flight> getAllFlights(){

        return flights.getAll();
    }

    @PostMapping("FlightByID")
    public List<Flight> getFlight(@RequestParam String id) {

        Flights request = factory.createFlightsID(id);
        return flights.getByID(request);
    }

    @PostMapping("FlightCreate")
    public String createFlight(@RequestParam String arrivalAirport, @RequestParam String arrivalDate, @RequestParam String departureAirport, @RequestParam String departureDate,
                               @RequestParam String flightNumber, @RequestParam String idAirplane, @RequestParam String price, @RequestParam String miles) {


        Flights request = factory.createFlights(arrivalAirport, arrivalDate, departureAirport,departureDate, flightNumber,"","",idAirplane,miles,price,"");
        return flights.create(request);
    }

    @PostMapping("FlightDelete")
    public String deleteFlight(@RequestParam String id) {

        Flights request = factory.createFlightsID(id);
        return flights.delete(request);
    }

    @PostMapping("FlightUpdate")
    public String updateFlight(@RequestParam String id, @RequestParam String arrivalAirport, @RequestParam String arrivalDate, @RequestParam String departureAirport, @RequestParam String departureDate,
                               @RequestParam String flightNumber, @RequestParam String gate, @RequestParam String idAirplane, @RequestParam String status,@RequestParam String price, @RequestParam String miles ) {

        Flights request = factory.createFlights(arrivalAirport, arrivalDate, departureAirport, departureDate, flightNumber,gate,id,idAirplane,miles,price,status);
        return flights.update(request);
    }

    @PostMapping("FlightByDateAndLocal")
   public List<Flight> flightsByDateAndLocal(@RequestParam String arrivalAirport,@RequestParam String departureAirport, @RequestParam String departureDate) {

        Flights request = factory.createFlights(arrivalAirport, "", departureAirport,departureDate, "","","","","","","");
        return flights.getByDateAndLocal(request);
    }
}
