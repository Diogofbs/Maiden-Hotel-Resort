import { Injectable } from '@angular/core';

import { ConstantsService } from '../common/services/constants.service';
import { HttpHeaders, HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientAuthService {

    constructor( private httpClient: HttpClient, private constants: ConstantsService  ) { }

    private httpOptions = {
        headers: new HttpHeaders({
          'Content-Type':  'application/json'
        }),
        observe: 'response'
    };


    authenticate(username: string, password: string) {
        return this.httpClient.post<any>(this.constants.webServicesUrl + '/login', {username, password}, {observe: 'response'});
    }

    /*isUserLoggedIn() {
      const user = localStorage.getItem('name');
      return !(user === null);
    }

    logOut() {
      localStorage.removeItem('name');
    }*/
}
