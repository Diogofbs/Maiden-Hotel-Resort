import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Role } from '../models/roles.model';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

    constructor(private http: HttpClient, private constants: ConstantsService) {}

    createAndStoreRole(name: string) {
      // console.log('inserting role ');
      let data: {
          name: string
      };


      data = {name: name};
      return this.http.post(this.constants.webServicesUrl + '/Roles/RoleCreate', data);
  }

      updateRole(id: number, name: string) {
          let data: {
              id: number,
              name: string
          };
          data = {id: id, name: name};
          return this.http.post(this.constants.webServicesUrl + '/Roles/RoleUpdate', data);
      }

      deleteRole(id: number) {
          let data: {
              id: number,
              name: string
          };
          data = { id: id, name: ''};
          return this.http.post(this.constants.webServicesUrl + '/Roles/RoleDelete', data);
      }

      fetchRoles() {
          // console.log( 'fetching Role...');

          return this.http.get<Role[]>(this.constants.webServicesUrl + '/Roles');
      }

      getRole(id: number) {
          let data: {
            id: number,
            name: string,
          };

          data = { id: id, name: ''};
          return this.http.post<Role[]>(this.constants.webServicesUrl + '/Roles/RoleByParam', data);
        }
}
