import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { IngredientModelUpdateComponent } from 'app/entities/ingredient-model/ingredient-model-update.component';
import { IngredientModelService } from 'app/entities/ingredient-model/ingredient-model.service';
import { IngredientModel } from 'app/shared/model/ingredient-model.model';

describe('Component Tests', () => {
  describe('IngredientModel Management Update Component', () => {
    let comp: IngredientModelUpdateComponent;
    let fixture: ComponentFixture<IngredientModelUpdateComponent>;
    let service: IngredientModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [IngredientModelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IngredientModelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngredientModelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngredientModelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngredientModel(123);
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
        const entity = new IngredientModel();
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
