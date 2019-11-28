package com.maidenhotels.Backend.security.tibcoToDB;

import com.maidenhotels.Backend.services.Guests_services;
import com.maidenhotels.Backend.tibco.schemas.*;
import com.maidenhotels.Backend.security.models.BackofficeDetailsModel;
import com.maidenhotels.Backend.security.models.ClientDetailsModel;
import com.maidenhotels.Backend.services.Backoffices_services;
import com.maidenhotels.Backend.services.Clients_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Service to get the details from the employee that's trying to login
// This Service use BackOffices_services to get the employee by username
@Service
public class UsersDetails_services implements UserDetailsService {

    @Autowired
    private Backoffices_services backoffices;

    @Autowired
    private Clients_services clients;

    @Autowired
    private Guests_services guests;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.contains("@")){

            Client client = new Client();
            Guest guest = new Guest();
            guest.setEmail(username);
            List<Guest> guestList = new ArrayList<Guest>();
            guestList.add(guest);

            Guest guestRequested = guests.getByParam(new Guests(guestList)).get(0);

            List<Client> clientList = new ArrayList<Client>();
            client.setIdGuest(guestRequested.getId());
            clientList.add(client);
            Client clientRequested = clients.getByParam(new Clients(clientList)).get(0);

            //Clients request = new Clients();
            //request.getClient().add(client);

            if(!guestRequested.getIdNumber().equals("")){
                ClientDetailsModel clientDetailsModel = new ClientDetailsModel(clientRequested, guestRequested);
                return clientDetailsModel;
            }else{
                //User not found
            }
//            List<Client> listClient = clients.getAll();
//            for(Client row : listClient){
//                if(row.getEmail().equals(username)){
//                    ClientDetailsModel clientDetailsModel = new ClientDetailsModel(row);
//                    return clientDetailsModel;
//                }else{
//                    //user not found
//                }
//            }

        }else{
            Backoffice backoffice = new Backoffice();
            backoffice.setUsername(username);

            Backoffices request = new Backoffices();
            request.getBackoffice().add(backoffice);

            //get(0) is used because getByUsername gives a list<Backoffice> with only one Backoffice(employee)
            Backoffice user = backoffices.getByParam(request).get(0);

            BackofficeDetailsModel backofficeDetailsModel = new BackofficeDetailsModel(user);

            return backofficeDetailsModel;
        }
        return null;
    }
}
