import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { CalorieConfigurationDetailComponent } from 'app/entities/calorie-configuration/calorie-configuration-detail.component';
import { CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

describe('Component Tests', () => {
  describe('CalorieConfiguration Management Detail Component', () => {
    let comp: CalorieConfigurationDetailComponent;
    let fixture: ComponentFixture<CalorieConfigurationDetailComponent>;
    const route = ({ data: of({ calorieConfiguration: new CalorieConfiguration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [CalorieConfigurationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CalorieConfigurationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CalorieConfigurationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load calorieConfiguration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.calorieConfiguration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
