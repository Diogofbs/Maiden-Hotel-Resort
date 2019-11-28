package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Roles_services;
import com.maidenhotels.Backend.tibco.schemas.Role;
import com.maidenhotels.Backend.tibco.schemas.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Roles")
public class Role_Controller {

    @Autowired
    private Roles_services roles;
    //Creates objects with less and repeatable cod
    // Example: To create Roles: we need to create Role, set the attributes, instantiate Roles and then add Role to Roles
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Role> getAllRoles(){
        System.out.println("Getting all roles");
        return roles.getAll();
    }

    @PostMapping("RoleByParam")
    public List<Role> getRole(@RequestBody Role request ) {

        Roles rolesReq = new Roles();
        rolesReq.getRole().add(request);
        return roles.getByParam(rolesReq);
    }

    @PostMapping("RoleCreate")
    public String createRole(@RequestBody Role request ) {

        Roles rolesReq = new Roles();
        rolesReq.getRole().add(request);
        return roles.create(rolesReq);
    }

    @PostMapping("RoleDelete")
    public String deleteRole(@RequestBody Role request ) {

        Roles rolesReq = new Roles();
        rolesReq.getRole().add(request);
        return roles.delete(rolesReq);
    }

    @PostMapping("RoleUpdate")
    public String UpdateRole(@RequestBody Role request ) {

        Roles rolesReq = new Roles();
        rolesReq.getRole().add(request);
        return roles.update(rolesReq);
    }
}
