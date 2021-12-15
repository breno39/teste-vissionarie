jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IApolice, Apolice } from '../apolice.model';
import { ApoliceService } from '../service/apolice.service';

import { ApoliceRoutingResolveService } from './apolice-routing-resolve.service';

describe('Apolice routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ApoliceRoutingResolveService;
  let service: ApoliceService;
  let resultApolice: IApolice | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(ApoliceRoutingResolveService);
    service = TestBed.inject(ApoliceService);
    resultApolice = undefined;
  });

  describe('resolve', () => {
    it('should return IApolice returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApolice = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultApolice).toEqual({ id: 'ABC' });
    });

    it('should return new IApolice if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApolice = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultApolice).toEqual(new Apolice());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Apolice })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultApolice = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultApolice).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
