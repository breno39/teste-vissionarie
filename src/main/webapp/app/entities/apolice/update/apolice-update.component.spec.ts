jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ApoliceService } from '../service/apolice.service';
import { IApolice, Apolice } from '../apolice.model';

import { ApoliceUpdateComponent } from './apolice-update.component';

describe('Apolice Management Update Component', () => {
  let comp: ApoliceUpdateComponent;
  let fixture: ComponentFixture<ApoliceUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let apoliceService: ApoliceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ApoliceUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(ApoliceUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApoliceUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    apoliceService = TestBed.inject(ApoliceService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const apolice: IApolice = { id: 'CBA' };

      activatedRoute.data = of({ apolice });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(apolice));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apolice>>();
      const apolice = { id: 'ABC' };
      jest.spyOn(apoliceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apolice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apolice }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(apoliceService.update).toHaveBeenCalledWith(apolice);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apolice>>();
      const apolice = new Apolice();
      jest.spyOn(apoliceService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apolice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: apolice }));
      saveSubject.complete();

      // THEN
      expect(apoliceService.create).toHaveBeenCalledWith(apolice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Apolice>>();
      const apolice = { id: 'ABC' };
      jest.spyOn(apoliceService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ apolice });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(apoliceService.update).toHaveBeenCalledWith(apolice);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
