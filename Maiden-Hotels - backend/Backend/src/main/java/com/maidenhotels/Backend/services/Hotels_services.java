package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.tibco.schemas.Hotel;
import com.maidenhotels.Backend.tibco.schemas.Hotels;
import com.maidenhotels.Backend.tibco.schemas.StatusHotel;
import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class Hotels_services implements Services_Interface<Hotels, Hotel> {

    @Autowired
    private ServicesModel<Hotels,StatusHotel> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.HOTEL_PORT + Scheme_SOAP_Services_Locations.HOTEL_SERVICE_SPECIFICATION;


    @Override
    public List<Hotel> getAll() {

        Hotels request = new Hotels(); // --> send an empty Hotel
        Hotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.HOTEL_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getHotel();
    }

    @Override
    public List<Hotel> getByParam(Hotels request) {
        Hotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.HOTEL_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getHotel();
    }


    @Override
    public String create(Hotels request) {

        StatusHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.HOTEL_SOAP_ACTION_CREATE, request);
        return dataOutput.getSucessHotel().getValue();
    }

    @Override
    public String update(Hotels request) {

        StatusHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.HOTEL_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSucessHotel().getValue();
    }

    @Override
    public String delete(Hotels request) {

        StatusHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.HOTEL_SOAP_ACTION_DELETE, request);
        return dataOutput.getSucessHotel().getValue();
    }
}
