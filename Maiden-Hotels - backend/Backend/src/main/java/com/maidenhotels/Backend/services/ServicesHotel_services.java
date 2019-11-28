package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.ServiceHotel;
import com.maidenhotels.Backend.tibco.schemas.ServicesHotels;
import com.maidenhotels.Backend.tibco.schemas.StatusService;
import com.maidenhotels.Backend.tibco.schemas.StatusServiceHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@org.springframework.stereotype.Service
public class ServicesHotel_services implements Services_Interface<ServicesHotels, ServiceHotel> {

    @Autowired
    private ServicesModel<ServicesHotels, StatusServiceHotel> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.SERVICESHOTEL_PORT + Scheme_SOAP_Services_Locations.SERVICESHOTEL_SERVICE_SPECIFICATION;


    @Override
    public List<ServiceHotel> getAll() {

        ServicesHotels request = new ServicesHotels(); // --> send an empty ServicesHotel
        ServicesHotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.SERVICESHOTEL_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getServiceHotel();
    }

    public List<ServiceHotel> getByParam(ServicesHotels request) {

        ServicesHotels dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.SERVICESHOTEL_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getServiceHotel();
    }

    @Override
    public String create(ServicesHotels request) {

        StatusServiceHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICESHOTEL_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessServiceHotel().getValue();
    }

    @Override
    public String update(ServicesHotels request) {

        StatusServiceHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICESHOTEL_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessServiceHotel().getValue();
    }

    @Override
    public String delete(ServicesHotels request) {

        StatusServiceHotel dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICESHOTEL_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessServiceHotel().getValue();
    }
}
