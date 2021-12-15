import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IApolice, Apolice } from '../apolice.model';
import { ApoliceService } from '../service/apolice.service';
import { CustomValidators } from 'app/shared/validators/CustomValidators';

@Component({
  selector: 'jhi-apolice-update',
  templateUrl: './apolice-update.component.html',
})
export class ApoliceUpdateComponent implements OnInit {
  isSaving = false;
  apoliceNumero = 0;

  editForm = this.fb.group(
    {
      id: [],
      numero: [],
      inicio: [null, [Validators.required]],
      fim: [null, [Validators.required]],
      placaVeiculo: [null, [Validators.required]],
      valor: [null, [Validators.required]],
    },
    { validator: CustomValidators.dateValidator() }
  );

  constructor(protected apoliceService: ApoliceService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apolice }) => {
      this.apoliceNumero = apolice.numero;
      this.updateForm(apolice);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apolice = this.createFromForm();
    if (apolice.id !== undefined) {
      this.subscribeToSaveResponse(this.apoliceService.update(apolice));
    } else {
      this.subscribeToSaveResponse(this.apoliceService.create(apolice));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApolice>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(apolice: IApolice): void {
    this.editForm.patchValue({
      id: apolice.id,
      numero: apolice.numero,
      inicio: apolice.inicio,
      fim: apolice.fim,
      placaVeiculo: apolice.placaVeiculo,
      valor: apolice.valor,
    });
  }

  protected createFromForm(): IApolice {
    return {
      ...new Apolice(),
      id: this.editForm.get(['id'])!.value,
      numero: this.apoliceNumero,
      inicio: this.editForm.get(['inicio'])!.value,
      fim: this.editForm.get(['fim'])!.value,
      placaVeiculo: this.editForm.get(['placaVeiculo'])!.value,
      valor: this.editForm.get(['valor'])!.value,
    };
  }
}
