package com.maidenhotels.Backend.services.interfaces;

public class Scheme_SOAP_Services_Locations {

    //--HOST--
    public static final String HOST = "http://localhost:";
    // public static final String HOST = "http://192.168.0.77:";
    //public static final String HOST = "http://192.168.0.182:";
    //--END: HOSTS--

    //--PORTS--
    public static final String HOTEL_PORT = "10100";
    public static final String ROOM_PORT = "10103";
    public static final String SERVICE_PORT = "10101";
    public static final String CLIENT_PORT = "10106";
    public static final String GUEST_PORT = "10105";
    public static final String ROOMSHOTEL_PORT = "10104";
    public static final String SERVICESHOTEL_PORT = "10102";
    public static final String BOOKING_PORT = "10107";
    public static final String ROLE_PORT = "10108";
    public static final String BACKOFFICE_PORT = "10109";


    //-- SERVICE SPECIFICATIONS--
    public static final String HOTEL_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/PortType_Hotels";
    public static final String ROOM_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room";
    public static final String SERVICE_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_Services";
    public static final String CLIENT_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients";
    public static final String GUEST_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests";
    public static final String ROOMSHOTEL_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels";
    public static final String SERVICESHOTEL_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotel";
    public static final String BOOKING_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent/PortTyp_Bookings";
    public static final String ROLE_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_Roles";
    public static final String BACKOFFICE_SERVICE_SPECIFICATION = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice";

    //--END: SERVICE SPECIFICATIONS--


    //-- HOTEL - SOAP ACTIONS--
    public static final String HOTEL_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/PortType_Hotels/insert";
    public static final String HOTEL_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/PortType_Hotels/Update";
    public static final String HOTEL_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/PortType_Hotels/Delete";
    public static final String HOTEL_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/PortType_Hotels/Get_All";
    public static final String HOTEL_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Hotels.serviceagent/Get_ByParam";
    //--END: HOTEL - SOAP ACTIONS--

    //-- ROOM - SOAP ACTIONS--
    public static final String ROOM_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room/Insert";
    public static final String ROOM_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room/Update";
    public static final String ROOM_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room/Delete";
    public static final String ROOM_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room/Get_All";
    public static final String ROOM_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms.serviceagent/PortType_Room/Get_ByParam";
    //--END: ROOM - SOAP ACTIONS--

    //-- ROOMSHOTEL - SOAP ACTIONS--
    public static final String ROOMSHOTEL_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels/Insert";
    public static final String ROOMSHOTEL_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels/Update";
    public static final String ROOMSHOTEL_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels/Delete";
    public static final String ROOMSHOTEL_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels/Get_All";
    public static final String ROOMSHOTEL_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Rooms_hotel.serviceagent/PortType_RoomsHotels/Get_ByParam";
    //--END: ROOMSHOTEL - SOAP ACTIONS--

    //-- SERVICE - SOAP ACTIONS--
    public static final String SERVICE_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_ServicesEndpoint1/Insert";
    public static final String SERVICE_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_ServicesEndpoint1/Update";
    public static final String SERVICE_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_ServicesEndpoint1/Delete";
    public static final String SERVICE_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_ServicesEndpoint1/Get_All";
    public static final String SERVICE_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services.serviceagent/PortType_ServicesEndpoint1/Get_ByParam";
    //--END: SERVICE - SOAP ACTIONS--

    //-- SERVICESHOTEL - SOAP ACTIONS--
    public static final String SERVICESHOTEL_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotelEndpoint1/Insert";
    public static final String SERVICESHOTEL_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotelEndpoint1/Update";
    public static final String SERVICESHOTEL_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotelEndpoint1/Delete";
    public static final String SERVICESHOTEL_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotelEndpoint1/Get_All";
    public static final String SERVICESHOTEL_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Services_hotel.serviceagent/PortType_ServiceHotelEndpoint1/Get_ByParam";
    //--END: SERVICESHOTEL - SOAP ACTIONS--

    //-- CLIENT - SOAP ACTIONS--
    public static final String CLIENT_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients/Insert";
    public static final String CLIENT_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients/Update";
    public static final String CLIENT_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients/Delete";
    public static final String CLIENT_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients/Get_All";
    public static final String CLIENT_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Clients.serviceagent/PortType_Clients/Get_ByParam";
    //--END: CLIENT - SOAP ACTIONS--

    //-- CLIENT - SOAP ACTIONS--
    public static final String GUEST_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Insert";
    public static final String GUEST_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Update";
    public static final String GUEST_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Delete";
    public static final String GUEST_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Get_All";
    public static final String GUEST_SOAP_ACTION_READ_BY_ID = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Get_ByParam";
    public static final String GUEST_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Guests.serviceagent/PortType_Guests/Get_ByParam";
    //--END: CLIENT - SOAP ACTIONS--

    //-- BOOKING - SOAP ACTIONS--
    public static final String BOOKING_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Insert";
    public static final String BOOKING_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Update";
    public static final String BOOKING_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Delete";
    public static final String BOOKING_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Get_All";
    public static final String BOOKING_SOAP_ACTION_READ_BY_ROOM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Get_ByRoom";
    public static final String BOOKING_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Get_ByID";
    public static final String BOOKING_SOAP_ACTION_UPDATE_SERVICES = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//UpdateServices";
    public static final String BOOKING_SOAP_ACTION_INSERT_SERVICES = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//InsertServices";
    public static final String BOOKING_SOAP_ACTION_DELETE_SERVICES = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//DeleteServices";
    public static final String BOOKING_SOAP_ACTION_UPDATE_ROOMS = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//UpdateRooms";
    public static final String BOOKING_SOAP_ACTION_UPDATE_GUESTS = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//UpdateGuests";
    public static final String BOOKING_SOAP_ACTION_READ_OCCUPIED_ROOMS = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Bookings.serviceagent//Get_OccupiedRooms";
    //--END: BOOKING - SOAP ACTIONS--

    //-- ROLES - SOAP ACTIONS--
    public static final String ROLE_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_RolesEndpoint1/Insert";
    public static final String ROLE_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_RolesEndpoint1/Update";
    public static final String ROLE_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_RolesEndpoint1/Delete";
    public static final String ROLE_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_RolesEndpoint1/Get_All";
    public static final String ROLE_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Roles.serviceagent/PortType_RolesEndpoint1/Get_ByParam";
    //--END: ROLES - SOAP ACTIONS--

    //-- BACKOFFICE - SOAP ACTIONS--
    public static final String BACKOFFICE_SOAP_ACTION_CREATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice/Insert";
    public static final String BACKOFFICE_SOAP_ACTION_UPDATE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice/Update";
    public static final String BACKOFFICE_SOAP_ACTION_DELETE = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice/Delete";
    public static final String BACKOFFICE_SOAP_ACTION_READ_ALL = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice/Get_All";
    public static final String BACKOFFICE_SOAP_ACTION_READ_BY_PARAM = "/Maiden_Hotels_Project/ServicesCRUD_SOAP/Service_Backoffices.serviceagent/PortType_Backoffice/Get_ByParam";
    //--END: BACKOFFICE - SOAP ACTIONS--



}
