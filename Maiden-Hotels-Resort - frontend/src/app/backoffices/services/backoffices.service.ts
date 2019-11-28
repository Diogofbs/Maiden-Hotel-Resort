import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Backoffice } from '../models/backoffice.model';

@Injectable({
  providedIn: 'root'
})
export class BackofficesService {

    constructor(private http: HttpClient, private constants: ConstantsService) {}

    createAndStoreBackoffice(name: string, username: string, idRole: number, password: string) {
        // console.log('inserting: ');
        let data: {
            name: string,
            username: string,
            idRole: number,
            password: string,
        };


        data = {name: name, username: username, idRole: idRole, password: password};
        return this.http.post(this.constants.webServicesUrl + '/Backoffices/BackofficeCreate', data);
    }

    updateBackoffice(id: number, name: string, username: string, idRole: number, password: string) {
        let data: {
            id: number,
            name: string,
            username: string,
            idRole: number,
            password: string,
        };
        data = {id: id, name: name, username: username, idRole: idRole, password: password};
        return this.http.post(this.constants.webServicesUrl + '/Backoffices/BackofficeUpdate', data);
        }

    deleteBackoffice(id: number) {
        let data: {
            id: number,
            name: string,
            username: string,
            idRole: number,
            password: string,
        };
        data = { id: id, name: '', username: '', idRole: null, password: ''};
        return this.http.post(this.constants.webServicesUrl + '/Backoffices/BackofficeDelete', data);
        }

        fetchBackoffices() {

            return this.http.get<Backoffice[]>(this.constants.webServicesUrl + '/Backoffices');
        }

    getBackoffice(id: number, username: string) {
        let data: {
            id: number,
            name: string,
            username: string,
            idRole: number,
            password: string,
        };
        data = { id: id, name: '', username: username, idRole: null, password: ''};
        return this.http.post<Backoffice[]>(this.constants.webServicesUrl + '/Backoffices/BackofficeByParam', data);
    }
}
