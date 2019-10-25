package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.*;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.exceptions.ClientNotExistException;
import com.MaidenAirlineProject.exceptions.ClientTypeNotExistException;
import com.MaidenAirlineProject.security.SecurityEncoder;
import com.MaidenAirlineProject.services.ClientTypes_services;
import com.MaidenAirlineProject.services.Clients_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Clients")
public class Client_Controller {

    @Autowired
    private SecurityEncoder securityEncoder;
    @Autowired
    private Clients_services clients;
    @Autowired
    private ClientTypes_services clientTypes;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add CLient to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Client> getAllClients(){

        return clients.getAll();
    }

    @PostMapping("ClientByID")
    public List<Client> getClient(@RequestParam String id) {


        Clients request = factory.createClientsID(id);

        List<Client> response =  clients.getByID(request);
        response.get(0).setPassword(null);

        return response;
    }

    //Controller not used
    @GetMapping("/Client/{id}")
    public List<Client> getClientByID(@PathVariable String id) {

        Clients request = factory.createClientsID(id);
        return clients.getByID(request);
    }


    @PostMapping("ClientCreate")
    public String createClient(@RequestParam String address, @RequestParam String contactNumber, @RequestParam String dateOfBirth, @RequestParam String email,
                               @RequestParam String firstName, @RequestParam String gender, @RequestParam String idNumber, @RequestParam String idTypeClient,
                               @RequestParam String lastName, @RequestParam String password, @RequestParam String photo) {

        ClientTypes requestClientType = factory.createClientTypesID(idTypeClient);
        String miles = clientTypes.getByID(requestClientType).get(0).getWelcomeBonus();

        Clients request = factory.createClients(address, contactNumber, dateOfBirth, email, firstName, gender,"", idNumber, idTypeClient, lastName, miles, securityEncoder.passwordEncoder().encode(password), photo);
        return clients.create(request);
    }

    @PostMapping("ClientDelete")
    public String deleteClient(@RequestParam String id) {

        Clients request = factory.createClientsID(id);
        return clients.delete(request);
    }

    @PostMapping("ClientUpdate")
    public String updateClient(@RequestParam String address, @RequestParam String contactNumber, @RequestParam String dateOfBirth, @RequestParam String email,
                               @RequestParam String firstName, @RequestParam String gender,@RequestParam String id, @RequestParam String idNumber, @RequestParam String idTypeClient,
                               @RequestParam String lastName, @RequestParam String password, @RequestParam String photo) {

        String numberMiles = new String();

        //if clause only use if client updates his ClientType
        if(idTypeClient.length()>0){

            // Checks the previous clientType and his current Miles.
            Clients request = factory.createClientsID(id);
            Client response =  clients.getByID(request).get(0);
            //Dealing with wrong id or non-existing client
            if(response.getIdNumber().equals("")){
                throw new ClientNotExistException();
            }
            String previousClientType = response.getIdTypeClient();
            int curentMiles = Integer.parseInt(response.getNumberMiles());
            // result: current Miles and previousClientType

            //Checking welcomeBonus of previousClientType
            ClientTypes requestClientType = factory.createClientTypesID(previousClientType);
            int welcomeBonus = Integer.parseInt(clientTypes.getByID(requestClientType).get(0).getWelcomeBonus());

            //Checking welcomeBonus of the new ClientType
            requestClientType.getClientType().get(0).setID(idTypeClient);
            int miles = 0;
            //dealing with non-existing ClientType
            try{
                miles = Integer.parseInt(clientTypes.getByID(requestClientType).get(0).getWelcomeBonus());
            }catch(Exception e){
                throw new ClientTypeNotExistException();
            }

            //adjust welcomeBonus
            if(welcomeBonus>=miles){
                //If previous ClientType has a bigger welcomeBonus client does not receive extra miles
                numberMiles = Integer.toString(curentMiles); //miles = currentMiles
            }else{
                //else new welcomeBonus added to currentMiles
                numberMiles = Integer.toString(miles+curentMiles);
            }
        }

        // Updating client info
        Clients request = factory.createClients(address, contactNumber, dateOfBirth, email, firstName, gender,id, idNumber, idTypeClient, lastName, numberMiles, securityEncoder.passwordEncoder().encode(password), photo);
        return clients.update(request);
    }


    @PostMapping("ClientByID_Number")
    public List<Client> getClientByID_Number(@RequestParam String id_Number) {

        Clients request = factory.createClients("", "", "", "", "", "","", id_Number, "", "", "", "","");

        List<Client> response = clients.getByID_Number(request);
        response.get(0).setPassword(null);
        return response;
    }

    @PostMapping("ClientByEmail")
    public List<Client> getClientByEmail(@RequestParam String email) {

        Clients request = factory.createClients("", "", "", email, "", "","", "", "", "", "", "","");

        List<Client> response =  clients.getByEmail(request);
        response.get(0).setPassword(null);

        return response;
    }
}
