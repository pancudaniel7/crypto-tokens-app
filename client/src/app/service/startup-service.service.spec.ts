import { TestBed } from '@angular/core/testing';

import { StartupServiceService } from './startup-service.service';

describe('StartupServiceService', () => {
  let service: StartupServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StartupServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
