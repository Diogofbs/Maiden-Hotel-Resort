package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airplane;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airplanes;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.Airplanes_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Airplanes")
public class Airplane_Controller {

    @Autowired
    private Airplanes_services airplanes;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Airplane> getAllAirplanes(){

        return airplanes.getAll();
    }

    @PostMapping("AirplaneByID")
    public List<Airplane> getAirplane(@RequestParam String id) {

        Airplanes request = factory.createAirplanesID(id);
        return airplanes.getByID(request);
    }

    @PostMapping("AirplaneCreate")
    public String createAirplane(@RequestParam String id, @RequestParam String cargoHoldCapacity, @RequestParam String model, @RequestParam String numberSeats) {

        Airplanes request = factory.createAirplanes(cargoHoldCapacity,id,model,numberSeats);
        return airplanes.create(request);
    }

    @PostMapping("AirplaneDelete")
    public String deleteAirplane(@RequestParam String id) {

        Airplanes request = factory.createAirplanesID(id);
        return airplanes.delete(request);
    }

    @PostMapping("AirplaneUpdate")
    public String UpdateAirplane(@RequestParam String id, @RequestParam String cargoHoldCapacity, @RequestParam String model, @RequestParam String numberSeats) {

        Airplanes request = factory.createAirplanes(cargoHoldCapacity,id,model,numberSeats);
        return airplanes.update(request);
    }
}
