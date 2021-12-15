import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApoliceDetailComponent } from './apolice-detail.component';

describe('Apolice Management Detail Component', () => {
  let comp: ApoliceDetailComponent;
  let fixture: ComponentFixture<ApoliceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApoliceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ apolice: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ApoliceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApoliceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load apolice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.apolice).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
