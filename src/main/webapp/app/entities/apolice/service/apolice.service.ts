import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IApolice, getApoliceIdentifier } from '../apolice.model';

export type EntityResponseType = HttpResponse<IApolice>;
export type EntityArrayResponseType = HttpResponse<IApolice[]>;

@Injectable({ providedIn: 'root' })
export class ApoliceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/apolices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(apolice: IApolice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apolice);
    return this.http
      .post<IApolice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(apolice: IApolice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apolice);
    return this.http
      .put<IApolice>(`${this.resourceUrl}/${getApoliceIdentifier(apolice) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(apolice: IApolice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apolice);
    return this.http
      .patch<IApolice>(`${this.resourceUrl}/${getApoliceIdentifier(apolice) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IApolice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApolice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByNumero(numero: string): Observable<HttpResponse<IApolice>> {
    return this.http
      .get<IApolice>(`${this.resourceUrl}/numero/${numero}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  addApoliceToCollectionIfMissing(apoliceCollection: IApolice[], ...apolicesToCheck: (IApolice | null | undefined)[]): IApolice[] {
    const apolices: IApolice[] = apolicesToCheck.filter(isPresent);
    if (apolices.length > 0) {
      const apoliceCollectionIdentifiers = apoliceCollection.map(apoliceItem => getApoliceIdentifier(apoliceItem)!);
      const apolicesToAdd = apolices.filter(apoliceItem => {
        const apoliceIdentifier = getApoliceIdentifier(apoliceItem);
        if (apoliceIdentifier == null || apoliceCollectionIdentifiers.includes(apoliceIdentifier)) {
          return false;
        }
        apoliceCollectionIdentifiers.push(apoliceIdentifier);
        return true;
      });
      return [...apolicesToAdd, ...apoliceCollection];
    }
    return apoliceCollection;
  }

  protected convertDateFromClient(apolice: IApolice): IApolice {
    return Object.assign({}, apolice, {
      inicio: apolice.inicio?.isValid() ? apolice.inicio.format(DATE_FORMAT) : undefined,
      fim: apolice.fim?.isValid() ? apolice.fim.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio ? dayjs(res.body.inicio) : undefined;
      res.body.fim = res.body.fim ? dayjs(res.body.fim) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((apolice: IApolice) => {
        apolice.inicio = apolice.inicio ? dayjs(apolice.inicio) : undefined;
        apolice.fim = apolice.fim ? dayjs(apolice.fim) : undefined;
      });
    }
    return res;
  }
}
