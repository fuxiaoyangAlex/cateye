import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollect } from 'app/shared/model/collect.model';
import { CollectService } from './collect.service';

@Component({
  selector: 'jhi-collect-delete-dialog',
  templateUrl: './collect-delete-dialog.component.html'
})
export class CollectDeleteDialogComponent {
  collect: ICollect;

  constructor(protected collectService: CollectService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.collectService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'collectListModification',
        content: 'Deleted an collect'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-collect-delete-popup',
  template: ''
})
export class CollectDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ collect }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CollectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.collect = collect;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/collect', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/collect', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
