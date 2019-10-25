package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Flight;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Flights;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusFlight;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Flights_services implements Services_Interface<Flights, Flight> {

    @Autowired
    private ServicesModel<Flights, StatusFlight> operations;

    private WebServiceTemplate template ;
    private String uri = (Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.FLIGHT_PORT + Scheme_SOAP_Services_Locations.FLIGHT_SERVICE_SPECIFICATION);

    @Override
    public List<Flight> getAll() {

        Flights request = new Flights();
        Flights dataOutput = operations.getResult (uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_READ_ALL, request);
        return dataOutput.getFlight();
    }

    @Override
    public List<Flight> getByID(Flights request) {

        Flights dataOutput = operations.getResult (uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_READ_BY_ID, request);
        return dataOutput.getFlight();
    }

    @Override
    public String create(Flights request) {

        StatusFlight dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessFlight().getValue();
    }

    @Override
    public String update(Flights request) {

        StatusFlight dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessFlight().getValue();
    }

    @Override
    public String delete(Flights request) {

        StatusFlight dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessFlight().getValue();
    }

    public List<Flight> getByDateAndLocal(Flights request) {

        Flights dataOutput = operations.getResult (uri, Scheme_SOAP_Services_Locations.FLIGHT_SOAP_ACTION_READ_BY_DATE_AND_LOCAL, request);
        return dataOutput.getFlight();
    }
}
