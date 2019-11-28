
import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import { HotelsComponent } from './hotels/hotels.component';
import { RoomsComponent } from './rooms/rooms.component';
import { ServicesComponent } from './services/services.component';
import { GuestsComponent } from './guests/guests.component';
import {HomeComponent} from './home/home.component';
import {ClientsComponent} from './clients/clients.component';
import {LoginClientComponent} from './login-client/login-client.component';
import {BookingComponent} from './booking/booking.component';
import { HotelViewComponent } from './hotels/hotel-view/hotel-view.component';
import { HotelTypeComponent } from './hotels/hotel-view/hotel-type/hotel-type.component';
import { HotelItemComponent } from './hotels/hotel-view/hotel-item/hotel-item.component';
import { HotelSearchComponent } from './hotels/hotel-view/hotel-search/hotel-search.component';
import { BookingCreateComponent } from './booking/booking-create/booking-create.component';
import { BackofficesComponent } from './backoffices/backoffices.component';
import { LoginBackofficeComponent } from './login-backoffice/login-backoffice.component';
import { BookingInfoComponent } from './booking/booking-info/booking-info.component';
import { ManageComponent } from './booking/manage/manage.component';
import { ChargeServicesComponent } from './services/charge-services/charge-services.component';
import { BookingClientComponent } from './booking/booking-client/booking-client.component';
import { GuestClientInfoComponent } from './guests/guest-client-info/guest-client-info.component';

const appRoutes: Routes = [
    {path: '', redirectTo: '/home', pathMatch : 'full'},
    {path: 'home', component: HomeComponent },
    {path: 'login-client', component: LoginClientComponent },
    {path: 'login-backoffice', component: LoginBackofficeComponent },
    {path: 'booking', component: BookingComponent, children: [
      {path: '', redirectTo: '/hotels/all', pathMatch: 'full' },
      {path: ':idBooking/info', component: BookingInfoComponent},
      {path: ':idBooking/charge-services', component: ChargeServicesComponent},
      {path: ':dateIn/:dateOut/:idHotel/:roomid', component: BookingCreateComponent}

    ] },
    {path: 'manage-clients', component: ClientsComponent},
    {path: 'manage-guests', component: GuestsComponent },
    {path: 'manage-hotels', component: HotelsComponent },
    {path: 'manage-rooms', component: RoomsComponent },
    {path: 'manage-services', component: ServicesComponent },
    {path: 'hotels', component: HotelViewComponent, children: [
      {path: '', redirectTo: 'all', pathMatch: 'full' },
      {path: 'search', component: HotelSearchComponent },
      {path: ':type', component: HotelTypeComponent },
      {path: 'hotel/:id', component: HotelItemComponent }
    ] },
    {path: 'manage-backoffices', component: BackofficesComponent},
    {path: 'manage-bookings', component: ManageComponent},
    {path: 'charge-services', component: ChargeServicesComponent},
    {path: 'Client/BookingSearch', component: BookingClientComponent},
    {path: 'Client/:clientName', component: GuestClientInfoComponent}

];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}
