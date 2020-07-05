import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChefadvisorTestModule } from '../../../test.module';
import { SpamDetectionTemplateComponent } from 'app/entities/spam-detection-template/spam-detection-template.component';
import { SpamDetectionTemplateService } from 'app/entities/spam-detection-template/spam-detection-template.service';
import { SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

describe('Component Tests', () => {
  describe('SpamDetectionTemplate Management Component', () => {
    let comp: SpamDetectionTemplateComponent;
    let fixture: ComponentFixture<SpamDetectionTemplateComponent>;
    let service: SpamDetectionTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [SpamDetectionTemplateComponent]
      })
        .overrideTemplate(SpamDetectionTemplateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpamDetectionTemplateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpamDetectionTemplateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SpamDetectionTemplate(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spamDetectionTemplates && comp.spamDetectionTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
