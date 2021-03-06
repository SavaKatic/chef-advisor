import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DishService } from 'app/entities/dish/dish.service';
import { IDish, Dish } from 'app/shared/model/dish.model';
import { DishCategory } from 'app/shared/model/enumerations/dish-category.model';

describe('Service Tests', () => {
  describe('Dish Service', () => {
    let injector: TestBed;
    let service: DishService;
    let httpMock: HttpTestingController;
    let elemDefault: IDish;
    let expectedResult: IDish | IDish[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DishService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Dish(0, 'AAAAAAA', DishCategory.BREAKFAST, 'image/png', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Dish', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Dish()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Dish', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            category: 'BBBBBB',
            image: 'BBBBBB',
            description: 'BBBBBB',
            averageRating: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Dish', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            category: 'BBBBBB',
            image: 'BBBBBB',
            description: 'BBBBBB',
            averageRating: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Dish', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
