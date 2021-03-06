import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConstantsService } from '../common/services/constants.service';
import { Guest } from './guests.model';

@Injectable({providedIn: 'root'})
export class GuestsService {

    constructor(private http: HttpClient, private constants:ConstantsService){}

    createAndStoreGuest(firstName: string,
        lastName: string,
        dateOfBirth: string,
        gender: string,
        idNumber: number,
        address: string,
        contactNumber: number,
        email: string,
        status: string){

          let  data: {id: string,
            firstName: string,
            lastName: string,
            dateOfBirth: string,
            gender: string,
            idNumber: number,
            address: string,
            contactNumber: number,
            email: string,
            status: string}//new Guest("", firstName,lastName,dateOfBirth,gender, idNumber, address, contactNumber, email, status);
            data = {id: "",firstName: firstName, lastName: lastName, dateOfBirth: dateOfBirth, gender: gender, idNumber: idNumber, address: address, contactNumber: contactNumber, email: email, status: status};
        return this.http.post(this.constants.webServicesUrl+'/Guests/GuestCreate',data);
    }

    updateGuest(id: number,firstName: string,
        lastName: string,
        dateOfBirth: string,
        idNumber: number,
        address: string,
        contactNumber: number,
        gender: string,
        email: string,
        status: string){

            let data: {
                id: number,
                firstName: string,
                lastName: string,
                dateOfBirth: string,
                gender: string,
                idNumber: number,
                address: string,
                contactNumber: number,
                email: string,
                status: string
            };
            data = {id: id,firstName: firstName, lastName: lastName, dateOfBirth: dateOfBirth, gender: gender, idNumber: idNumber, address: address, contactNumber: contactNumber, email: email, status: status};
        return this.http.post(this.constants.webServicesUrl+'/Guests/GuestUpdate',data);
    }

    deleteGuest(id: number){
        let data: {
            id: number
        }
        data = {id: id};
        return this.http.post(this.constants.webServicesUrl+'/Guests/GuestDelete', data);
    }

    fetchGuests() {
        return this.http.get<{id: number, firstName: string, lastName: string, dateOfBirth: string, gender: string, idNumber: number, address: string, contactNumber: number, email: string, status: string}[]>(this.constants.webServicesUrl+'/Guests');
    }

    getGuest(id: number, email: string) {
        let data: {
            id: number,
            email: string
        }
        data = {id: id, email: email};
        return this.http.post<Guest>(this.constants.webServicesUrl + '/Guests/GuestByID', data);
    }
}
