import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IApolice, Apolice } from '../apolice.model';

import { ApoliceService } from './apolice.service';

describe('Apolice Service', () => {
  let service: ApoliceService;
  let httpMock: HttpTestingController;
  let elemDefault: IApolice;
  let expectedResult: IApolice | IApolice[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApoliceService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 'AAAAAAA',
      numero: 0,
      inicio: currentDate,
      fim: currentDate,
      placaVeiculo: 'AAAAAAA',
      valor: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          inicio: currentDate.format(DATE_FORMAT),
          fim: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Apolice', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          inicio: currentDate.format(DATE_FORMAT),
          fim: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inicio: currentDate,
          fim: currentDate,
        },
        returnedFromService
      );

      service.create(new Apolice()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Apolice', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          numero: 1,
          inicio: currentDate.format(DATE_FORMAT),
          fim: currentDate.format(DATE_FORMAT),
          placaVeiculo: 'BBBBBB',
          valor: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inicio: currentDate,
          fim: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Apolice', () => {
      const patchObject = Object.assign(
        {
          numero: 1,
          inicio: currentDate.format(DATE_FORMAT),
          fim: currentDate.format(DATE_FORMAT),
          valor: 1,
        },
        new Apolice()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          inicio: currentDate,
          fim: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Apolice', () => {
      const returnedFromService = Object.assign(
        {
          id: 'BBBBBB',
          numero: 1,
          inicio: currentDate.format(DATE_FORMAT),
          fim: currentDate.format(DATE_FORMAT),
          placaVeiculo: 'BBBBBB',
          valor: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inicio: currentDate,
          fim: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Apolice', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addApoliceToCollectionIfMissing', () => {
      it('should add a Apolice to an empty array', () => {
        const apolice: IApolice = { id: 'ABC' };
        expectedResult = service.addApoliceToCollectionIfMissing([], apolice);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apolice);
      });

      it('should not add a Apolice to an array that contains it', () => {
        const apolice: IApolice = { id: 'ABC' };
        const apoliceCollection: IApolice[] = [
          {
            ...apolice,
          },
          { id: 'CBA' },
        ];
        expectedResult = service.addApoliceToCollectionIfMissing(apoliceCollection, apolice);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Apolice to an array that doesn't contain it", () => {
        const apolice: IApolice = { id: 'ABC' };
        const apoliceCollection: IApolice[] = [{ id: 'CBA' }];
        expectedResult = service.addApoliceToCollectionIfMissing(apoliceCollection, apolice);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apolice);
      });

      it('should add only unique Apolice to an array', () => {
        const apoliceArray: IApolice[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'e7e32401-28cb-4f12-bee1-31c1ece7cbd9' }];
        const apoliceCollection: IApolice[] = [{ id: 'ABC' }];
        expectedResult = service.addApoliceToCollectionIfMissing(apoliceCollection, ...apoliceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const apolice: IApolice = { id: 'ABC' };
        const apolice2: IApolice = { id: 'CBA' };
        expectedResult = service.addApoliceToCollectionIfMissing([], apolice, apolice2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(apolice);
        expect(expectedResult).toContain(apolice2);
      });

      it('should accept null and undefined values', () => {
        const apolice: IApolice = { id: 'ABC' };
        expectedResult = service.addApoliceToCollectionIfMissing([], null, apolice, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(apolice);
      });

      it('should return initial array if no Apolice is added', () => {
        const apoliceCollection: IApolice[] = [{ id: 'ABC' }];
        expectedResult = service.addApoliceToCollectionIfMissing(apoliceCollection, undefined, null);
        expect(expectedResult).toEqual(apoliceCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
