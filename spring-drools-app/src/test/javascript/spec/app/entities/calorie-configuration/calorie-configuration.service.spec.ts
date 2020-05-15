import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CalorieConfigurationService } from 'app/entities/calorie-configuration/calorie-configuration.service';
import { ICalorieConfiguration, CalorieConfiguration } from 'app/shared/model/calorie-configuration.model';

describe('Service Tests', () => {
  describe('CalorieConfiguration Service', () => {
    let injector: TestBed;
    let service: CalorieConfigurationService;
    let httpMock: HttpTestingController;
    let elemDefault: ICalorieConfiguration;
    let expectedResult: ICalorieConfiguration | ICalorieConfiguration[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CalorieConfigurationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CalorieConfiguration(0, 0, 0, 0, 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CalorieConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CalorieConfiguration()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CalorieConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            breakfastLow: 1,
            breakfastHigh: 1,
            lunchLow: 1,
            lunchHigh: 1,
            dinnerLow: 1,
            dinnerHigh: 1,
            snackLow: 1,
            snackHigh: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CalorieConfiguration', () => {
        const returnedFromService = Object.assign(
          {
            breakfastLow: 1,
            breakfastHigh: 1,
            lunchLow: 1,
            lunchHigh: 1,
            dinnerLow: 1,
            dinnerHigh: 1,
            snackLow: 1,
            snackHigh: 1
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

      it('should delete a CalorieConfiguration', () => {
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
