package com.MaidenAirlineProject.security.tibcoToDB;

import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffice;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Backoffices;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Client;
import com.MaidenAirlineProject.TIBCO.generatedSchemas.Clients;
import com.MaidenAirlineProject.security.models.BackofficeDetailsModel;
import com.MaidenAirlineProject.security.models.ClientDetailsModel;
import com.MaidenAirlineProject.services.Backoffices_services;
import com.MaidenAirlineProject.services.Clients_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Service to get the details from the employee that's trying to login
// This Service use BackOffices_services to get the employee by username
@Service
public class UsersDetails_services implements UserDetailsService {

    @Autowired
    private Backoffices_services backoffices;

    @Autowired
    private Clients_services clients;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username.contains("@")){

            Client client = new Client();
            client.setEmail(username);

            Clients request = new Clients();
            request.getClient().add(client);

            Client clientRequested = clients.getByEmail(request).get(0);
            if(!clientRequested.getIdNumber().equals("")){
                ClientDetailsModel clientDetailsModel = new ClientDetailsModel(clientRequested);
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
            Backoffice user = backoffices.getByUsername(request).get(0);

            BackofficeDetailsModel backofficeDetailsModel = new BackofficeDetailsModel(user);

            return backofficeDetailsModel;
        }
        return null;
    }
}
