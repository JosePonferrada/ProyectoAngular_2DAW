import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotoFormComponent } from './piloto-form.component';

describe('PilotoFormComponent', () => {
  let component: PilotoFormComponent;
  let fixture: ComponentFixture<PilotoFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PilotoFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotoFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
