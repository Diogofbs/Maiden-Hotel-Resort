package com.maidenhotels.Backend.services.interfaces;


import java.util.Collection;

//Interface to implement in all Services
//Generic classes - I,O examples: Airports/Airport || Airplanes/Airplane ...
public interface Services_Interface<I,O> {

    // read all
    Collection<O> getAll();

    //read specific object - list of one element
    Collection<O> getByParam(I object);

    String create(I object);
    String update(I object);
    String delete(I object);

}
