import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscuderiaListComponent } from './escuderia-list.component';

describe('EscuderiaListComponent', () => {
  let component: EscuderiaListComponent;
  let fixture: ComponentFixture<EscuderiaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscuderiaListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EscuderiaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
