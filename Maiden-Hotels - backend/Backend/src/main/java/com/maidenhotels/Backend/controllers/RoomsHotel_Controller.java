package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.RoomsHotel_services;
import com.maidenhotels.Backend.tibco.schemas.RoomsHotel;
import com.maidenhotels.Backend.tibco.schemas.RoomsHotels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("RoomsHotel")
public class RoomsHotel_Controller {

    @Autowired
    private RoomsHotel_services rooms;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<RoomsHotel> getAllRoomsHotels(){
        return rooms.getAll();
    }

    @PostMapping("RoomsHotelByParam")
    public List<RoomsHotel> getRoomsHotel(@RequestBody RoomsHotel request ) {

        RoomsHotels roomsReq = new RoomsHotels();
        roomsReq.getRoomsHotel().add(request);
        return rooms.getByParam(roomsReq);
    }

    @PostMapping("RoomsHotelCreate")
    public String createRoomsHotel(@RequestBody RoomsHotel request ) {

        RoomsHotels roomsReq = new RoomsHotels();
        roomsReq.getRoomsHotel().add(request);
        return rooms.create(roomsReq);
    }

    @PostMapping("RoomsHotelDelete")
    public String deleteRoomsHotel(@RequestBody RoomsHotel request ) {

        RoomsHotels roomsReq = new RoomsHotels();
        roomsReq.getRoomsHotel().add(request);
        return rooms.delete(roomsReq);
    }

    @PostMapping("RoomsHotelUpdate")
    public String UpdateRoomsHotel(@RequestBody RoomsHotel request ) {

        RoomsHotels roomsReq = new RoomsHotels();
        roomsReq.getRoomsHotel().add(request);
        return rooms.update(roomsReq);
    }
}
