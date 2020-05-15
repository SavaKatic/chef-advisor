import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnitType } from 'app/shared/model/unit-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UnitTypeService } from './unit-type.service';
import { UnitTypeDeleteDialogComponent } from './unit-type-delete-dialog.component';

@Component({
  selector: 'jhi-unit-type',
  templateUrl: './unit-type.component.html'
})
export class UnitTypeComponent implements OnInit, OnDestroy {
  unitTypes: IUnitType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected unitTypeService: UnitTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.unitTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.unitTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IUnitType[]>) => this.paginateUnitTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.unitTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUnitTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUnitType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUnitTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('unitTypeListModification', () => this.reset());
  }

  delete(unitType: IUnitType): void {
    const modalRef = this.modalService.open(UnitTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unitType = unitType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUnitTypes(data: IUnitType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.unitTypes.push(data[i]);
      }
    }
  }
}
