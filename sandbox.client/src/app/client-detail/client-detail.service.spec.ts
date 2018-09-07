import { TestBed, inject } from '@angular/core/testing';

import { ClientDetailService } from './client-detail.service';

describe('ClientDetailService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ClientDetailService]
    });
  });

  it('should be created', inject([ClientDetailService], (service: ClientDetailService) => {
    expect(service).toBeTruthy();
  }));
});
