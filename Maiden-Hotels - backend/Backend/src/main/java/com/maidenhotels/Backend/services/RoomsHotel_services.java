package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.RoomsHotel;
import com.maidenhotels.Backend.tibco.schemas.RoomsHotels;
import com.maidenhotels.Backend.tibco.schemas.StatusRoomsHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class RoomsHotel_services implements Services_Interface<RoomsHotels, RoomsHotel> {

    @Autowired
    private ServicesModel<RoomsHotels,StatusRoomsHotel> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.ROOMSHOTEL_PORT + Scheme_SOAP_Services_Locations.ROOMSHOTEL_SERVICE_SPECIFICATION;


    @Override
    public List<RoomsHotel> getAll() {

        RoomsHotels request = new RoomsHotels(); // --> send an empty RoomsHotel
        RoomsHotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROOMSHOTEL_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getRoomsHotel();
    }

    public List<RoomsHotel> getByParam(RoomsHotels request) {

        RoomsHotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROOMSHOTEL_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getRoomsHotel();
    }

    @Override
    public String create(RoomsHotels request) {

        StatusRoomsHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOMSHOTEL_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessRoomsHotel().getValue();
    }

    @Override
    public String update(RoomsHotels request) {

        StatusRoomsHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOMSHOTEL_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessRoomsHotel().getValue();
    }

    @Override
    public String delete(RoomsHotels request) {

        StatusRoomsHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOMSHOTEL_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessRoomsHotel().getValue();
    }
}
