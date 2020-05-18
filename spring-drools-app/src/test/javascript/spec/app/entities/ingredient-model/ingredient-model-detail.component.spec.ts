import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { IngredientModelDetailComponent } from 'app/entities/ingredient-model/ingredient-model-detail.component';
import { IngredientModel } from 'app/shared/model/ingredient-model.model';

describe('Component Tests', () => {
  describe('IngredientModel Management Detail Component', () => {
    let comp: IngredientModelDetailComponent;
    let fixture: ComponentFixture<IngredientModelDetailComponent>;
    const route = ({ data: of({ ingredientModel: new IngredientModel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [IngredientModelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IngredientModelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IngredientModelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ingredientModel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ingredientModel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
