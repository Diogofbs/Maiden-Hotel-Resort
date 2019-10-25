package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.ClientType;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.ClientTypes;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusClientType;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class ClientTypes_services implements Services_Interface<ClientTypes, ClientType> {

    @Autowired
    private ServicesModel<ClientTypes, StatusClientType> operations;

    private WebServiceTemplate template ;
    private String uri = (Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.CLIENT_TYPE_PORT + Scheme_SOAP_Services_Locations.CLIENT_TYPE_SERVICE_SPECIFICATION);

    @Override
    public List<ClientType> getAll() {

        ClientTypes request = new ClientTypes(); // ClientTypes - send empty
        ClientTypes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_TYPE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getClientType();
    }

    @Override
    public List<ClientType> getByID(ClientTypes request) {

        ClientTypes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_TYPE_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getClientType();
    }

    @Override
    public String create(ClientTypes request) {

        StatusClientType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_TYPE_SOAP_ACTION_CREATE,request);
        return dataOutput.getSuccessClientType().getValue();
    }

    @Override
    public String update(ClientTypes request) {

        StatusClientType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_TYPE_SOAP_ACTION_UPDATE,request);
        return dataOutput.getSuccessClientType().getValue();
    }

    @Override
    public String delete(ClientTypes request) {

        StatusClientType dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_TYPE_SOAP_ACTION_DELETE,request);
        return dataOutput.getSuccessClientType().getValue();
    }
}
