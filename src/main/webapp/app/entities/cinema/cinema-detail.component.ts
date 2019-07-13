import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICinema } from 'app/shared/model/cinema.model';

@Component({
  selector: 'jhi-cinema-detail',
  templateUrl: './cinema-detail.component.html'
})
export class CinemaDetailComponent implements OnInit {
  cinema: ICinema;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cinema }) => {
      this.cinema = cinema;
    });
  }

  previousState() {
    window.history.back();
  }
}
