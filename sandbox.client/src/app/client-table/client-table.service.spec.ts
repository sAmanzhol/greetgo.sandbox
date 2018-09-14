import {inject, TestBed} from '@angular/core/testing';

import {ClientTableService} from './client-table.service';

+

  describe('ClientTableService', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [ClientTableService]
      });
    });

    it('should be created', inject([ClientTableService], (service: ClientTableService) => {
      expect(service).toBeTruthy();
    }));
  });
