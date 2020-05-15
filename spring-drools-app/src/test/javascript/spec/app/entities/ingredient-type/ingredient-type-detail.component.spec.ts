import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { IngredientTypeDetailComponent } from 'app/entities/ingredient-type/ingredient-type-detail.component';
import { IngredientType } from 'app/shared/model/ingredient-type.model';

describe('Component Tests', () => {
  describe('IngredientType Management Detail Component', () => {
    let comp: IngredientTypeDetailComponent;
    let fixture: ComponentFixture<IngredientTypeDetailComponent>;
    const route = ({ data: of({ ingredientType: new IngredientType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [IngredientTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IngredientTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngredientTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ingredientType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingredientType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
