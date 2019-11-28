import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HotelsComponent } from './hotels/hotels.component';
import { ConstantsService } from './common/services/constants.service';
import { ServicesComponent } from './services/services.component';
import { RoomsComponent } from './rooms/rooms.component';
import { GuestsComponent } from './guests/guests.component';
import { DatePipe } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { ClientsComponent } from './clients/clients.component';
import { LoginClientComponent } from './login-client/login-client.component';
import { BookingComponent } from './booking/booking.component';
import {AgmCoreModule, GoogleMapsAPIWrapper} from '@agm/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HotelViewComponent } from './hotels/hotel-view/hotel-view.component';
import { HotelTypeComponent } from './hotels/hotel-view/hotel-type/hotel-type.component';
import { HotelSearchComponent } from './hotels/hotel-view/hotel-search/hotel-search.component';
import { HotelItemComponent } from './hotels/hotel-view/hotel-item/hotel-item.component';
import { FilterHotelsPipe } from './hotels/hotel-view/hotel-search/filterHotels.pipe';
import { BookingCreateComponent } from './booking/booking-create/booking-create.component';
import { FilterRoomsHotelPipe } from './hotels/hotel-view/hotel-item/rooms-hotel/filterRoomsHotel.pipe';
import { RoomsHotelComponent } from './hotels/hotel-view/hotel-item/rooms-hotel/rooms-hotel.component';
import { ManageComponent } from './booking/manage/manage.component';
import { BackofficesComponent } from './backoffices/backoffices.component';
import { BasicAuthHtppInterceptorService } from './common/services/httpinterceptor.service';
import { LoginBackofficeComponent } from './login-backoffice/login-backoffice.component';
import { BookingInfoComponent } from './booking/booking-info/booking-info.component';
import { ChargeServicesComponent } from './services/charge-services/charge-services.component';
import { BookingClientComponent } from './booking/booking-client/booking-client.component';
import { GuestClientInfoComponent } from './guests/guest-client-info/guest-client-info.component';
import { FilterBookingsPipe } from './booking/manage/filterBookings.pipe';



@NgModule({
  declarations: [
    AppComponent,
    HotelsComponent,
    ServicesComponent,
    RoomsComponent,
    GuestsComponent,
    HomeComponent,
    FooterComponent,
    ClientsComponent,
    LoginClientComponent,
    BookingComponent,
    HotelViewComponent,
    HotelTypeComponent,
    HotelSearchComponent,
    HotelItemComponent,
    FilterHotelsPipe,
    BookingCreateComponent,
    FilterRoomsHotelPipe,
    RoomsHotelComponent,
    ManageComponent,
    BackofficesComponent,
    LoginBackofficeComponent,
    BookingInfoComponent,
    ChargeServicesComponent,
    BookingClientComponent,
    GuestClientInfoComponent,
    FilterBookingsPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule,
    AgmCoreModule.forRoot({apiKey:'AIzaSyCYXcUfC6UfsTy9ZVxp7mWMklLb9_saYqM'})
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService , multi: true }, ConstantsService, DatePipe, GoogleMapsAPIWrapper],
  bootstrap: [AppComponent]
})
export class AppModule { }
