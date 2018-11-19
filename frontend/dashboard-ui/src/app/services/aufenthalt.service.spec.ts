import { TestBed } from '@angular/core/testing';

import { AufenthaltService } from './aufenthalt.service';

describe('AufenthaltService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AufenthaltService = TestBed.get(AufenthaltService);
    expect(service).toBeTruthy();
  });
});
