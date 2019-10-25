package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Role;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Roles;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusRole;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Roles_services implements Services_Interface<Roles, Role> {

    @Autowired
    private ServicesModel<Roles, StatusRole> operations;

    private WebServiceTemplate template;
    private String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.ROLE_PORT + Scheme_SOAP_Services_Locations.ROLE_SERVICE_SPECIFICATION;

    @Override
    public List<Role> getAll() {

        Roles request = new Roles(); // Roles - send empty
        Roles dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getRole();
    }

    @Override
    public List<Role> getByID(Roles request) {

        Roles dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getRole();
    }

    @Override
    public String create(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_CREATE,request);
        return dataOutput.getSuccessRole().getValue();
    }

    @Override
    public String update(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_UPDATE,request);
        return dataOutput.getSuccessRole().getValue();
    }

    @Override
    public String delete(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_DELETE,request);
        return dataOutput.getSuccessRole().getValue();
    }

    public List<Role> getByName(Roles request) {

        Roles dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_READ_BY_NAME,request);
        return dataOutput.getRole();
    }
}
