package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.security.SecurityEncoder;
import com.maidenhotels.Backend.services.Backoffices_services;
import com.maidenhotels.Backend.tibco.schemas.Backoffice;
import com.maidenhotels.Backend.tibco.schemas.Backoffices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Backoffices")
public class Backoffice_Controller {

    @Autowired
    private Backoffices_services backoffices;
    //Creates objects with less and repeatable cod
    // Example: To create Backoffices: we need to create Backoffice, set the attributes, instantiate Backoffices and then add Backoffice to Backoffices
    @Autowired
    private SecurityEncoder se;


    @GetMapping
    public List<Backoffice> getAllBackoffices(){
        System.out.println("Getting all backoffices");
        return backoffices.getAll();
    }

    @PostMapping("BackofficeByParam")
    public List<Backoffice> getBackoffice(@RequestBody Backoffice request ) {

        Backoffices backofficesReq = new Backoffices();
        backofficesReq.getBackoffice().add(request);
        return backoffices.getByParamWithoutPassword(backofficesReq);
    }

    @PostMapping("BackofficeCreate")
    public String createBackoffice(@RequestBody Backoffice request ) {

        Backoffices backofficesReq = new Backoffices();
        request.setPassword(se.passwordEncoder().encode(request.getPassword()));
        backofficesReq.getBackoffice().add(request);
        return backoffices.create(backofficesReq);
    }

    @PostMapping("BackofficeDelete")
    public String deleteBackoffice(@RequestBody Backoffice request ) {

        Backoffices backofficesReq = new Backoffices();
        backofficesReq.getBackoffice().add(request);
        return backoffices.delete(backofficesReq);
    }

    @PostMapping("BackofficeUpdate")
    public String UpdateBackoffice(@RequestBody Backoffice request ) {

        Backoffices backofficesReq = new Backoffices();
        request.setPassword(se.passwordEncoder().encode(request.getPassword()));
        backofficesReq.getBackoffice().add(request);
        return backoffices.update(backofficesReq);
    }
}
