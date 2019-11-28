package com.maidenhotels.Backend.services;

import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Clients_services implements Services_Interface<Clients, Client> {

    @Autowired
    private ServicesModel<Clients,StatusClient> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.CLIENT_PORT + Scheme_SOAP_Services_Locations.CLIENT_SERVICE_SPECIFICATION;


    @Override
    public List<Client> getAll() {

        Clients request = new Clients(); // --> send an empty Client
        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getClient();
    }

    public List<Client> getByParam(Clients request) {

        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getClient();
    }

    public List<Client> getByParamWithoutPassword(Clients request) {

        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_BY_PARAM,request);

        dataOutput.getClient().get(0).setPassword("");
        return dataOutput.getClient();
    }


    @Override
    public String create(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessClient().getValue();
    }

    @Override
    public String update(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessClient().getValue();
    }

    @Override
    public String delete(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessClient().getValue();
    }
}
