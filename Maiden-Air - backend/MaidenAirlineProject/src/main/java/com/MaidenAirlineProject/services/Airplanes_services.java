package com.MaidenAirlineProject.services;


import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airplane;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Airplanes;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusAirplane;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Airplanes_services implements Services_Interface<Airplanes, Airplane> {

    @Autowired
    private ServicesModel<Airplanes,StatusAirplane> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.AIRPLANE_PORT + Scheme_SOAP_Services_Locations.AIRPLANE_SERVICE_SPECIFICATION;


    @Override
    public List<Airplane> getAll() {

        Airplanes request = new Airplanes(); // --> send an empty Airplane
        Airplanes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.AIRPLANE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getAirplane();
    }

    @Override
    public List<Airplane> getByID(Airplanes request) {

        Airplanes dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.AIRPLANE_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getAirplane();
    }

    @Override
    public String create(Airplanes request) {

        StatusAirplane dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPLANE_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessAirplane().getValue();
    }

    @Override
    public String update(Airplanes request) {

        StatusAirplane dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPLANE_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessAirplane().getValue();
    }

    @Override
    public String delete(Airplanes request) {

        StatusAirplane dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.AIRPLANE_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessAirplane().getValue();
    }
}
