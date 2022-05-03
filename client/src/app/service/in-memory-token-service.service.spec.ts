import { TestBed } from '@angular/core/testing';

import { InMemoryTokenServiceService } from './in-memory-token-service.service';

describe('InMemoryTokenServiceService', () => {
  let service: InMemoryTokenServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InMemoryTokenServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
