import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApoliceNumeroViewComponent } from './apolice-numero-view.component';

describe('ApoliceNumeroViewComponent', () => {
  let component: ApoliceNumeroViewComponent;
  let fixture: ComponentFixture<ApoliceNumeroViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApoliceNumeroViewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApoliceNumeroViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
