package com.maidenhotels.Backend.services;

import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.Guest;
import com.maidenhotels.Backend.tibco.schemas.Guests;
import com.maidenhotels.Backend.tibco.schemas.StatusGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Guests_services implements Services_Interface<Guests, Guest> {

    @Autowired
    private ServicesModel<Guests,StatusGuest> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.GUEST_PORT + Scheme_SOAP_Services_Locations.GUEST_SERVICE_SPECIFICATION;


    @Override
    public List<Guest> getAll() {

        Guests request = new Guests(); // --> send an empty Guest
        Guests dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getGuest();
    }

    public List<Guest> getByParam(Guests request) {

        Guests dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getGuest();
    }

    public List<Guest> getByID(Guests request) {

        Guests dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getGuest();
    }

    @Override
    public String create(Guests request) {

        StatusGuest dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessGuest().getValue();
    }

    @Override
    public String update(Guests request) {

        StatusGuest dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessGuest().getValue();
    }

    @Override
    public String delete(Guests request) {

        StatusGuest dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.GUEST_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessGuest().getValue();
    }
}
