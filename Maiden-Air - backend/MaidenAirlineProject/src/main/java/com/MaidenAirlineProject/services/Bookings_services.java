package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Bookings_services implements Services_Interface<Bookings, Booking> {

    @Autowired
    private Jaxb2Marshaller marshaller;
    @Autowired
    private ServicesModel<Bookings, StatusBooking> operations;
    @Autowired
    private ServicesModel<BookingClientFlights, StatusBooking> operationClientFlight;

    private WebServiceTemplate template;
    private String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.BOOKING_PORT + Scheme_SOAP_Services_Locations.BOOKING_SERVICE_SPECIFICATION;

    @Override
    public List<Booking> getAll() {

        Bookings request = new Bookings(); // Bookings - send empty
        Bookings dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getBooking();
    }

    @Override
    public List<Booking> getByID(Bookings request) {

        Bookings dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getBooking();
    }

    @Override
    public String create(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    @Override
    public String update(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    @Override
    public String delete(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public List<BookingClientFlight> getAllClientFlights() {

        BookingClientFlights request = new BookingClientFlights(); // BookingClientFlights - send empty
        BookingClientFlights dataOutput = operationClientFlight.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_ALL_CLIENT_FLIGHT,request);
        return dataOutput.getBookingClientFlight();
    }

    public List<BookingClientFlight> getByBookingCLientFlight(BookingClientFlights request) {

        BookingClientFlights dataOutput = operationClientFlight.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_BY_CLIENT_FLIGHT,request);
        return dataOutput.getBookingClientFlight();
    }

    public String update_BookingClientFLight(BookingClientFlights request) {

        StatusBooking dataOutput = operationClientFlight.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE_CLIENT_FLIGHT, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public String update_BookingClientFLightByFlightID(BookingClientFlights request) {

        StatusBooking dataOutput = operationClientFlight.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE_CLIENT_FLIGHT_BY_FLIGHTID, request);
        return dataOutput.getSuccessBooking().getValue();
    }
}
