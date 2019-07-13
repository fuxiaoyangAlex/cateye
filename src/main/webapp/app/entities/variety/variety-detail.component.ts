import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariety } from 'app/shared/model/variety.model';

@Component({
  selector: 'jhi-variety-detail',
  templateUrl: './variety-detail.component.html'
})
export class VarietyDetailComponent implements OnInit {
  variety: IVariety;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ variety }) => {
      this.variety = variety;
    });
  }

  previousState() {
    window.history.back();
  }
}
