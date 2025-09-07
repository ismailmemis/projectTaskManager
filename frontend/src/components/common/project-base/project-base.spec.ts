import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectBase } from './project-base';

describe('ProjectBase', () => {
  let component: ProjectBase;
  let fixture: ComponentFixture<ProjectBase>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectBase]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectBase);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
