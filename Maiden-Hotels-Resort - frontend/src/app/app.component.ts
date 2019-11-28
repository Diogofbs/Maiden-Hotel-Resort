import { Component, OnInit, OnChanges, OnDestroy } from '@angular/core';
import { GuestsService } from './guests/guests.service';
import { Guest } from './guests/guests.model';
import { Backoffice } from './backoffices/models/backoffice.model';
import { BackofficesService } from './backoffices/services/backoffices.service';
import { RolesService } from './backoffices/services/roles.service';
import { Role } from './backoffices/models/roles.model';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {

    public error = '';
    public success = '';
    public guest: Guest;
    public employer: Backoffice;
    public roles: Role[];
    public roleName: string;

    private subscrition: Subscription;

    // public loggedIn = false;
    constructor(private guestsService: GuestsService, private backofficeServices: BackofficesService,
                private rolesServices: RolesService, private router: Router, private route: ActivatedRoute) {}

    ngOnInit() {

        console.log('refreshing');

        this.refreshSideBar();

    }

    ngOnDestroy() {
        if (this.subscrition) {
            this.subscrition.unsubscribe();
        }
    }



    onLogout() {
        console.log('logout..');

        // this.loggedIn = false;
        localStorage.removeItem('name');
        localStorage.removeItem('userId');
        localStorage.removeItem('token');
        localStorage.removeItem('username');

        this.guest = null;
        this.employer = null;
        this.router.navigate(['../', 'home'], {relativeTo: this.route});
    }

    public refreshSideBar() {

        if (localStorage.getItem('username')) {

            if (localStorage.getItem('username').includes('@')) {
                // console.log('client');
                this.fetchClientLog();
            } else {
                // console.log('backoffice');
                this.fetchEmployerLog();
                this.fetchRoles();
            }
        }
    }

    public fetchClientLog() {

        // console.log('Fetching client..' + localStorage.getItem('username'));
        this.guestsService.getGuest(null, localStorage.getItem('username')).subscribe(data => {

            // console.log(data);
            this.guest = new Guest(data[0].id, data[0].firstName, data[0].lastName, data[0].dateOfBirth, data[0]. gender,
                                    data[0].idNumber, data[0].address, data[0].contactNumber, data[0].email, data[0].status);
            this.error = '';

        },
         error => {
          this.error = error.message;

        });
    }

    private fetchEmployerLog() {

        // console.log('Fetching employer..' + localStorage.getItem('username'));
        this.subscrition = this.backofficeServices.getBackoffice(null, localStorage.getItem('username')).subscribe(data => {

            // console.log(data);
            this.employer = new Backoffice(data[0].id, data[0].name, data[0].username, data[0].password, data[0].idRole);
            this.error = '';

        },
         error => {
          this.error = error.message;

        });
    }

    private fetchRoles() {
        // console.log('Fetching Roles...');

        this.roles = [];

        // console.log(this.roles);
        this.subscrition = this.rolesServices.fetchRoles().subscribe(data => {
            // console.log(data);
            for (const role of data) {
              this.roles.push(new Role(role.id, role.name));
            }
          },
          error => {
              this.error = error.message;

          });
      }

    private getRoleByIdRole(idRole: number) {
        return this.roles.find(role => role.id === idRole);
    }



}
