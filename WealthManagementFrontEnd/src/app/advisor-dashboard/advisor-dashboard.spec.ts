import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvisorDashboard } from './advisor-dashboard';

describe('AdvisorDashboard', () => {
  let component: AdvisorDashboard;
  let fixture: ComponentFixture<AdvisorDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvisorDashboard],
    }).compileComponents();

    fixture = TestBed.createComponent(AdvisorDashboard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
