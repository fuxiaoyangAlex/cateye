import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollect } from 'app/shared/model/collect.model';

@Component({
  selector: 'jhi-collect-detail',
  templateUrl: './collect-detail.component.html'
})
export class CollectDetailComponent implements OnInit {
  collect: ICollect;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ collect }) => {
      this.collect = collect;
    });
  }

  previousState() {
    window.history.back();
  }
}
