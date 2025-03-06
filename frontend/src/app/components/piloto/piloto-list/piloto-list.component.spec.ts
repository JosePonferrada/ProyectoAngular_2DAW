import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotoListComponent } from './piloto-list.component';

describe('PilotoListComponent', () => {
  let component: PilotoListComponent;
  let fixture: ComponentFixture<PilotoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PilotoListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
