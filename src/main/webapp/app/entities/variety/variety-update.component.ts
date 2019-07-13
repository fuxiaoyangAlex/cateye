import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IVariety, Variety } from 'app/shared/model/variety.model';
import { VarietyService } from './variety.service';

@Component({
  selector: 'jhi-variety-update',
  templateUrl: './variety-update.component.html'
})
export class VarietyUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: []
  });

  constructor(protected varietyService: VarietyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ variety }) => {
      this.updateForm(variety);
    });
  }

  updateForm(variety: IVariety) {
    this.editForm.patchValue({
      id: variety.id,
      label: variety.label
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const variety = this.createFromForm();
    if (variety.id !== undefined) {
      this.subscribeToSaveResponse(this.varietyService.update(variety));
    } else {
      this.subscribeToSaveResponse(this.varietyService.create(variety));
    }
  }

  private createFromForm(): IVariety {
    return {
      ...new Variety(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariety>>) {
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
