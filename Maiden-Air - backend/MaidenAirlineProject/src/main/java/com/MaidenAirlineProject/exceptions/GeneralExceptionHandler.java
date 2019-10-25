package com.MaidenAirlineProject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(value = PassengerInputNotParsedException.class)
    public ResponseEntity<Object> handleParsePassenger(PassengerInputNotParsedException exception){
        return new ResponseEntity<>("Passenger message was not parsed. Passenger object was not created. Booking was not created", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PassengerNotCreatedException.class)
    public ResponseEntity<Object> handlePassengerNotFound(PassengerNotCreatedException exception){
        return new ResponseEntity<>("Client was not created from Passenger. Email, password and photo are the only parameters which can be null. Check date-of-birth: yyyy-MM-dd", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookingNotCreatedException.class)
    public ResponseEntity<Object> handleBookingCreation(BookingNotCreatedException exception){
        return new ResponseEntity<>("Booking was not created. Invalid input or problems accessing dateBase ", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = FlightNotExistException.class)
    public ResponseEntity<Object> handleNullFlightCreation(FlightNotExistException exception){
        return new ResponseEntity<>("Selected Flight does not exist ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ClientTypeNotExistException.class)
    public ResponseEntity<Object> handleNullClientType(ClientTypeNotExistException exception){
        return new ResponseEntity<>("Client Type does not exist ", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BookingTypeNotExistException.class)
    public ResponseEntity<Object> handleNullBookingType(BookingTypeNotExistException exception){
        return new ResponseEntity<>("BookingType does not exist ", HttpStatus.NOT_FOUND);
    }
}
