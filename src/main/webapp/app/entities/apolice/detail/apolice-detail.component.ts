import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApolice } from '../apolice.model';

@Component({
  selector: 'jhi-apolice-detail',
  templateUrl: './apolice-detail.component.html',
})
export class ApoliceDetailComponent implements OnInit {
  apolice: IApolice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apolice }) => {
      this.apolice = apolice;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
