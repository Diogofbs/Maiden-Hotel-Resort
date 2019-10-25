package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airport;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airports;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.SearchToken;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusAirport;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import java.util.List;


@Service
public class Airports_services implements Services_Interface<Airports,Airport> {

    @Autowired
    private ServicesModel<Airports,StatusAirport> operations;

    private WebServiceTemplate template;
    private String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.AIRPORT_PORT + Scheme_SOAP_Services_Locations.AIRPORT_SERVICE_SPECIFICATION;


    @Override
    public List<Airport> getAll() {

        Airports request = new Airports(); // Airports - send empty
        Airports dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getAirport();
    }

    @Override
    public List<Airport> getByID(Airports request) {

        Airports dataOutput = operations.getResult(uri, Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_READ_BY_ID, request);
        return dataOutput.getAirport();
    }

    @Override
    public String create(Airports request) {

        StatusAirport dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessAirport().getValue();
    }

    @Override
    public String update(Airports request){

        StatusAirport dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessAirport().getValue();
    }

    @Override
    public String delete(Airports request){

        StatusAirport dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessAirport().getValue();
    }

    public List<Airport> search(SearchToken token){

        template = operations.getTemplate();
        template.setDefaultUri(uri);

        Airports dataOutput = (Airports)  template.marshalSendAndReceive( token, message -> ((SoapMessage)message).setSoapAction(Scheme_SOAP_Services_Locations.AIRPORT_SOAP_ACTION_SEARCH_BY_TOKEN));
        return dataOutput.getAirport();
    }


}
