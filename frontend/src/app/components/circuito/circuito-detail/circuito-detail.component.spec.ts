import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CircuitoDetailComponent } from './circuito-detail.component';

describe('CircuitoDetailComponent', () => {
  let component: CircuitoDetailComponent;
  let fixture: ComponentFixture<CircuitoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CircuitoDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CircuitoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
