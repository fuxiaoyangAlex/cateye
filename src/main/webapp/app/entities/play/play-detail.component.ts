import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlay } from 'app/shared/model/play.model';

@Component({
  selector: 'jhi-play-detail',
  templateUrl: './play-detail.component.html'
})
export class PlayDetailComponent implements OnInit {
  play: IPlay;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ play }) => {
      this.play = play;
    });
  }

  previousState() {
    window.history.back();
  }
}
