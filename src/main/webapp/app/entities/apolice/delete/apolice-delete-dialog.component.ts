import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IApolice } from '../apolice.model';
import { ApoliceService } from '../service/apolice.service';

@Component({
  templateUrl: './apolice-delete-dialog.component.html',
})
export class ApoliceDeleteDialogComponent {
  apolice?: IApolice;

  constructor(protected apoliceService: ApoliceService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.apoliceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
