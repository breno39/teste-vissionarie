import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IApolice } from '../../apolice.model';

@Component({
  selector: 'jhi-apolice-numero-view',
  templateUrl: './apolice-numero-view.component.html',
  styleUrls: ['./apolice-numero-view.component.scss'],
})
export class ApoliceNumeroViewComponent implements OnInit {
  apolice: IApolice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apolice }) => {
      this.apolice = apolice;
    });
  }

  calculateRemainigDays(): string | void {
    if (this.apolice?.fim?.isAfter(Date())) {
      return `A apolice vencera em aproximadamente ${this.apolice.fim.fromNow(true)}`;
    }

    if (this.apolice?.fim?.isBefore(Date())) {
      return `A apolice venceu a aproximadamente ${this.apolice.fim.fromNow(true)}`;
    }
  }

  previousState(): void {
    window.history.back();
  }
}
