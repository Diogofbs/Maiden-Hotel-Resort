package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.Room;
import com.maidenhotels.Backend.tibco.schemas.Rooms;
import com.maidenhotels.Backend.tibco.schemas.StatusRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Collection;
import java.util.List;

@Service
public class Rooms_services implements Services_Interface<Rooms, Room> {

    @Autowired
    private ServicesModel<Rooms,StatusRoom> operations;

    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.ROOM_PORT + Scheme_SOAP_Services_Locations.ROOM_SERVICE_SPECIFICATION;


    @Override
    public List<Room> getAll() {

        Rooms request = new Rooms(); // --> send an empty Room
        Rooms dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROOM_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getRoom();
    }

    public List<Room> getByParam(Rooms request) {

        Rooms dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.ROOM_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getRoom();
    }

    @Override
    public String create(Rooms request) {

        StatusRoom dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOM_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessRoom().getValue();
    }

    @Override
    public String update(Rooms request) {

        StatusRoom dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOM_SOAP_ACTION_UPDATE, request);
        return dataOutput.getSuccessRoom().getValue();
    }

    @Override
    public String delete(Rooms request) {

        StatusRoom dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.ROOM_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessRoom().getValue();
    }
}
