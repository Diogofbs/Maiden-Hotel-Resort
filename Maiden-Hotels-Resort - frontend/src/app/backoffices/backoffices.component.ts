import { Component, OnInit, OnDestroy } from '@angular/core';
import { BackofficesService } from './services/backoffices.service';
import { Backoffice } from './models/backoffice.model';
import { RolesService } from './services/roles.service';
import { Role } from './models/roles.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppComponent } from '../app.component';
import { Guest } from '../guests/guests.model';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-backoffices',
  templateUrl: './backoffices.component.html',
  styleUrls: ['./backoffices.component.css']
})
export class BackofficesComponent implements OnInit, OnDestroy {

    public insertForm: FormGroup;
    public roleForm: FormGroup;

    public isFetching = false;
    public error = '';
    public success = '';

    public backoffices: Backoffice[];
    public roles: Role[];

    public guest: Guest;
    public employer: Backoffice;

    private subscrition: Subscription;

    constructor(private app: AppComponent, private backofficesService: BackofficesService, private rolesServices: RolesService) {

        // this.guest = this.app.guest;
        this.employer = this.app.employer;
     }

    ngOnInit() {

        this.OnRefresh();

        this.insertForm = new FormGroup({
            name : new FormControl(null, [Validators.required, Validators.pattern("^[A-ZÀ-Ù][a-zà-ú]*$")]),
            username : new FormControl(null, Validators.required),
            password : new FormControl(null, Validators.required),
            idRole : new FormControl(null, Validators.required)
          });

        this.roleForm = new FormGroup({
            Rolename : new FormControl(null, [Validators.required, Validators.pattern("^[A-ZÀ-Ù][a-zà-ú]*$")])
        });
    }

    ngOnDestroy() {
        this.subscrition.unsubscribe();
    }

    OnRefresh() {
        /*this.guest = this.app.guest;
        this.employer = this.app.employer;*/
        this.fetchBackoffices();
        this.fetchRoles();
    }


  private fetchBackoffices() {
    this.isFetching = true;
    // console.log('Fetching Backoffices...');

    this.backoffices = [];

    this.subscrition = this.backofficesService.fetchBackoffices().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.backoffices = [];
        for (const employer of data) {
          this.backoffices.push(new Backoffice(employer.id, employer.name, employer.username, employer.password, employer.idRole));
        }
      },
      error => {
          this.error = error.message;

      });
  }

  private fetchRoles() {
    this.isFetching = true;
    // console.log('Fetching Roles...');

    this.roles = [];
    // this.roles.push(new Role(1, 'admin'));
    // console.log(this.roles);
    this.subscrition = this.rolesServices.fetchRoles().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.roles = [];
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

  private onRefresh() {

    // console.log('refresh');
    this.fetchBackoffices();
    this.fetchRoles();

  }

  private DeleteBackoffice(id: number) {

    this.subscrition = this.backofficesService.deleteBackoffice(id).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'Backoffice Deleted!';
        this.error = '';
        this.fetchBackoffices();

    },
    error => {
        this.error = error.message;
        this.success = '';
    });
  }

  createBackoffice() {
    // console.log('onCreateUser');
    // console.log(this.insertForm);
    // send http request
    this.subscrition = this.backofficesService.createAndStoreBackoffice(
      this.insertForm.value.name,
      this.insertForm.value.username,
      this.insertForm.value.idRole,
      this.insertForm.value.password
      ).subscribe(responseData => {
        // console.log(responseData);
        this.success = 'New User Inserted.';
        this.error = '';
        this.fetchBackoffices();
    },
    error => {
        this.error = error.message;
        this.success = '';
    });
  }

    createRole() {
        this.subscrition =  this.rolesServices.createAndStoreRole(this.roleForm.value.Rolename).subscribe(
          data => {
            this.success = 'New Role Inserted.';
            this.error = '';
            this.fetchRoles();

          }, error => {
            this.error = error.message;
            this.success = '';
        });
    }

}
