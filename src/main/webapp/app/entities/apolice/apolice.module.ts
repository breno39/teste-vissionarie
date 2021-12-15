import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ApoliceComponent } from './list/apolice.component';
import { ApoliceDetailComponent } from './detail/apolice-detail.component';
import { ApoliceUpdateComponent } from './update/apolice-update.component';
import { ApoliceDeleteDialogComponent } from './delete/apolice-delete-dialog.component';
import { ApoliceRoutingModule } from './route/apolice-routing.module';
import { ApoliceNumeroFormComponent } from './numero/apolice-numero-form/apolice-numero-form.component';
import { ApoliceNumeroViewComponent } from './numero/apolice-numero-view/apolice-numero-view.component';

@NgModule({
  imports: [SharedModule, ApoliceRoutingModule],
  declarations: [
    ApoliceComponent,
    ApoliceDetailComponent,
    ApoliceUpdateComponent,
    ApoliceDeleteDialogComponent,
    ApoliceNumeroFormComponent,
    ApoliceNumeroViewComponent,
  ],
  entryComponents: [ApoliceDeleteDialogComponent],
})
export class ApoliceModule {}
