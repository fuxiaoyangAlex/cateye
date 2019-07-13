import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRound } from 'app/shared/model/round.model';

@Component({
  selector: 'jhi-round-detail',
  templateUrl: './round-detail.component.html'
})
export class RoundDetailComponent implements OnInit {
  round: IRound;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ round }) => {
      this.round = round;
    });
  }

  previousState() {
    window.history.back();
  }
}
