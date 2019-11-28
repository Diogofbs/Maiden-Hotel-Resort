package com.maidenhotels.Backend.controllers;

import com.maidenhotels.Backend.controllers.factory.RequestCreator;
import com.maidenhotels.Backend.services.Rooms_services;
import com.maidenhotels.Backend.tibco.schemas.Room;
import com.maidenhotels.Backend.tibco.schemas.Rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("Rooms")
public class Room_Controller {

    @Autowired
    private Rooms_services rooms;
    //Creates objects with less and repeatable cod
    // Example: To create Clients: we need to create Client, set the attributes, instantiate Clients and then add Client to Clients
    @Autowired
    private RequestCreator factory;


    @GetMapping
    public List<Room> getAllRooms(){
        return rooms.getAll();
    }

    @PostMapping("RoomByID")
    public List<Room> getRoom(@RequestBody Room request ) {

        Rooms roomsReq = new Rooms();
        roomsReq.getRoom().add(request);
        return rooms.getByParam(roomsReq);
    }

    @PostMapping("RoomCreate")
    public String createRoom(@RequestBody Room request ) {

        Rooms roomsReq = new Rooms();
        roomsReq.getRoom().add(request);
        return rooms.create(roomsReq);
    }

    @PostMapping("RoomDelete")
    public String deleteRoom(@RequestBody Room request ) {

        Rooms roomsReq = new Rooms();
        roomsReq.getRoom().add(request);
        return rooms.delete(roomsReq);
    }

    @PostMapping("RoomUpdate")
    public String UpdateRoom(@RequestBody Room request ) {

        Rooms roomsReq = new Rooms();
        roomsReq.getRoom().add(request);
        return rooms.update(roomsReq);
    }
}
