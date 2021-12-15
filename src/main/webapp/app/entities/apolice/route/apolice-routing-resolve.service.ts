import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApolice, Apolice } from '../apolice.model';
import { ApoliceService } from '../service/apolice.service';

@Injectable({ providedIn: 'root' })
export class ApoliceRoutingResolveService implements Resolve<IApolice> {
  constructor(protected service: ApoliceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApolice> | Observable<never> {
    const id = route.params['id'];
    const numero = route.params['numero'];

    if (id) {
      return this.service.find(id).pipe(
        mergeMap((apolice: HttpResponse<Apolice>) => {
          if (apolice.body) {
            return of(apolice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }

    if (numero) {
      return this.service.findByNumero(numero).pipe(
        mergeMap((apolice: HttpResponse<Apolice>) => {
          if (apolice.body) {
            return of(apolice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }

    return of(new Apolice());
  }
}
