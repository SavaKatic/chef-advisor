import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ChefadvisorTestModule } from '../../../test.module';
import { RatingComponent } from 'app/entities/rating/rating.component';
import { RatingService } from 'app/entities/rating/rating.service';
import { Rating } from 'app/shared/model/rating.model';

describe('Component Tests', () => {
  describe('Rating Management Component', () => {
    let comp: RatingComponent;
    let fixture: ComponentFixture<RatingComponent>;
    let service: RatingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChefadvisorTestModule],
        declarations: [RatingComponent]
      })
        .overrideTemplate(RatingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RatingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RatingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Rating(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ratings && comp.ratings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
