import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChefadvisorTestModule } from '../../../test.module';
import { AlarmTriggerTemplateComponent } from 'app/entities/alarm-trigger-template/alarm-trigger-template.component';
import { AlarmTriggerTemplateService } from 'app/entities/alarm-trigger-template/alarm-trigger-template.service';
import { AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

describe('Component Tests', () => {
  describe('AlarmTriggerTemplate Management Component', () => {
    let comp: AlarmTriggerTemplateComponent;
    let fixture: ComponentFixture<AlarmTriggerTemplateComponent>;
    let service: AlarmTriggerTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [AlarmTriggerTemplateComponent]
      })
        .overrideTemplate(AlarmTriggerTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmTriggerTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmTriggerTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlarmTriggerTemplate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alarmTriggerTemplates && comp.alarmTriggerTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
