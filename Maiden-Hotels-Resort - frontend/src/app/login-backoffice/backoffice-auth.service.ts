import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ConstantsService } from '../common/services/constants.service';

@Injectable({
  providedIn: 'root'
})
export class BackofficeAuthService {

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
}
