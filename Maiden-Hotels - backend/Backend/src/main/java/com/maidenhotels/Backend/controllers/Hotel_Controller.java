package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.tibco.schemas.Hotel;
import com.maidenhotels.Backend.tibco.schemas.Hotels;
import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Hotels_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Hotels")
public class Hotel_Controller {

    @Autowired
    private Hotels_services hotels;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Hotel> getAllHotels(){
        System.out.println("Getting all hotels");
        return hotels.getAll();
    }

    @PostMapping("HotelByParam")
    public List<Hotel> getHotel(@RequestBody Hotel request ) {

        Hotels hotelsReq = new Hotels();
        hotelsReq.getHotel().add(request);
        return hotels.getByParam(hotelsReq);
    }

    @PostMapping("HotelCreate")
    public String createHotel(@RequestBody Hotel request ) {

        Hotels hotelsReq = new Hotels();
        hotelsReq.getHotel().add(request);
        return hotels.create(hotelsReq);
    }

    @PostMapping("HotelDelete")
    public String deleteHotel(@RequestBody Hotel request ) {

        Hotels hotelsReq = new Hotels();
        hotelsReq.getHotel().add(request);
        return hotels.delete(hotelsReq);
    }

    @PostMapping("HotelUpdate")
    public String UpdateHotel(@RequestBody Hotel request ) {

        Hotels hotelsReq = new Hotels();
        hotelsReq.getHotel().add(request);
        return hotels.update(hotelsReq);
    }
}
