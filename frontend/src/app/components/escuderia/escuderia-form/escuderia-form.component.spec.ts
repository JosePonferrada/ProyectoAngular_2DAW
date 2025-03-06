import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscuderiaFormComponent } from './escuderia-form.component';

describe('EscuderiaFormComponent', () => {
  let component: EscuderiaFormComponent;
  let fixture: ComponentFixture<EscuderiaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscuderiaFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EscuderiaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
