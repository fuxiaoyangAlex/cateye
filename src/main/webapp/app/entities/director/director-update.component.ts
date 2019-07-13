import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDirector, Director } from 'app/shared/model/director.model';
import { DirectorService } from './director.service';

@Component({
  selector: 'jhi-director-update',
  templateUrl: './director-update.component.html'
})
export class DirectorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    gender: [],
    age: []
  });

  constructor(protected directorService: DirectorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ director }) => {
      this.updateForm(director);
    });
  }

  updateForm(director: IDirector) {
    this.editForm.patchValue({
      id: director.id,
      name: director.name,
      gender: director.gender,
      age: director.age
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const director = this.createFromForm();
    if (director.id !== undefined) {
      this.subscribeToSaveResponse(this.directorService.update(director));
    } else {
      this.subscribeToSaveResponse(this.directorService.create(director));
    }
  }

  private createFromForm(): IDirector {
    return {
      ...new Director(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      gender: this.editForm.get(['gender']).value,
      age: this.editForm.get(['age']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirector>>) {
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
