import { HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IApolice } from '../../apolice.model';
import { ApoliceService } from '../../service/apolice.service';

@Component({
  selector: 'jhi-apolice-numero-form',
  templateUrl: './apolice-numero-form.component.html',
  styleUrls: ['./apolice-numero-form.component.scss'],
})
export class ApoliceNumeroFormComponent {
  isSaving = false;
  numero = '';
  editForm = this.fb.group({
    Numero: [null, [Validators.required]],
  });

  constructor(protected apoliceService: ApoliceService, protected fb: FormBuilder, private router: Router) {}

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.numero = this.createFromForm();
    this.subscribeToResponse(this.apoliceService.findByNumero(this.numero));
  }

  protected subscribeToResponse(result: Observable<HttpResponse<IApolice>>): void {
    result.pipe(finalize(() => this.onFinalize())).subscribe(() => this.onSuccess(this.numero));
  }

  protected onSuccess(numero: string): void {
    this.router.navigate([`/apolice/numero/${numero}`]);
  }

  protected onError(): void {
    // Api for inheritance.
  }

  protected onFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(apolice: IApolice): void {
    this.editForm.patchValue({
      id: apolice.id,
      inicio: apolice.inicio,
      fim: apolice.fim,
      placaVeiculo: apolice.placaVeiculo,
      valor: apolice.valor,
    });
  }

  protected createFromForm(): string {
    return this.editForm.get(['Numero'])!.value as string;
  }
}
