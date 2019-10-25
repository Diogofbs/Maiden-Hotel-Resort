package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.Airports_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Airports")
public class Airport_Controller {

    @Autowired
    private Airports_services airports;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add CLient to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<Airport> getAllAirports(){

        return airports.getAll();
    }

    @PostMapping("AirportByID")
    public List<Airport> getAirport(@RequestParam String id) {

        Airports request = factory.createAirportID(id);
        return airports.getByID(request);
    }

    @PostMapping("AirportCreate")
    public ResponseEntity<Void> createAirport(@RequestParam String country, @RequestParam String city, @RequestParam String name, @RequestParam String shortName, @RequestParam String imagePath) {

        Airports request = factory.createAirports(city,country,"",name,shortName, imagePath);

        String response;
        try{
            response = airports.create(request);
        }catch(Exception e){
            return ResponseEntity.unprocessableEntity().build();
        }

        if(response.equals("0")){
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("AirportDelete")
    public String deleteAirport(@RequestParam String id) {

        Airports request = factory.createAirportID(id);
        return airports.delete(request);
    }

    @PostMapping("AirportUpdate")
    public String updateAirport(@RequestParam String id, @RequestParam String country, @RequestParam String city, @RequestParam String name, @RequestParam String shortName, @RequestParam String imagePath) {

        Airports request = factory.createAirports(city,country,id,name,shortName, imagePath);
        return airports.update(request);
    }

    @PostMapping("Search")
    public List<Airport> searchAirport(@RequestParam String value) {

        Word word = new Word();
        word.setValue(value);
        SearchToken request = new SearchToken();
        request.setWord(word);
        return airports.search(request);
    }

}
