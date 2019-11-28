package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Guests_services;
import com.maidenhotels.Backend.tibco.schemas.Guest;
import com.maidenhotels.Backend.tibco.schemas.Guests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Guests")
public class Guest_Controller {

    @Autowired
    private Guests_services guests;
    //Creates objects with less and repeatable cod
    // Example: To create Guests: we need to create Guest, set the attributes, instantiate Guests and then add Guest to Guests
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Guest> getAllGuests(){
        System.out.println("Getting all guests");
        return guests.getAll();
    }

    @PostMapping("GuestByID")
    public List<Guest> getGuest(@RequestBody Guest request ) {

        Guests guestsReq = new Guests();
        guestsReq.getGuest().add(request);
        return guests.getByID(guestsReq);
    }

    @PostMapping("GuestCreate")
    public String createGuest(@RequestBody Guest request ) {

        Guests guestsReq = new Guests();
        guestsReq.getGuest().add(request);
        return guests.create(guestsReq);
    }

    @PostMapping("GuestDelete")
    public String deleteGuest(@RequestBody Guest request ) {

        Guests guestsReq = new Guests();
        guestsReq.getGuest().add(request);
        return guests.delete(guestsReq);
    }

    @PostMapping("GuestUpdate")
    public String UpdateGuest(@RequestBody Guest request ) {

        Guests guestsReq = new Guests();
        guestsReq.getGuest().add(request);
        return guests.update(guestsReq);
    }
}
