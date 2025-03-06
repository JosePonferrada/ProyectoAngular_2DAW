import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PilotoDetailComponent } from './piloto-detail.component';

describe('PilotoDetailComponent', () => {
  let component: PilotoDetailComponent;
  let fixture: ComponentFixture<PilotoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PilotoDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PilotoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
