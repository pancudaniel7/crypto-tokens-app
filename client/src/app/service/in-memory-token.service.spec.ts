import { TestBed } from '@angular/core/testing';

import { InMemoryTokenService } from './in-memory-token.service';

describe('InMemoryTokenService', () => {
  let service: InMemoryTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InMemoryTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
