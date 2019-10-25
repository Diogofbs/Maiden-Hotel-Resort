package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.BookingType;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.BookingTypes;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.BookingTypes_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("BookingTypes")
public class BookingType_Controller {

    @Autowired
    private BookingTypes_services bookingTypes;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<BookingType> getAllBookingTypes(){

        return bookingTypes.getAll();
    }

    @PostMapping("BookingTypeByID")
    public List<BookingType> getBookingType(@RequestParam String id) {

        BookingTypes request = factory.createBookingTypesID(id);
        return bookingTypes.getByID(request);
    }

    @PostMapping("BookingTypeCreate")
    public String createClient(@RequestParam String name, @RequestParam String handBaggage, @RequestParam String checkedBaggage, @RequestParam String changeDate,
                               @RequestParam String cancelBooking, @RequestParam String cost) {

        BookingTypes request = factory.createBookingTypes(cancelBooking, changeDate, checkedBaggage, cost, handBaggage, "", name);
        return bookingTypes.create(request);
    }

    @PostMapping("BookingTypeDelete")
    public String deleteClient(@RequestParam String id) {

        BookingTypes request = factory.createBookingTypesID(id);
        return bookingTypes.delete(request);
    }

    @PostMapping("BookingTypeUpdate")
    public String updateClient(@RequestParam String name, @RequestParam String handBaggage, @RequestParam String checkedBaggage, @RequestParam String changeDate,
                               @RequestParam String cancelBooking, @RequestParam String cost, @RequestParam String id) {

        BookingTypes request = factory.createBookingTypes(cancelBooking, changeDate, checkedBaggage, cost, handBaggage, "", name);
        return bookingTypes.update(request);
    }
}
