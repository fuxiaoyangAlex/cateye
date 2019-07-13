import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariety } from 'app/shared/model/variety.model';
import { VarietyService } from './variety.service';

@Component({
  selector: 'jhi-variety-delete-dialog',
  templateUrl: './variety-delete-dialog.component.html'
})
export class VarietyDeleteDialogComponent {
  variety: IVariety;

  constructor(protected varietyService: VarietyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.varietyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'varietyListModification',
        content: 'Deleted an variety'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-variety-delete-popup',
  template: ''
})
export class VarietyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ variety }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VarietyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.variety = variety;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/variety', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/variety', { outlets: { popup: null } }]);
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
