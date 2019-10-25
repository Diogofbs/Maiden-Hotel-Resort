package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffice;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffices;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.security.SecurityEncoder;
import com.MaidenAirlineProject.services.Backoffices_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Backoffices")
public class Backoffice_Controller {

    @Autowired
    private SecurityEncoder securityEncoder;

    @Autowired
    private Backoffices_services backoffices;

    //Creates objects with less and repeatable code
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<Backoffice> getAllBackoffices() {

        return backoffices.getAll();
    }

    @PostMapping("BackofficeByID")
    public List<Backoffice> getBackoffice(@RequestParam String id) {

        Backoffices request = factory.createBackofficesID(id);
        List<Backoffice> response =  backoffices.getByID(request);
        response.get(0).setPassword(null);
        return response;
    }

    @PostMapping("BackofficeCreate")
    public String createBackoffice(@RequestParam String idRole, @RequestParam String name, @RequestParam String username, @RequestParam String password) {

        Backoffices request = factory.createBackoffices("", idRole, name, securityEncoder.passwordEncoder().encode(password), username);
        return backoffices.create(request);
    }

    @PostMapping("BackofficeDelete")
    public String deleteBackoffice(@RequestParam String id) {

        Backoffices request = factory.createBackofficesID(id);
        return backoffices.delete(request);
    }

    @PostMapping("BackofficeUpdate")
    public String updateBackoffice(@RequestParam String id,@RequestParam String idRole, @RequestParam String name, @RequestParam String username, @RequestParam String password) {

        Backoffices request = factory.createBackoffices(id, idRole, name, securityEncoder.passwordEncoder().encode(password), username);
        return backoffices.update(request);
    }

    @PostMapping("BackofficeByUsername")
    public List<Backoffice> getBackofficeByUsername(@RequestParam String username) {

        Backoffices request = factory.createBackoffices("", "","", "", username);
        return backoffices.getByUsername(request);
    }

}
