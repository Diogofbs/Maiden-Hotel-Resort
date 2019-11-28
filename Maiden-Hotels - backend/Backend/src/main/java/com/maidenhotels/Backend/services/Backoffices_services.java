package com.maidenhotels.Backend.services;

import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.Backoffice;
import com.maidenhotels.Backend.tibco.schemas.Backoffices;
import com.maidenhotels.Backend.tibco.schemas.StatusBackoffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Backoffices_services implements Services_Interface<Backoffices, Backoffice> {

    @Autowired
    private ServicesModel<Backoffices,StatusBackoffice> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.BACKOFFICE_PORT + Scheme_SOAP_Services_Locations.BACKOFFICE_SERVICE_SPECIFICATION;


    @Override
    public List<Backoffice> getAll() {

        Backoffices request = new Backoffices(); // --> send an empty Backoffice
        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getBackoffice();
    }

    public List<Backoffice> getByParam(Backoffices request) {

        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getBackoffice();
    }

    public List<Backoffice> getByParamWithoutPassword(Backoffices request) {

        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_BY_PARAM,request);

        dataOutput.getBackoffice().get(0).setPassword("");
        return dataOutput.getBackoffice();
    }

    public List<Backoffice> getByID(Backoffices request) {
        return null;
    }

    @Override
    public String create(Backoffices request) {

        StatusBackoffice dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessBackoffice().getValue();
    }

    @Override
    public String update(Backoffices request) {

        StatusBackoffice dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessBackoffice().getValue();
    }

    @Override
    public String delete(Backoffices request) {

        StatusBackoffice dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessBackoffice().getValue();
    }
}
