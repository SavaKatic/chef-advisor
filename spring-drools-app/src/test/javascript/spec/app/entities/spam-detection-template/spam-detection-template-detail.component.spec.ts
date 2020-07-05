import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { SpamDetectionTemplateDetailComponent } from 'app/entities/spam-detection-template/spam-detection-template-detail.component';
import { SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

describe('Component Tests', () => {
  describe('SpamDetectionTemplate Management Detail Component', () => {
    let comp: SpamDetectionTemplateDetailComponent;
    let fixture: ComponentFixture<SpamDetectionTemplateDetailComponent>;
    const route = ({ data: of({ spamDetectionTemplate: new SpamDetectionTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [SpamDetectionTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpamDetectionTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpamDetectionTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load spamDetectionTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spamDetectionTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
