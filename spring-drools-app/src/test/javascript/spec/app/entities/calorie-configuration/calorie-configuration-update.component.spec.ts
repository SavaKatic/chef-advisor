import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { CalorieConfigurationUpdateComponent } from 'app/entities/calorie-configuration/calorie-configuration-update.component';
import { CalorieConfigurationService } from 'app/entities/calorie-configuration/calorie-configuration.service';
import { CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

describe('Component Tests', () => {
  describe('CalorieConfiguration Management Update Component', () => {
    let comp: CalorieConfigurationUpdateComponent;
    let fixture: ComponentFixture<CalorieConfigurationUpdateComponent>;
    let service: CalorieConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [CalorieConfigurationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CalorieConfigurationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CalorieConfigurationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CalorieConfigurationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CalorieConfiguration(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CalorieConfiguration();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
