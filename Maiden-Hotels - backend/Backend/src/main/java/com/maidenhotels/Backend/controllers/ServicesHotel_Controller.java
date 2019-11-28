package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.ServicesHotel_services;
import com.maidenhotels.Backend.tibco.schemas.ServiceHotel;
import com.maidenhotels.Backend.tibco.schemas.ServicesHotels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ServicesHotel")
public class ServicesHotel_Controller {

    @Autowired
    private ServicesHotel_services servicesHotel;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<ServiceHotel> getAllServicesHotel(){
        return servicesHotel.getAll();
    }

    @PostMapping("ServicesHotelByParam")
    public List<ServiceHotel> getService(@RequestBody ServiceHotel request ) {

        ServicesHotels servicesHotelReq = new ServicesHotels();
        servicesHotelReq.getServiceHotel().add(request);
        return servicesHotel.getByParam(servicesHotelReq);
    }

    @PostMapping("ServicesHotelCreate")
    public String createService(@RequestBody ServiceHotel request ) {

        ServicesHotels servicesHotelReq = new ServicesHotels();
        servicesHotelReq.getServiceHotel().add(request);
        return servicesHotel.create(servicesHotelReq);
    }

    @PostMapping("ServicesHotelDelete")
    public String deleteService(@RequestBody ServiceHotel request ) {

        ServicesHotels servicesHotelReq = new ServicesHotels();
        servicesHotelReq.getServiceHotel().add(request);
        return servicesHotel.delete(servicesHotelReq);
    }

    @PostMapping("ServicesHotelUpdate")
    public String UpdateService(@RequestBody ServiceHotel request ) {

        ServicesHotels servicesHotelReq = new ServicesHotels();
        servicesHotelReq.getServiceHotel().add(request);
        return servicesHotel.update(servicesHotelReq);
    }
}
