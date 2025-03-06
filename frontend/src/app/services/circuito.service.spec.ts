import { TestBed } from '@angular/core/testing';

import { CircuitoService } from './circuito.service';

describe('CircuitoService', () => {
  let service: CircuitoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CircuitoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
