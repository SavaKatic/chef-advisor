import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SpamDetectionTemplateService } from 'app/entities/spam-detection-template/spam-detection-template.service';
import { ISpamDetectionTemplate, SpamDetectionTemplate } from 'app/shared/model/spam-detection-template.model';

describe('Service Tests', () => {
  describe('SpamDetectionTemplate Service', () => {
    let injector: TestBed;
    let service: SpamDetectionTemplateService;
    let httpMock: HttpTestingController;
    let elemDefault: ISpamDetectionTemplate;
    let expectedResult: ISpamDetectionTemplate | ISpamDetectionTemplate[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SpamDetectionTemplateService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SpamDetectionTemplate(0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SpamDetectionTemplate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SpamDetectionTemplate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SpamDetectionTemplate', () => {
        const returnedFromService = Object.assign(
          {
            numberOfComments: 1,
            rating: 1,
            reason: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SpamDetectionTemplate', () => {
        const returnedFromService = Object.assign(
          {
            numberOfComments: 1,
            rating: 1,
            reason: 'BBBBBB'
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

      it('should delete a SpamDetectionTemplate', () => {
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
