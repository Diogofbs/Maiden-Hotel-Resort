package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Bookings_services;
import com.maidenhotels.Backend.tibco.schemas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("Bookings")
public class Booking_Controller {

    @Autowired
    private Bookings_services booking;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Booking> getAllBooking(){
        return booking.getAll();
    }

    @PostMapping("BookingByParam")
    public List<Booking> getBookingByParam(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.getByParam(bookingReq);
    }

    @PostMapping("BookingByRoom")
    public List<Booking> getBookingByRoom(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.getByRoom(bookingReq);
    }

    @PostMapping("BookingCreate")
    public String createBooking(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();

        //Checking the date of the booking
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        request.setDate(date);

        bookingReq.getBooking().add(request);
        return booking.create(bookingReq);
    }

    @PostMapping("BookingCreateWithBill")
    public Map<String, Object> createBookingWithBill(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();

        //Checking the date of the booking
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        request.setDate(date);

        bookingReq.getBooking().add(request);
        return booking.createWithBill(bookingReq);
    }

    @PostMapping("BookingDelete")
    public String deleteBooking(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.delete(bookingReq);
    }

    @PostMapping("BookingUpdate")
    public String UpdateBooking(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.update(bookingReq);
    }

    @PostMapping("BookingUpdateServices")
    public String UpdateBookingServices(@RequestBody BookingsService request ) {

        BookingsServices bookingReq = new BookingsServices();
        bookingReq.getBookingsService().add(request);
        return booking.updateServices(bookingReq);
    }

    @PostMapping("BookingUpdateGuests")
    public String UpdateBookingGuests(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.updateGuests(bookingReq);
    }

    @PostMapping("BookingUpdateRooms")
    public String UpdateBookingRooms(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.updateRooms(bookingReq);
    }

    @PostMapping("BookingInsertServices")
    public String InsertBookingServices(@RequestBody BookingsService request ) {

        BookingsServices bookingReq = new BookingsServices();
        bookingReq.getBookingsService().add(request);
        return booking.insertServices(bookingReq);
    }

    @PostMapping("BookingDeleteServices")
    public String DeleteBookingServices(@RequestBody BookingsService request ) {

        BookingsServices bookingReq = new BookingsServices();
        bookingReq.getBookingsService().add(request);
        return booking.deleteServices(bookingReq);
    }

    @PostMapping("BookingGetOccupiedRooms")
    public List<OccupiedRoom> getOccupiedRooms(@RequestBody Booking request ) {

        Bookings bookingReq = new Bookings();
        bookingReq.getBooking().add(request);
        return booking.getOccupiedRooms(bookingReq);
    }
}
