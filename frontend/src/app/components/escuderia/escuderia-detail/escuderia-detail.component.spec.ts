import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscuderiaDetailComponent } from './escuderia-detail.component';

describe('EscuderiaDetailComponent', () => {
  let component: EscuderiaDetailComponent;
  let fixture: ComponentFixture<EscuderiaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscuderiaDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EscuderiaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
