import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ApoliceService } from '../service/apolice.service';

import { ApoliceComponent } from './apolice.component';

describe('Apolice Management Component', () => {
  let comp: ApoliceComponent;
  let fixture: ComponentFixture<ApoliceComponent>;
  let service: ApoliceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ApoliceComponent],
    })
      .overrideTemplate(ApoliceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ApoliceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ApoliceService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.apolices?.[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
  });
});
