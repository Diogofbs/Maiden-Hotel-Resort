package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Services_services;
import com.maidenhotels.Backend.tibco.schemas.Service;
import com.maidenhotels.Backend.tibco.schemas.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Services")
public class Service_Controller {

    @Autowired
    private Services_services services;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Service> getAllServices(){
        return services.getAll();
    }

    @PostMapping("ServiceByID")
    public List<Service> getService(@RequestBody Service request ) {

        Services servicesReq = new Services();
        servicesReq.getService().add(request);
        return services.getByParam(servicesReq);
    }

    @PostMapping("ServiceCreate")
    public String createService(@RequestBody Service request ) {

        Services servicesReq = new Services();
        servicesReq.getService().add(request);
        return services.create(servicesReq);
    }

    @PostMapping("ServiceDelete")
    public String deleteService(@RequestBody Service request ) {

        Services servicesReq = new Services();
        servicesReq.getService().add(request);
        return services.delete(servicesReq);
    }

    @PostMapping("ServiceUpdate")
    public String UpdateService(@RequestBody Service request ) {

        Services servicesReq = new Services();
        servicesReq.getService().add(request);
        return services.update(servicesReq);
    }
}
