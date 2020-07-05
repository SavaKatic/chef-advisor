import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { AlarmTriggerTemplateUpdateComponent } from 'app/entities/alarm-trigger-template/alarm-trigger-template-update.component';
import { AlarmTriggerTemplateService } from 'app/entities/alarm-trigger-template/alarm-trigger-template.service';
import { AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

describe('Component Tests', () => {
  describe('AlarmTriggerTemplate Management Update Component', () => {
    let comp: AlarmTriggerTemplateUpdateComponent;
    let fixture: ComponentFixture<AlarmTriggerTemplateUpdateComponent>;
    let service: AlarmTriggerTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [AlarmTriggerTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AlarmTriggerTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmTriggerTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmTriggerTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AlarmTriggerTemplate(123);
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
        const entity = new AlarmTriggerTemplate();
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
