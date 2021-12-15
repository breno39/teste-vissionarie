import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApolice } from '../apolice.model';
import { ApoliceService } from '../service/apolice.service';
import { ApoliceDeleteDialogComponent } from '../delete/apolice-delete-dialog.component';

@Component({
  selector: 'jhi-apolice',
  templateUrl: './apolice.component.html',
})
export class ApoliceComponent implements OnInit {
  apolices?: IApolice[];
  isLoading = false;

  constructor(protected apoliceService: ApoliceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.apoliceService.query().subscribe(
      (res: HttpResponse<IApolice[]>) => {
        this.isLoading = false;
        this.apolices = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IApolice): string {
    return item.id!;
  }

  delete(apolice: IApolice): void {
    const modalRef = this.modalService.open(ApoliceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.apolice = apolice;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
