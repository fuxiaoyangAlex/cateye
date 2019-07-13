import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlay } from 'app/shared/model/play.model';
import { PlayService } from './play.service';

@Component({
  selector: 'jhi-play-delete-dialog',
  templateUrl: './play-delete-dialog.component.html'
})
export class PlayDeleteDialogComponent {
  play: IPlay;

  constructor(protected playService: PlayService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.playService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'playListModification',
        content: 'Deleted an play'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-play-delete-popup',
  template: ''
})
export class PlayDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ play }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PlayDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.play = play;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/play', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/play', { outlets: { popup: null } }]);
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
