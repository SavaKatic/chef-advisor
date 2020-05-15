import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ChefadvisorTestModule } from '../../../test.module';
import { DishTypeUpdateComponent } from 'app/entities/dish-type/dish-type-update.component';
import { DishTypeService } from 'app/entities/dish-type/dish-type.service';
import { DishType } from 'app/shared/model/dish-type.model';

describe('Component Tests', () => {
  describe('DishType Management Update Component', () => {
    let comp: DishTypeUpdateComponent;
    let fixture: ComponentFixture<DishTypeUpdateComponent>;
    let service: DishTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [DishTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DishTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DishTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DishTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DishType(123);
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
        const entity = new DishType();
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
