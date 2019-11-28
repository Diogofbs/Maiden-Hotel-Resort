package com.maidenhotels.Backend.services;


import com.maidenhotels.Backend.services.interfaces.Scheme_SOAP_Services_Locations;
import com.maidenhotels.Backend.services.interfaces.ServicesModel;
import com.maidenhotels.Backend.services.interfaces.Services_Interface;
import com.maidenhotels.Backend.tibco.schemas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service
public class Bookings_services implements Services_Interface<Bookings, Booking> {

    @Autowired
    private ServicesModel<Bookings, StatusBooking> operations;

    @Autowired
    private ServicesModel<BookingsServices, StatusBooking> operationsServices;

    @Autowired
    private ServicesModel<Bookings, OccupiedRooms> operationsRooms;

    @Autowired
    private RoomsHotel_services roomsHotelServices;

    @Autowired
    private Guests_services guestsServices;

    @Autowired
    private Clients_services clientsServices;



    private WebServiceTemplate template;
    String uri = Scheme_SOAP_Services_Locations.HOST + Scheme_SOAP_Services_Locations.BOOKING_PORT + Scheme_SOAP_Services_Locations.BOOKING_SERVICE_SPECIFICATION;

    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public List<Booking> getAll() {

        Bookings request = new Bookings(); // --> send an empty ServicesHotel
        Bookings dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_ALL,request);
        return dataOutput.getBooking();
    }

    public List<Booking> getByParam(Bookings request) {

        Bookings dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_BY_PARAM,request);
        return dataOutput.getBooking();
    }

    public List<Booking> getByRoom(Bookings request) {

        Bookings dataOutput = operations.getResult(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_BY_ROOM,request);
        return dataOutput.getBooking();
    }

    @Override
    public String create(Bookings request){

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_CREATE, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public Map<String,Object> createWithBill(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_CREATE, request);

        if(Integer.parseInt(dataOutput.getSuccessBooking().getValue()) > 0){
            List<RoomsHotel> roomsHotelList = new ArrayList<>();
            //RoomsHotel roomsHotel = new RoomsHotel();
            float totalCost = 0;
            for( BkroomsHotel bkRoomsHotel: request.getBooking().get(0).getBkroomsHotel()){

                RoomsHotel roomsHotel = new RoomsHotel();
                roomsHotel.setId(bkRoomsHotel.getId());

                List<RoomsHotel> list = new ArrayList<>();
                list.add(roomsHotel);

                List<RoomsHotel> newlist = roomsHotelServices.getByParam(new RoomsHotels(list));
                roomsHotelList.add(newlist.get(0));
                totalCost += Float.parseFloat(newlist.get(0).getCost()) * getDays(request.getBooking().get(0).getCheckInDate(), request.getBooking().get(0).getCheckOutDate());
            }
            BillingReservation bReservation = new BillingReservation(roomsHotelList,totalCost,dataOutput.getSuccessBooking().getValue());

            Map<String, Object> response = new HashMap<>();
            response.put("Result", dataOutput.getSuccessBooking().getValue());
            response.put("Bill", bReservation);

            return response;

        }else{
            Map<String, Object> response = new HashMap<>();
            response.put("Result", dataOutput.getSuccessBooking().getValue());
            return response;
        }


    }

    private long getDays(String date1, String date2){
        try {
            Date dateFirst = myFormat.parse(date1);
            Date dateSecond = myFormat.parse(date2);
            long diff = dateSecond.getTime() - dateFirst.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

    }


    @Override
    public String update(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE, request);

        if(Integer.parseInt( dataOutput.getSuccessBooking().getValue()) > 0 && request.getBooking().get(0).getStatus().compareTo("inactive") == 0){

            String idNumber = getByParam(request).get(0).getBkGuests().get(0).getIdNumber();
            Guest guest = new Guest();
            guest.setIdNumber(idNumber);

            List<Guest> guests = new ArrayList<>();
            guests.add(guest);

            Guests requestGuest = new Guests(guests);

            guests = new ArrayList<>();
            guests = guestsServices.getByParam(requestGuest);

            System.out.println("Guest: "+guests.get(0).getId() +" "+guests.get(0).getFirstName());
            String idGuest = guests.get(0).getId();

            Client client = new Client();
            client.setIdGuest(idGuest);

            List<Client> clients = new ArrayList<>();
            clients.add(client);

            Clients requestClient = new Clients(clients);
            clients = new ArrayList<>();
            clients = clientsServices.getByParam(requestClient);

            if(clients.get(0).getId().compareTo("") != 0) {
                System.out.println("client:" + clients.get(0).getId() +" "+clients.get(0).getIdGuest()+" "+clients.get(0).getPassword());
                List<BkServices> bkServicesList = getByParam(request).get(0).getBkServices();
                float credits = 0;
                for (BkServices bkService : bkServicesList) {
                    credits += Float.parseFloat(bkService.getCost()) * 0.1;
                }
                System.out.println("credits:" + credits);
                clients.get(0).setMoneySpent((Float.parseFloat(clients.get(0).getMoneySpent()) + credits) + "");
                requestClient = new Clients(clients);
                clientsServices.update(requestClient);
            }
        }
        return dataOutput.getSuccessBooking().getValue();
    }

    @Override
    public String delete(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_DELETE, request);
        return dataOutput.getSuccessBooking().getValue();
    }


    public String updateServices(BookingsServices request) {

        StatusBooking dataOutput = operationsServices.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE_SERVICES, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public String updateGuests(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE_GUESTS, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public String updateRooms(Bookings request) {

        StatusBooking dataOutput = operations.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_UPDATE_ROOMS, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public String insertServices(BookingsServices request) {

        StatusBooking dataOutput = operationsServices.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_INSERT_SERVICES, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public String deleteServices(BookingsServices request) {

        StatusBooking dataOutput = operationsServices.getStatus(uri, Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_DELETE_SERVICES, request);
        return dataOutput.getSuccessBooking().getValue();
    }

    public List<OccupiedRoom> getOccupiedRooms(Bookings request) {

        OccupiedRooms dataOutput = operationsRooms.getStatus(uri,Scheme_SOAP_Services_Locations.BOOKING_SOAP_ACTION_READ_OCCUPIED_ROOMS,request);
        return dataOutput.getOccupiedRoom();
    }

}
