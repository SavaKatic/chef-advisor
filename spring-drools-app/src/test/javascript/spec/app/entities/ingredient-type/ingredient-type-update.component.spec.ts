import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { IngredientTypeUpdateComponent } from 'app/entities/ingredient-type/ingredient-type-update.component';
import { IngredientTypeService } from 'app/entities/ingredient-type/ingredient-type.service';
import { IngredientType } from 'app/shared/model/ingredient-type.model';

describe('Component Tests', () => {
  describe('IngredientType Management Update Component', () => {
    let comp: IngredientTypeUpdateComponent;
    let fixture: ComponentFixture<IngredientTypeUpdateComponent>;
    let service: IngredientTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [IngredientTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IngredientTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IngredientTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IngredientTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IngredientType(123);
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
        const entity = new IngredientType();
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
