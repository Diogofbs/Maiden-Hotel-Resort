package com.MaidenAirlineProject.services.interfaces;

public class Scheme_SOAP_Services_Locations {

    //--HOST--
    public static final String LOCALHOST = "http://localhost:";
    public static final String HOST = "http://192.168.0.189:";
    //public static final String HOST = "http://192.168.0.182:";
    //--END: HOSTS--

    //--PORTS--
    public static final String AIRPORT_PORT = "9081";
    public static final String AIRPLANE_PORT = "9080";
    public static final String FLIGHT_PORT = "9084";
    public static final String CLIENT_PORT = "9083";
    public static final String CLIENT_TYPE_PORT = "9088";
    public static final String BACKOFFICE_PORT = "9082";
    public static final String ROLE_PORT = "9085";
    public static final String BOOKING_PORT = "9087";
    public static final String BOOKING_TYPE_PORT = "9086";
    //--END: PORTS--

    //-- SERVICE SPECIFICATIONS--
    public static final String AIRPORT_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport";
    public static final String AIRPLANE_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane";
    public static final String FLIGHT_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight";
    public static final String CLIENT_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceClient.serviceagent/Client";
    public static final String CLIENT_TYPE_SERVICE_SPECIFICATION = "/SOAP%2520Process/Service.serviceagent/Client_Type";
    public static final String BACKOFFICE_SERVICE_SPECIFICATION = "/SOAP%2520Process/Service.serviceagent/Backoffice";
    public static final String ROLE_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceRole.serviceagent/Role";
    public static final String BOOKING_SERVICE_SPECIFICATION = "/SOAP%2520Process/Service.serviceagent/Booking";
    public static final String BOOKING_TYPE_SERVICE_SPECIFICATION = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type";
    //--END: SERVICE SPECIFICATIONS--


    //-- AIRPORT - SOAP ACTIONS--
    public static final String AIRPORT_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/InsertAirport";
    public static final String AIRPORT_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/UpdateAirport";
    public static final String AIRPORT_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/DeleteAirport";
    public static final String AIRPORT_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/GetAllAirports";
    public static final String AIRPORT_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/GetAirportByID";
    public static final String AIRPORT_SOAP_ACTION_SEARCH_BY_TOKEN = "/SOAP%2520Process/ServiceAirport.serviceagent/Airport/GetAirportBySearchToken";
    //--END: AIRPORT - SOAP ACTIONS--

    //-- AIRPLANE - SOAP ACTIONS--
    public static final String AIRPLANE_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane/InsertAirplane";
    public static final String AIRPLANE_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane/UpdateAirplane";
    public static final String AIRPLANE_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane/DeleteAirplane";
    public static final String AIRPLANE_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane/GetAllAirplanes";
    public static final String AIRPLANE_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceAirplane.serviceagent/Airplane/GetAirplaneByID";
    //--END: AIRPLANE - SOAP ACTIONS--

    //-- FLIGHT - SOAP ACTIONS--
    public static final String FLIGHT_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/InsertFlight";
    public static final String FLIGHT_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/UpdateFlight";
    public static final String FLIGHT_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/DeleteFlight";
    public static final String FLIGHT_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/GetAllFlights";
    public static final String FLIGHT_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/GetFlightByID";
    public static final String FLIGHT_SOAP_ACTION_READ_BY_DATE_AND_LOCAL = "/SOAP%2520Process/ServiceFlight.serviceagent/Flight/GetFlightByDateAndLocal";
    //--END: FLIGHT - SOAP ACTIONS--

    //-- CLIENT - SOAP ACTIONS--
    public static final String CLIENT_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceClient.serviceagent/Client/InsertClient";
    public static final String CLIENT_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceClient.serviceagent/Client/UpdateClient";
    public static final String CLIENT_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceClient.serviceagent/Client/DeleteClient";
    public static final String CLIENT_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceClient.serviceagent/Client/GetAllClients";
    public static final String CLIENT_SOAP_ACTION_READ_BY_ID_NUMBER = "/SOAP%2520Process/ServiceClient.serviceagent/Client/GetClientByID_Number";
    public static final String CLIENT_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceClient.serviceagent/Client/GetClientByID";
    public static final String CLIENT_SOAP_ACTION_READ_BY_EMAIL  = "/SOAP%2520Process/ServiceClient.serviceagent//GetClientByEmail";
    //--END: CLIENT - SOAP ACTIONS--

