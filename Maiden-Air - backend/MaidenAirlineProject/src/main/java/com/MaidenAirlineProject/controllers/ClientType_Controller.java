package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.ClientType;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.ClientTypes;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.ClientTypes_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("ClientTypes")
public class ClientType_Controller {

    @Autowired
    private ClientTypes_services clientTypes;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add CLient to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<ClientType> getAllClientTpes(){

        return clientTypes.getAll();
    }

    @PostMapping("ClientTypeByID")
    public List<ClientType> getClientType(@RequestParam String id) {

        ClientTypes request = factory.createClientTypesID(id);
        return clientTypes.getByID(request);
    }

    @PostMapping("ClientTypeCreate")
    public String createClientType(@RequestParam String annualFee, @RequestParam String bonusMiles,@RequestParam String monthlyMiles,
                               @RequestParam String name, @RequestParam String welcomeBonus) {

        ClientTypes request = factory.createClientTypes(annualFee,bonusMiles,"",monthlyMiles,name,welcomeBonus);
        return clientTypes.create(request);
    }

    @PostMapping("ClientTypeDelete")
    public String deleteClientType(@RequestParam String id) {

        ClientTypes request = factory.createClientTypesID(id);
        return clientTypes.delete(request);
    }

    @PostMapping("ClientTypeUpdate")
    public String updateClientType(@RequestParam String id, @RequestParam String annualFee, @RequestParam String bonusMiles,@RequestParam String monthlyMiles,
                                   @RequestParam String name, @RequestParam String welcomeBonus) {

        ClientTypes request = factory.createClientTypes(annualFee,bonusMiles,id,monthlyMiles,name,welcomeBonus);
        return clientTypes.update(request);
    }
}
