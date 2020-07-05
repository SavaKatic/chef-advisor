import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { SpamDetectionTemplateUpdateComponent } from 'app/entities/spam-detection-template/spam-detection-template-update.component';
import { SpamDetectionTemplateService } from 'app/entities/spam-detection-template/spam-detection-template.service';
import { SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

describe('Component Tests', () => {
  describe('SpamDetectionTemplate Management Update Component', () => {
    let comp: SpamDetectionTemplateUpdateComponent;
    let fixture: ComponentFixture<SpamDetectionTemplateUpdateComponent>;
    let service: SpamDetectionTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [SpamDetectionTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpamDetectionTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpamDetectionTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpamDetectionTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpamDetectionTemplate(123);
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
        const entity = new SpamDetectionTemplate();
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
