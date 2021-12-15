import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApoliceNumeroFormComponent } from './apolice-numero-form.component';

describe('ApoliceNumeroFormComponent', () => {
  let component: ApoliceNumeroFormComponent;
  let fixture: ComponentFixture<ApoliceNumeroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApoliceNumeroFormComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApoliceNumeroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
