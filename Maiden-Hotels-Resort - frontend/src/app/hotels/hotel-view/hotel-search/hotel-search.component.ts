import { Component, OnInit, OnDestroy } from '@angular/core';
import { HotelsService } from '../../services/hotels.service';
import { Hotel } from '../../models/hotels.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-hotel-search',
  templateUrl: './hotel-search.component.html',
  styleUrls: ['./hotel-search.component.css']
})
export class HotelSearchComponent implements OnInit, OnDestroy {

  public isFetching = false;
  public error = '';
  public success = '';

  public hotels: Hotel[] = [];
  public fetchedHotels: boolean;

  public formTrue = false;
  public location = '';
  public type = 'All';

  public locationInput = '';
  public typeInput = 'All';

  private subscription: Subscription;


  constructor(private hotelsService: HotelsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.fetchHotels();

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  private fetchHotels() {
    this.isFetching = true;
    // console.log('Fetching hotels...');
    this.subscription = this.hotelsService.fetchHotels().subscribe(data => {
        this.isFetching = false;
        // console.log(data);
        this.hotels = [];
        for (let i = 0, len = data.length; i < len; i++) {
          this.hotels.push(new Hotel(data[i].id, data[i].name, data[i].location, data[i].classification, data[i].type, data[i].imagePath));
          this.type = 'All';
          this.location = '';
        }
        this.error = '';
        this.fetchedHotels = true;
      },
      error => {
          this.error = error.message;

      });
  }


  changePage(id: number) {
    this.router.navigate(['../', 'hotel', id], {relativeTo: this.route});
  }

}




