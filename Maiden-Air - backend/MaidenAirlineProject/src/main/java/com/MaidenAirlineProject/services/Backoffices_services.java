package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffice;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffices;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusBackoffice;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Backoffices_services implements Services_Interface<Backoffices, Backoffice> {

    @Autowired
    private ServicesModel<Backoffices, StatusBackoffice> operations;

    private WebServiceTemplate template;
    private String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.BACKOFFICE_PORT + Scheme_SOAP_Services_Locations.BACKOFFICE_SERVICE_SPECIFICATION;


    @Override
    public List<Backoffice> getAll() {

        Backoffices request = new Backoffices(); // Backoffices - send empty
        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getBackoffice();
    }

    @Override
    public List<Backoffice> getByID(Backoffices request) {

        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getBackoffice();
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

    public List<Backoffice> getByUsername(Backoffices request) {

        Backoffices dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BACKOFFICE_SOAP_ACTION_READ_BY_USERNAME,request);
        return dataOutput.getBackoffice();
    }
}
