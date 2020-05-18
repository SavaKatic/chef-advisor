import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChefadvisorTestModule } from '../../../test.module';
import { CalorieConfigurationComponent } from 'app/entities/calorie-configuration/calorie-configuration.component';
import { CalorieConfigurationService } from 'app/entities/calorie-configuration/calorie-configuration.service';
import { CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

describe('Component Tests', () => {
  describe('CalorieConfiguration Management Component', () => {
    let comp: CalorieConfigurationComponent;
    let fixture: ComponentFixture<CalorieConfigurationComponent>;
    let service: CalorieConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [CalorieConfigurationComponent]
      })
        .overrideTemplate(CalorieConfigurationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalorieConfigurationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalorieConfigurationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CalorieConfiguration(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.calorieConfigurations && comp.calorieConfigurations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