    //-- CLIENT_TYPE - SOAP ACTIONS--
    public static final String CLIENT_TYPE_SOAP_ACTION_CREATE = "/SOAP%2520Process/Service.serviceagent/Client_Type/InsertClient_Type";
    public static final String CLIENT_TYPE_SOAP_ACTION_UPDATE = "/SOAP%2520Process/Service.serviceagent/Client_Type/UpdateClient_Type";
    public static final String CLIENT_TYPE_SOAP_ACTION_DELETE = "/SOAP%2520Process/Service.serviceagent/Client_Type/DeleteClient_Type";
    public static final String CLIENT_TYPE_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/Service.serviceagent/Client_Type/GetAllClient_Type";
    public static final String CLIENT_TYPE_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/Service.serviceagent/Client_Type/GetClient_TypeByID";
    //--END: CLIENT_TYPE - SOAP ACTIONS--


    //-- BACKOFFICE - SOAP ACTIONS--
    public static final String BACKOFFICE_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceBackoffice.serviceagent//InsertBackoffice";
    public static final String BACKOFFICE_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceBackoffice.serviceagent//UpdateBackoffice";
    public static final String BACKOFFICE_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceBackoffice.serviceagent//DeleteBackoffice";
    public static final String BACKOFFICE_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceBackoffice.serviceagent//GetAllBackoffice";
    public static final String BACKOFFICE_SOAP_ACTION_READ_BY_USERNAME = "/SOAP%2520Process/ServiceBackoffice.serviceagent//GetBackofficeByUsername";
    public static final String BACKOFFICE_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceBackoffice.serviceagent//GetBackofficeByID";
    //--END: BACKOFFICE - SOAP ACTIONS--

    //-- ROLE - SOAP ACTIONS--
    public static final String ROLE_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceRole.serviceagent//InsertRole";
    public static final String ROLE_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceRole.serviceagent//UpdateRole";
    public static final String ROLE_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceRole.serviceagent//DeleteRole";
    public static final String ROLE_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceRole.serviceagent//GetAllRoles";
    public static final String ROLE_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceRole.serviceagent//GetRoleByID";
    public static final String ROLE_SOAP_ACTION_READ_BY_NAME = "/SOAP%2520Process/ServiceRole.serviceagent//GetRoleByName";
    //--END: ROLE - SOAP ACTIONS--

    //-- BOOKING - SOAP ACTIONS--
    public static final String BOOKING_SOAP_ACTION_CREATE = "/SOAP%2520Process/Service.serviceagent/Booking/InsertBooking";
    public static final String BOOKING_SOAP_ACTION_UPDATE = "/SOAP%2520Process/Service.serviceagent/Booking/UpdateBooking";
    public static final String BOOKING_SOAP_ACTION_DELETE = "/SOAP%2520Process/Service.serviceagent/Booking/DeleteBooking";
    public static final String BOOKING_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/Service.serviceagent/Booking/GetAllBookings";
    public static final String BOOKING_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/Service.serviceagent/Booking/GetBookingByID";
    public static final String BOOKING_SOAP_ACTION_UPDATE_CLIENT_FLIGHT = "/SOAP%2520Process/Service.serviceagent/Booking/UpdateBooking_Client_Flight";
    public static final String BOOKING_SOAP_ACTION_READ_BY_CLIENT_FLIGHT ="/SOAP%2520Process/ServiceBooking.serviceagent//GetBooking_Client_FlightByBooking_Client_Flight";

    public static final String BOOKING_SOAP_ACTION_READ_ALL_CLIENT_FLIGHT = "/SOAP%2520Process/ServiceBooking.serviceagent//GetAllBooking_Client_Flights";
    public static final String BOOKING_SOAP_ACTION_UPDATE_CLIENT_FLIGHT_BY_FLIGHTID ="/SOAP%2520Process/ServiceBooking.serviceagent//UpdateBooking_Client_FlightId_Flight";
    //--END: BOOKING - SOAP ACTIONS--

    //-- BOOKING_TYPE - SOAP ACTIONS--
    public static final String BOOKING_TYPE_SOAP_ACTION_CREATE = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type/InsertBookingType";
    public static final String BOOKING_TYPE_SOAP_ACTION_UPDATE = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type/UpdateBookingType";
    public static final String BOOKING_TYPE_SOAP_ACTION_DELETE = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type/DeleteBookingType";
    public static final String BOOKING_TYPE_SOAP_ACTION_READ_ALL = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type/GetAllBookingTypes";
    public static final String BOOKING_TYPE_SOAP_ACTION_READ_BY_ID = "/SOAP%2520Process/ServiceBooking_Type.serviceagent/Booking_Type/GetBookingTypeByID";
    //--END: BOOKING_TYPE - SOAP ACTIONS--



}
