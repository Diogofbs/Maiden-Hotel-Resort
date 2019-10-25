package com.MaidenAirlineProject.controllers;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Role;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Roles;
import com.MaidenAirlineProject.controllers.factory.RequestCreator;
import com.MaidenAirlineProject.services.Roles_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Roles")
public class Role_Controller {

    @Autowired
    private Roles_services roles;

    //Creates objects with less and repeatable code
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;

    @GetMapping
    public List<Role> getAllRoles() {

        return roles.getAll();
    }

    @PostMapping("RoleByID")
    public List<Role> getRoles(@RequestParam String id) {

        Roles request = factory.createRolesID(id);
        return roles.getByID(request);
    }

    @PostMapping("RoleCreate")
    public String createRole(@RequestParam String name) {

        Roles request = factory.createRoles("",name);
        return roles.create(request);
    }

    @PostMapping("RoleDelete")
    public String deleteRole(@RequestParam String id) {

        Roles request = factory.createRolesID(id);
        return roles.delete(request);
    }

    @PostMapping("RoleUpdate")
    public String updateRole(@RequestParam String id, @RequestParam String name) {

        Roles request = factory.createRoles("",name);
        return roles.update(request);
    }

    @PostMapping("RolesByName")
    public List<Role> getRolesByName(@RequestParam String name) {

        Roles request = factory.createRoles("",name);;
        return roles.getByName(request);
    }
}
