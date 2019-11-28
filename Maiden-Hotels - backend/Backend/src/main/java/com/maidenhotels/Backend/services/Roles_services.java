package com.maidenhotels.Backend.services;

import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.Role;
import com.maidenhotels.Backend.tibco.schemas.Roles;
import com.maidenhotels.Backend.tibco.schemas.StatusRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.List;

@Service
public class Roles_services implements Services_Interface<Roles, Role> {

    @Autowired
    private ServicesModel<Roles,StatusRole> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.ROLE_PORT + Scheme_SOAP_Services_Locations.ROLE_SERVICE_SPECIFICATION;


    @Override
    public List<Role> getAll() {

        Roles request = new Roles(); // --> send an empty Role
        Roles dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getRole();
    }

    public List<Role> getByParam(Roles request) {

        Roles dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getRole();
    }

    public List<Role> getByID(Roles request) {
        return null;
    }

    @Override
    public String create(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessRole().getValue();
    }

    @Override
    public String update(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessRole().getValue();
    }

    @Override
    public String delete(Roles request) {

        StatusRole dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROLE_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessRole().getValue();
    }
}
