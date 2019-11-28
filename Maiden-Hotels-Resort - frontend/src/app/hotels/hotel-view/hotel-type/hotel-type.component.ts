import { Component, OnInit, OnDestroy } from '@angular/core';
import { Hotel } from '../../models/hotels.model';
import { HotelsService } from '../../services/hotels.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-hotel-type',
  templateUrl: './hotel-type.component.html',
  styleUrls: ['./hotel-type.component.css']
})
export class HotelTypeComponent implements OnInit, OnDestroy {

  public isFetching = false;
  public error = '';
  public success = '';

  public hotels: Hotel[] = [];
  public fetchedHotels: boolean;

  public type: string;

  private subscription: Subscription;

  constructor(private hotelsService: HotelsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.params
    .subscribe(
      (params: Params) => {
          this.type = params.type;
          this.fetchHotels(this.type);
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  private fetchHotels(type: string) {
    this.isFetching = true;
    // console.log('Fetching hotels...' + type);
    this.subscription = this.hotelsService.fetchHotels().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.hotels = [];
        for (let i = 0, len = data.length; i < len; i++) {
          if (type === 'all') {
            this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location,
                data[i].classification, data[i].type, data[i].imagePath));
          } else {
            if (data[i].type === type) {
              this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location,
                data[i].classification, data[i].type, data[i].imagePath));
            }
          }
        }
        this.error = '';
        this.fetchedHotels = true;
      },
      error => {
          this.error = error.message;

      });
  }

  goToHotel(id: number) {
    this.router.navigate(['../', 'hotel', id], {relativeTo: this.route});
  }

}
