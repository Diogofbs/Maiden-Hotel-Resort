package com.MaidenAirlineProject.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@CrossOrigin
@RestController
public class Welcome_Controller {

    //Colocar isto no service welcome quando criado. welcome.message -- application.properties
    @Value("${welcome.message}")
    private String welcomeMessage;

    @RequestMapping(value = "/welcome")
    public String welcome(){
        return welcomeMessage;
    }

    @RequestMapping(value = "/main")
    public ResponseEntity<Void> main2(){
        String a = "ol";
        if(a.equals("ola")){
        return ResponseEntity.badRequest().build();
        } else{
            return ResponseEntity.accepted().build();
        }
    }
}
