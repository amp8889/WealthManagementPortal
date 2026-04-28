import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientRecords } from './client-records';

describe('ClientRecords', () => {
  let component: ClientRecords;
  let fixture: ComponentFixture<ClientRecords>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientRecords],
    }).compileComponents();

    fixture = TestBed.createComponent(ClientRecords);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
