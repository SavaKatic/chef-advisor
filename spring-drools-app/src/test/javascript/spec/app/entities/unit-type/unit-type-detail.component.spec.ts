import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { UnitTypeDetailComponent } from 'app/entities/unit-type/unit-type-detail.component';
import { UnitType } from 'app/shared/model/unit-type.model';

describe('Component Tests', () => {
  describe('UnitType Management Detail Component', () => {
    let comp: UnitTypeDetailComponent;
    let fixture: ComponentFixture<UnitTypeDetailComponent>;
    const route = ({ data: of({ unitType: new UnitType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [UnitTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UnitTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UnitTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load unitType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.unitType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
