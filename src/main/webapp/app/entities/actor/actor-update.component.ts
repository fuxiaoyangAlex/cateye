import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IActor, Actor } from 'app/shared/model/actor.model';
import { ActorService } from './actor.service';

@Component({
  selector: 'jhi-actor-update',
  templateUrl: './actor-update.component.html'
})
export class ActorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    gender: [],
    age: [],
    nationality: []
  });

  constructor(protected actorService: ActorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ actor }) => {
      this.updateForm(actor);
    });
  }

  updateForm(actor: IActor) {
    this.editForm.patchValue({
      id: actor.id,
      name: actor.name,
      gender: actor.gender,
      age: actor.age,
      nationality: actor.nationality
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const actor = this.createFromForm();
    if (actor.id !== undefined) {
      this.subscribeToSaveResponse(this.actorService.update(actor));
    } else {
      this.subscribeToSaveResponse(this.actorService.create(actor));
    }
  }

  private createFromForm(): IActor {
    return {
      ...new Actor(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      gender: this.editForm.get(['gender']).value,
      age: this.editForm.get(['age']).value,
      nationality: this.editForm.get(['nationality']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActor>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
