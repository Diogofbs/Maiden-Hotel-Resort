package com.MaidenAirlineProject.services;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Client;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Clients;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.StatusClient;
import com.MaidenAirlineProject.services.interfaces.Scheme_SOAP_Services_Locations;
import com.MaidenAirlineProject.services.interfaces.ServicesModel;
import com.MaidenAirlineProject.services.interfaces.Services_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Clients_services implements Services_Interface<Clients, Client> {

    @Autowired
    private ServicesModel<Clients,StatusClient> operations;

    private WebServiceTemplate template ;
    private String uri = (Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.CLIENT_PORT + Scheme_SOAP_Services_Locations.CLIENT_SERVICE_SPECIFICATION);

    @Override
    public List<Client> getAll() {

        Clients request = new Clients(); // Clients - send empty
        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getClient();
    }

    @Override
    public List<Client> getByID(Clients request) {

        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_BY_ID,request);
        return dataOutput.getClient();
    }

    @Override
    public String create(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_CREATE,request);
        return dataOutput.getSuccessClient().getValue();
    }

    @Override
    public String update(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_UPDATE,request);
        return dataOutput.getSuccessClient().getValue();
    }

    @Override
    public String delete(Clients request) {

        StatusClient dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_DELETE,request);
        return dataOutput.getSuccessClient().getValue();
    }

    public List<Client> getByID_Number(Clients request) {

        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_BY_ID_NUMBER,request);
        return dataOutput.getClient();
    }

    public List<Client> getByEmail(Clients request) {

        Clients dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.CLIENT_SOAP_ACTION_READ_BY_EMAIL,request);
        return dataOutput.getClient();
    }

    public int getClientAge(Clients request) {

        Client client = this.getByID(request).get(0);

        String date = client.getDateOfBirth().substring(0,10);
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate dateOfBirth = LocalDate.parse(date,formatter);
        LocalDate now = LocalDate.now();
        int age = Period.between(dateOfBirth,now).getYears();
        return age;
    }
}
