import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ApoliceComponent } from '../list/apolice.component';
import { ApoliceDetailComponent } from '../detail/apolice-detail.component';
import { ApoliceUpdateComponent } from '../update/apolice-update.component';
import { ApoliceRoutingResolveService } from './apolice-routing-resolve.service';
import { ApoliceNumeroFormComponent } from '../numero/apolice-numero-form/apolice-numero-form.component';
import { ApoliceNumeroViewComponent } from '../numero/apolice-numero-view/apolice-numero-view.component';

const apoliceRoute: Routes = [
  {
    path: '',
    component: ApoliceComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApoliceDetailComponent,
    resolve: {
      apolice: ApoliceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApoliceUpdateComponent,
    resolve: {
      apolice: ApoliceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApoliceUpdateComponent,
    resolve: {
      apolice: ApoliceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'numero',
    component: ApoliceNumeroFormComponent,
    resolve: {
      apolice: ApoliceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'numero/:numero',
    component: ApoliceNumeroViewComponent,
    resolve: {
      apolice: ApoliceRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(apoliceRoute)],
  exports: [RouterModule],
})
export class ApoliceRoutingModule {}
