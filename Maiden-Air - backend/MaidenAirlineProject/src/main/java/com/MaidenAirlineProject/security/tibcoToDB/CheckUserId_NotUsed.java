package com.MaidenAirlineProject.security.tibcoToDB;

import com.MaidenAirlineProject.services.Clients_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

// ---- THIS CLASS IS NOT BEEING USED ---- //
// ---- ---- ---- WEBSITE SHOWING THE PURPOSE OF IT: https://stackoverflow.com/questions/33115446/authorization-in-spring-security-based-on-path-variables ---- //
// ---- ---- ---- ---- ---- ---- ---- TO BE USE WITH:  clientByEmail method from Clients_Services

@Component
public class CheckUserId_NotUsed {

    @Autowired
    private Clients_services client;


    public boolean check(Authentication authentication, int id) {
        String name = authentication.getName();
        System.out.println(name+" at "+id);
        if(name.contains("@")){
            //find by email
        }
        //User result = repo.findByUsername(name);
        //return result != null && result.getPid() == id;
        return false;
    }
}
