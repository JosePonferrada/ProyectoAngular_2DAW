import { TestBed } from '@angular/core/testing';

import { EscuderiaService } from './escuderia.service';

describe('EscuderiaService', () => {
  let service: EscuderiaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EscuderiaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
