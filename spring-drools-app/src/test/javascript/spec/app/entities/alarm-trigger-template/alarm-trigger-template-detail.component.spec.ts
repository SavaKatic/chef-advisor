import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { AlarmTriggerTemplateDetailComponent } from 'app/entities/alarm-trigger-template/alarm-trigger-template-detail.component';
import { AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

describe('Component Tests', () => {
  describe('AlarmTriggerTemplate Management Detail Component', () => {
    let comp: AlarmTriggerTemplateDetailComponent;
    let fixture: ComponentFixture<AlarmTriggerTemplateDetailComponent>;
    const route = ({ data: of({ alarmTriggerTemplate: new AlarmTriggerTemplate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [AlarmTriggerTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AlarmTriggerTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AlarmTriggerTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load alarmTriggerTemplate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.alarmTriggerTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
