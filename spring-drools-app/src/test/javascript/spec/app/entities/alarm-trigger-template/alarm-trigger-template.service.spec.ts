import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AlarmTriggerTemplateService } from 'app/entities/alarm-trigger-template/alarm-trigger-template.service';
import { IAlarmTriggerTemplate, AlarmTriggerTemplate } from 'app/shared/model/alarm-trigger-template.model';

describe('Service Tests', () => {
  describe('AlarmTriggerTemplate Service', () => {
    let injector: TestBed;
    let service: AlarmTriggerTemplateService;
    let httpMock: HttpTestingController;
    let elemDefault: IAlarmTriggerTemplate;
    let expectedResult: IAlarmTriggerTemplate | IAlarmTriggerTemplate[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AlarmTriggerTemplateService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AlarmTriggerTemplate(0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AlarmTriggerTemplate', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AlarmTriggerTemplate()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AlarmTriggerTemplate', () => {
        const returnedFromService = Object.assign(
          {
            numberOfSuspiciousActions: 1,
            warningMessage: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AlarmTriggerTemplate', () => {
        const returnedFromService = Object.assign(
          {
            numberOfSuspiciousActions: 1,
            warningMessage: 'BBBBBB'
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

      it('should delete a AlarmTriggerTemplate', () => {
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
