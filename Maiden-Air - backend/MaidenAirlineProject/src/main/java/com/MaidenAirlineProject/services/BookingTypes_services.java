package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.BookingType;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.BookingTypes;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusBookingType;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class BookingTypes_services implements Services_Interface<BookingTypes, BookingType> {

    @Autowired
    private ServicesModel<BookingTypes, StatusBookingType> operations;

    private WebServiceTemplate template;
    private String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.BOOKING_TYPE_PORT + Scheme_SOAP_Services_Locations.BOOKING_TYPE_SERVICE_SPECIFICATION;

    @Override
    public List<BookingType> getAll() {

        BookingTypes request = new BookingTypes(); // BookingType - send empty
        BookingTypes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_TYPE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getBookingType();
    }

    @Override
    public List<BookingType> getByID(BookingTypes request) {

        BookingTypes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_TYPE_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getBookingType();
    }

    @Override
    public String create(BookingTypes request) {

        StatusBookingType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_TYPE_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessBookingType().getValue();
    }

    @Override
    public String update(BookingTypes request) {

        StatusBookingType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_TYPE_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessBookingType().getValue();
    }

    @Override
    public String delete(BookingTypes request) {

        StatusBookingType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_TYPE_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessBookingType().getValue();
    }
}
