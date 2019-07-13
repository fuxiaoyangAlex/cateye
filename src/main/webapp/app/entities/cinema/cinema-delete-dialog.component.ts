import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICinema } from 'app/shared/model/cinema.model';
import { CinemaService } from './cinema.service';

@Component({
  selector: 'jhi-cinema-delete-dialog',
  templateUrl: './cinema-delete-dialog.component.html'
})
export class CinemaDeleteDialogComponent {
  cinema: ICinema;

  constructor(protected cinemaService: CinemaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cinemaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cinemaListModification',
        content: 'Deleted an cinema'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cinema-delete-popup',
  template: ''
})
export class CinemaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cinema }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CinemaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cinema = cinema;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cinema', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cinema', { outlets: { popup: null } }]);
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
