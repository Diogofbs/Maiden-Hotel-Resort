package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.Service;
import com.maidenhotels.Backend.tibco.schemas.Services;
import com.maidenhotels.Backend.tibco.schemas.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@org.springframework.stereotype.Service
public class Services_services implements Services_Interface<Services, Service> {

    @Autowired
    private ServicesModel<Services,StatusService> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.SERVICE_PORT + Scheme_SOAP_Services_Locations.SERVICE_SERVICE_SPECIFICATION;


    @Override
    public List<Service> getAll() {

        Services request = new Services(); // --> send an empty Service
        Services dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.SERVICE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getService();
    }

    public List<Service> getByParam(Services request) {

        Services dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.SERVICE_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getService();
    }

    @Override
    public String create(Services request) {

        StatusService dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICE_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessService().getValue();
    }

    @Override
    public String update(Services request) {

        StatusService dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICE_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessService().getValue();
    }

    @Override
    public String delete(Services request) {

        StatusService dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.SERVICE_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessService().getValue();
    }
}
